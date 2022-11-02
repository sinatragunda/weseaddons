/*Created by Sinatra Gunda
  At 04:32 on 2/17/2021 */

package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.GeneralLedger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneralLedgerMapper implements MapperInterface {

    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        GeneralLedger generalLedger = null ;
        try{
            generalLedger = new GeneralLedger();
            generalLedger.setAccountName(resultSet.getString("account_name"));
            generalLedger.setAcid1(resultSet.getLong("acid1"));
            generalLedger.setAfterTxn(resultSet.getDouble("aftrtxn"));
            generalLedger.setBranchName(resultSet.getString("branchname"));
            generalLedger.setCreditAmount(resultSet.getDouble("credit_amount"));
            generalLedger.setDebitAmount(resultSet.getDouble("debit_amount"));
            generalLedger.setOpeningBalance(resultSet.getDouble("openingbalance"));
            generalLedger.setReportName(resultSet.getString("report_header"));
            generalLedger.setReportId(resultSet.getString("reportid"));
            generalLedger.setEntryDate(resultSet.getDate("edate"));
            generalLedger.setDescription(resultSet.getString("description"));
            generalLedger.setTransactionId(resultSet.getString("transactionid"));

        }

        catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }
}
