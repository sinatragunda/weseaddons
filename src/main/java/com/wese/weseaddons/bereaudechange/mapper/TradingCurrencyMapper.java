package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FinancialInstrument;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradingCurrencyMapper implements RowMapper<TradingCurrency> {

    public TradingCurrency mapRow(ResultSet resultSet ,int rowNum){
        TradingCurrency tradingCurrency = null ;
        try{

            tradingCurrency = new TradingCurrency();
            tradingCurrency.setId(resultSet.getLong("id"));
            tradingCurrency.setAddedDate(resultSet.getLong("added_date"));
            tradingCurrency.setName(resultSet.getString("name"));
            tradingCurrency.setSymbol(resultSet.getString("symbol"));
            tradingCurrency.setDescription(resultSet.getString("description"));
            long id = resultSet.getLong("financial_instrument_id");

            FinancialInstrument financialInstrument = new FinancialInstrument(id);
            tradingCurrency.setFinancialInstrument(financialInstrument);

            long id2 = resultSet.getLong("standard_currency_id");
            tradingCurrency.setStandardCurrency(new StandardCurrency(id2));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return tradingCurrency ;
    }
}
