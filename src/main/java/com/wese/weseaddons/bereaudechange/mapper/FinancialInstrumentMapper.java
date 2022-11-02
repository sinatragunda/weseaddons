package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FinancialInstrument;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FinancialInstrumentMapper implements RowMapper<FinancialInstrument>{

    public FinancialInstrument mapRow(ResultSet resultSet ,int rowNum){

        FinancialInstrument financialInstrument = null ;

        try{
            financialInstrument = new FinancialInstrument();
            financialInstrument.setId(resultSet.getLong("id"));
            financialInstrument.setDescription(resultSet.getString("description"));
            financialInstrument.setName(resultSet.getString("name"));
            financialInstrument.setFinancialInstrumentType(resultSet.getInt("instrument_type"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return financialInstrument ;
    }
}