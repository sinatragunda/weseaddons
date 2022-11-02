package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.pojo.Charges;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountTransactions;
import com.wese.weseaddons.bereaudechange.pojo.TransactionCharge;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionChargeMapper implements RowMapper<TransactionCharge> {

    ///this is a mapper class linking many to many kind of relationship
    @Override
    public TransactionCharge mapRow(ResultSet rs , int rowNum){

        TransactionCharge transactionCharge = null ;
        try{
            transactionCharge = new TransactionCharge();
            transactionCharge.setId(rs.getLong("id"));
            transactionCharge.setMoneyAccountTransactions(new MoneyAccountTransactions(rs.getLong("transaction_id")));
            transactionCharge.setCharges(new Charges(rs.getLong("charge_id")));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return transactionCharge ;
    }
}
