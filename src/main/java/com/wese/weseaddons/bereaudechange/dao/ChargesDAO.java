package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.ChargesMapper;
import com.wese.weseaddons.bereaudechange.pojo.Charges;
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

public class ChargesDAO implements FxDAOService{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public ChargesDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<Charges> findAll(){

        String sql = "SELECT * FROM m_fx_charges";

        List<Charges> chargesList = jdbcTemplate.query(sql ,new ChargesMapper());

        if (chargesList.isEmpty()){
            return new ArrayList<>();
        }

        return chargesList;

    }

    public Charges find(long id){

        String sql = "SELECT * FROM m_fx_charges where id=?";

        Object args[] = new Object[]{
                id
        };

        List<Charges> chargesList = jdbcTemplate.query(sql ,args ,new ChargesMapper());

        if (chargesList.isEmpty()){
            return null;
        }

        return chargesList.get(0);

    }

    public ObjectNode edit(Charges charges){

        if(charges==null){
            return Helper.statusNodes(false);
        }

        String sql = "update m_fx_charges set name = ? ,description= ? ,charge_criteria = ? ,t_account = ? ,amount=? where id = ?";
        Object args[]= new Object[]{
                charges.getName() ,
                charges.getDescription() ,
                charges.getChargeCriteria().ordinal(),
                charges.gettAccount().ordinal() ,
                charges.getAmount() ,
                charges.getId()
        };

        int rows = jdbcTemplate.update(sql ,args);

        if(rows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);
    }


    public boolean create(Charges charges){

        String sql ="insert into m_fx_charges(name ,description,amount,charge_criteria ,t_account) values(? ,?,? ,?,?)";
        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,charges.getName());
                    statement.setString(2 ,charges.getDescription());
                    statement.setDouble(3 ,charges.getAmount());
                    statement.setInt(4 ,charges.getChargeCriteria().ordinal());
                    statement.setInt(5 ,charges.gettAccount().ordinal());
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }

        if(id!=0){
            return true ;
        }

        return false ;

    }
}
