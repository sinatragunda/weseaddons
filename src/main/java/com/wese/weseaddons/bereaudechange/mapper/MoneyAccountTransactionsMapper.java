package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.enumerations.MONEY_ACCOUNT_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountTransactions;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyAccountTransactionsMapper implements RowMapper<MoneyAccountTransactions>{

    @Override
    public MoneyAccountTransactions mapRow(ResultSet resultSet, int i) throws SQLException {

        MoneyAccountTransactions moneyAccountTransactions = null;
        try{

            moneyAccountTransactions = new MoneyAccountTransactions();
            moneyAccountTransactions.setId(resultSet.getLong("id"));
            moneyAccountTransactions.setAmount(resultSet.getDouble("amount"));
            moneyAccountTransactions.setRunningBalance(resultSet.getDouble("running_balance"));
            moneyAccountTransactions.setCharges(resultSet.getDouble("charges"));

            long id = resultSet.getLong("fx_deal_id");
            FxDeal fxDeal = new FxDeal(id);
            moneyAccountTransactions.setFxDeal(fxDeal);

            long id2 = resultSet.getLong("money_account_id");
            MoneyAccount moneyAccount = new MoneyAccount(id2);
            moneyAccountTransactions.setMoneyAccount(moneyAccount);


            int appliedOn = resultSet.getInt("t_account");
            moneyAccountTransactions.settAccount(T_ACCOUNT.fromInt(appliedOn));


        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return moneyAccountTransactions ;
    }
}
