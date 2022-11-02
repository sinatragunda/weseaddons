package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FxWeightedAverage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FxWeightedAverageMapper implements RowMapper<FxWeightedAverage>{

    @Override
    public FxWeightedAverage mapRow(ResultSet resultSet, int i) throws SQLException {
    	FxWeightedAverage myPojo = null;
        try{
            myPojo = new FxWeightedAverage();
            myPojo.setId(resultSet.getLong("id"));
            myPojo.setCurrencySymbol((resultSet.getString("currency_symbol")));
            myPojo.setWeightedAverageBuyPrice(resultSet.getDouble("weighted_average_buy_price"));
            myPojo.setTotalCurrencyBought(resultSet.getDouble("total_currency_bought"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return myPojo ;
    }
}
