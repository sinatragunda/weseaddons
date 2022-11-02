package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;

import com.wese.weseaddons.bereaudechange.pojo.LiveRates;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LiveRatesMapper implements  RowMapper<LiveRates> {

    @Override
    public LiveRates mapRow(ResultSet rs , int rowNum){

        LiveRates liveRates= null ;

        try{

            liveRates = new LiveRates();
            liveRates.setId(rs.getLong("id"));
            long id = rs.getLong("trading_rates_id");

            TradingRates tradingRates = new TradingRates(id);
            liveRates.setTradingRates(tradingRates);
            liveRates.setHasGained(rs.getBoolean("has_gained"));
            liveRates.setPercentageChange(rs.getDouble("percentage_change"));

        }

        catch(SQLException s){
            s.printStackTrace();
        }

        return liveRates;
    }
}