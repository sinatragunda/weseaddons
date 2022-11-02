/*Created by Sinatra Gunda
  At 10:16 on 9/30/2020 */

package com.wese.weseaddons.employeerelations.mapper;

import com.wese.weseaddons.employeerelations.pojo.BankAccount;
import com.wese.weseaddons.employeerelations.pojo.DisbursedLoanBankAccount;
import com.wese.weseaddons.pojo.Loan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DisbursedLoanBankAccountMapper implements RowMapper<DisbursedLoanBankAccount>{


    @Override
    public DisbursedLoanBankAccount mapRow(ResultSet resultSet, int i) {

        DisbursedLoanBankAccount disbursedLoanBankAccount = null ;
        try{
            disbursedLoanBankAccount = new DisbursedLoanBankAccount();

            disbursedLoanBankAccount.setId(resultSet.getLong("id"));
            Long bankAccountId = resultSet.getLong("bank_account_id");

            disbursedLoanBankAccount.setBankAccount(new BankAccount(bankAccountId));

            Long loanId = resultSet.getLong("loan_id");
            disbursedLoanBankAccount.setLoan(new Loan(loanId));
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return disbursedLoanBankAccount;
    }
}
