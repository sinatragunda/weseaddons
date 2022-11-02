package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.Charges;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountChargesRM;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyAccountChargesMapper implements RowMapper<MoneyAccountChargesRM>{

    @Override
    public MoneyAccountChargesRM mapRow(ResultSet resultSet, int i) throws SQLException {
        MoneyAccountChargesRM moneyAccountChargesRM =  null;

        try{
            moneyAccountChargesRM = new MoneyAccountChargesRM();
            moneyAccountChargesRM.setId(resultSet.getLong("id"));
            moneyAccountChargesRM.setActive(resultSet.getBoolean("active"));
            long accountId = resultSet.getLong("money_account_id");
            long chargeId = resultSet.getLong("charge_id");

            moneyAccountChargesRM.setCharges(new Charges(chargeId));
            moneyAccountChargesRM.setMoneyAccount(new MoneyAccount(accountId));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return moneyAccountChargesRM;
    }
}
