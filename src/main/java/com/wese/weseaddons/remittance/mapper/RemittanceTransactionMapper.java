package com.wese.weseaddons.remittance.mapper;

import com.wese.weseaddons.bereaudechange.pojo.FinancialInstrument;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.remittance.enumerations.REMITTANCE_TRANSACTION_STATE;
import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;
import com.wese.weseaddons.remittance.pojo.Remitter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RemittanceTransactionMapper implements RowMapper<RemittanceTransaction> {

    public RemittanceTransaction mapRow(ResultSet resultSet ,int rowNum){

        RemittanceTransaction remittanceTransaction = null;
        try{

            remittanceTransaction = new RemittanceTransaction();
            remittanceTransaction.setId(resultSet.getLong("id"));

            remittanceTransaction.setFundsKey(resultSet.getString("funds_key"));

            String recieverNameString  = resultSet.getString("remitter_reciever");
            remittanceTransaction.setRemitterReciever(new Remitter(recieverNameString));

            String senderNameString  = resultSet.getString("remitter_sender");
            remittanceTransaction.setRemitterSender(new Remitter(senderNameString));

            remittanceTransaction.setAmount(resultSet.getDouble("amount"));
            remittanceTransaction.setCommission(resultSet.getDouble("commission"));


            long currencyId = resultSet.getLong("standard_currency_id");
            remittanceTransaction.setStandardCurrency(new StandardCurrency(currencyId));

            int state = resultSet.getInt("state");

            remittanceTransaction.setRemittanceTransactionState(state);

            remittanceTransaction.setTransactionDate(resultSet.getLong("date"));

            long cashierIdSender = resultSet.getLong("fx_cashier_sender_id");
            long cashierIdReciever = resultSet.getLong("fx_cashier_reciever_id");

            remittanceTransaction.setFxCashierSender(new FxCashier(cashierIdSender));

            if(cashierIdReciever != 0){
                remittanceTransaction.setFxCashierReciever(new FxCashier(cashierIdReciever));
                remittanceTransaction.setDisbursedDate(resultSet.getLong("disbursed_date"));
            }


        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return remittanceTransaction ;
    }
}
