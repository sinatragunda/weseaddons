package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.PaymentMethods;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrency;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrencyPaymentMethodsRM;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TradingCurrencyPaymentMethodsMapper implements RowMapper<TradingCurrencyPaymentMethodsRM>{

    @Override
    public TradingCurrencyPaymentMethodsRM mapRow(ResultSet resultSet, int i) throws SQLException {
    	TradingCurrencyPaymentMethodsRM tradingCurrencyPaymentMethodsRM =  null;

        try{
        	tradingCurrencyPaymentMethodsRM = new TradingCurrencyPaymentMethodsRM();
        	tradingCurrencyPaymentMethodsRM.setId(resultSet.getLong("id"));
        	tradingCurrencyPaymentMethodsRM.setActive(resultSet.getBoolean("active"));
            long tradingCurrencyId = resultSet.getLong("trading_currency_id");
            long paymentMethodId = resultSet.getLong("payment_method_id");

            tradingCurrencyPaymentMethodsRM.setPaymentMethod(new PaymentMethods(paymentMethodId));
            tradingCurrencyPaymentMethodsRM.setTradingCurrency(new TradingCurrency(tradingCurrencyId));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return tradingCurrencyPaymentMethodsRM;
    }
}
