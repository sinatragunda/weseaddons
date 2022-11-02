package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FinancialInstrument;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StandardCurrencyMapper implements RowMapper<StandardCurrency> {

    public StandardCurrency mapRow(ResultSet resultSet ,int rowNum){
        
        StandardCurrency standardCurrency = null ;
        try{

            standardCurrency = new StandardCurrency();
            standardCurrency.setId(resultSet.getLong("id"));
            standardCurrency.setName(resultSet.getString("name"));
            standardCurrency.setCountry(resultSet.getString("country"));
            standardCurrency.setInternationalCode(resultSet.getString("internationalized_name_code"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return standardCurrency ;
    }
}
