package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.Blotter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BlotterMapper implements RowMapper<Blotter> {


    @Override
    public Blotter mapRow(ResultSet rs , int rowNum){

        Blotter blotter =null ;
        try{
            blotter = new Blotter();
            blotter.setId(rs.getLong("id"));
            blotter.setBaseAmount(rs.getDouble("base_amount"));
            blotter.setBaseCharges(rs.getDouble("base_charges"));
            blotter.setQuoteAmount(rs.getDouble("quote_amount"));
            blotter.setQuoteCharges(rs.getDouble("quote_charges"));
            blotter.setNetQuoteAmount(rs.getDouble("net_quote_amount"));
            blotter.setNetBaseAmount(rs.getDouble("net_base_amount"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return blotter ;
    }
}
