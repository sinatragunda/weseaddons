package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;

import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradingRatesMapper implements  RowMapper<TradingRates> {

    @Override
    public TradingRates mapRow(ResultSet rs , int rowNum){

        TradingRates tradingRates= null ;

        try{
            tradingRates = new TradingRates();
            tradingRates.setId(rs.getLong("id"));
            tradingRates.setRate(rs.getDouble("rate"));
            tradingRates.setSellRate(rs.getDouble("sell_rate"));
            tradingRates.setOpenTime(rs.getLong("open_time"));
            boolean isActive = rs.getBoolean("active");
            tradingRates.setActive(isActive);

            tradingRates.setCloseTime(rs.getLong("close_time"));

            long id = rs.getLong("currency_pair_id");
            CurrencyPair currencyPair = new CurrencyPair(id);
            tradingRates.setCurrencyPair(currencyPair);

        }

        catch(SQLException s){
            s.printStackTrace();
        }

        return tradingRates;
    }
}