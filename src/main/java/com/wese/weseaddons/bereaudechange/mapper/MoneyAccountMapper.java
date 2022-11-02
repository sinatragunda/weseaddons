package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.dao.TradingCurrencyDAO;
import com.wese.weseaddons.bereaudechange.enumerations.MONEY_ACCOUNT_TYPE;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyAccountMapper implements RowMapper<MoneyAccount>{

    @Override
    public MoneyAccount mapRow(ResultSet resultSet, int i) throws SQLException {
        MoneyAccount moneyAccount = null;
        try{
            moneyAccount = new MoneyAccount();
            moneyAccount.setId(resultSet.getLong("id"));
            moneyAccount.setName(resultSet.getString("name"));
            moneyAccount.setBalance(resultSet.getDouble("balance"));
            moneyAccount.setLowerLimit(resultSet.getDouble("lower_limit"));
            moneyAccount.setUpperLimit(resultSet.getDouble("upper_limit"));
            moneyAccount.setAccountNumber(resultSet.getString("account_number"));

            long tradingCurrencyId = resultSet.getLong("trading_currency_id");
            TradingCurrency tradingCurrency = new TradingCurrency(tradingCurrencyId);

            moneyAccount.setTradingCurrency(tradingCurrency);

            int moneyAccountType = resultSet.getInt("money_account_type");
            moneyAccount.setMoneyAccountType(MONEY_ACCOUNT_TYPE.fromInt(moneyAccountType));
            moneyAccount.setActive(resultSet.getBoolean("active"));


        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return moneyAccount ;
    }
}
