package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FxDailyTracker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FxDailyTrackerMapper implements RowMapper<FxDailyTracker> {

    @Override
    public FxDailyTracker mapRow(ResultSet rs , int rowNum){

        FxDailyTracker dailyTracker = null ;

        try{
            dailyTracker = new FxDailyTracker();
            dailyTracker.setId(rs.getLong("id"));
            dailyTracker.setAmount(rs.getDouble("amount"));
            dailyTracker.setDate(rs.getDate("date"));

        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return dailyTracker ;

    }


}
