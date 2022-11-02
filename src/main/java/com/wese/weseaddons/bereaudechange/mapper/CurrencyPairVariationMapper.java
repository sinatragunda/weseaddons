package com.wese.weseaddons.bereaudechange.mapper;


import com.wese.weseaddons.bereaudechange.pojo.CurrencyPairVariation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CurrencyPairVariationMapper implements RowMapper<CurrencyPairVariation> {


    @Override
    public CurrencyPairVariation mapRow(ResultSet rs , int rowNum){

        CurrencyPairVariation currencyPairVariation =null ;
        try{

            currencyPairVariation = new CurrencyPairVariation();
            currencyPairVariation.setId(rs.getLong("id"));
            currencyPairVariation.setValue(rs.getDouble("value"));
            currencyPairVariation.setExchangeVariation(rs.getInt("exchange_variation"));

        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return currencyPairVariation ;
    }
}
