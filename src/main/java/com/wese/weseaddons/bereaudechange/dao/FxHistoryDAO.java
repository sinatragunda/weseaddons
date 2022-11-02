package com.wese.weseaddons.bereaudechange.dao;


import com.wese.weseaddons.bereaudechange.mapper.FxHistoryMapper;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxHistory;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class FxHistoryDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    private Consumer<FxHistory> fxHistoryConsumer = (e)->{

        AppUser appUser = FxSession.getInstance().getAppUser(tenant ,e.getAuthorizer().getId());
        if(appUser !=null){
            e.setAuthorizer(appUser);
        }
    };


    public FxHistoryDAO(){}

    public FxHistoryDAO(String tenant){

        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }


    public List<FxHistory> findAll(){

        String sql = "SELECT * FROM m_fx_history";

        List<FxHistory> fxHistoryList = jdbcTemplate.query(sql ,new FxHistoryMapper());

        if(fxHistoryList.isEmpty()){
            return new ArrayList<>();
        }

        fxHistoryList.stream().forEach(fxHistoryConsumer);
        return fxHistoryList;
    }



    public void create(FxHistory fxHistory){

        long id = 0;

        String sql= "insert into m_fx_history(audit_trail ,resource_id ,authoriser_id,date) values (? ,? ,? ,?)";

        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setInt(1,fxHistory.getAuditTrail().ordinal());
                    statement.setLong(2,fxHistory.getResourceId());
                    statement.setLong(3 ,fxHistory.getAuthorizer().getId());
                    statement.setLong(4 ,Helper.timestampNow());
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            e.printStackTrace();

        }

    }
}


