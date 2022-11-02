package com.wese.weseaddons.bereaudechange.dao;


import com.wese.weseaddons.bereaudechange.mapper.FxDailyTrackerMapper;
import com.wese.weseaddons.bereaudechange.pojo.*;

import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Date;
import java.util.List;

public class FxDailyTrackerDAO{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public FxDailyTrackerDAO(){}

    public FxDailyTrackerDAO(String tenant){

        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public FxDailyTracker findWhereDayIsToday(){

        Date date = Helper.sqlDateNow();

        String sql = "SELECT * FROM m_fx_daily_tracker WHERE date=?";

        Object args[] = new Object[]{
                date 
        };

        List<FxDailyTracker> fxDailyTrackerList = jdbcTemplate.query(sql ,args ,new FxDailyTrackerMapper());

        if (fxDailyTrackerList.isEmpty()){
            return null ;
        }

        return fxDailyTrackerList.get(0);
    }

    public void updateWhereDayIsToday(double amount){

        String sql = "UPDATE m_fx_daily_tracker SET amount = ? WHERE date = ?";
        Object args[] = new Object[]{
                amount ,
                Helper.sqlDateNow()
        };

        int affectedRows = jdbcTemplate.update(sql ,args);

    }



    public FxDailyTracker createWhereDayIsToday(){

        Date date = Helper.sqlDateNow();
        long id = 0;

        String sql= "insert into m_fx_daily_tracker(amount ,date) values (? ,?)";
        
        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1,0);
                    statement.setDate(2,date);
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            e.printStackTrace();
            return null ;
        }

        return new FxDailyTracker(id ,0 ,date);
    }
}
