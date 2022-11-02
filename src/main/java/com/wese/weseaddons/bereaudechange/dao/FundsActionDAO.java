package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.TransactFunds;
import com.wese.weseaddons.bereaudechange.mapper.FundsActionMapper;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FundsAction;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FundsActionDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public FundsActionDAO(String tenant){

        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<FundsAction> find(long id){

        String sql = "SELECT * FROM m_fx_funds_action WHERE source_account_id = ? OR destination_account_id = ?";

        Object object[] = new Object[]{
                id ,
                id
        };

        List<FundsAction> fundsActionsList = jdbcTemplate.query(sql ,object,new FundsActionMapper());

        if(fundsActionsList.isEmpty()){
            new ArrayList<>();
        }

        for(FundsAction fundsAction : fundsActionsList){

            AppUser appUser = FxSession.getInstance().getTenantSession(tenant).getAppUser(fundsAction.getAuthoriser().getId());
            fundsAction.setAuthoriser(appUser);
        }

        return fundsActionsList ;

    }

    public ObjectNode create(FundsAction fundsAction , boolean includeCharges){


        TransactFunds transactFunds = new TransactFunds(fundsAction ,tenant ,includeCharges);
        ObjectNode objectNode = transactFunds.transact();
        boolean status = objectNode.get("status").asBoolean();

        if(!status){

            return objectNode ;
        }

        String sql ="INSERT INTO m_fx_funds_action(source_account_id ,destination_account_id ,amount ,action_type ,authoriser_id) VALUES(? ,? ,? ,? ,?)";

        long id = 0 ;

        try{
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, fundsAction.getSourceMoneyAccount().getId());
                    statement.setLong(2 ,fundsAction.getDestinationMoneyAccount().getId());
                    statement.setDouble(3,fundsAction.getAmount());
                    statement.setInt(4 ,fundsAction.getFundsActionType().ordinal());
                    statement.setLong(5 ,fundsAction.getAuthoriser().getId());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
            fundsAction.setId(id);

        }

        catch (Exception e) {
            e.printStackTrace();
            return Helper.statusNodes(false).put("fx_error_message","Failed to run please try again");
        }


        return Helper.statusNodes(true);

    }

}
