/*Created by Sinatra Gunda
  At 10:16 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.ActiveLoans;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveLoansMapper implements MapperInterface{


    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        ActiveLoans activeLoans = null ;

        try{
            activeLoans = new ActiveLoans();
            activeLoans.setOfficeName(resultSet.getString("Office Name"));
            activeLoans.setName(resultSet.getString("Client"));
            activeLoans.setCurrency(resultSet.getString("Currency"));
            activeLoans.setInterestPaid(resultSet.getDouble("Interest Repaid"));
            activeLoans.setLoanAccountNumber(resultSet.getString("Loan Account No"));
            activeLoans.setLoanId(resultSet.getLong("Loan Id"));
            activeLoans.setLoanOfficer(resultSet.getString("Loan Officer"));
            activeLoans.setMaturedOn(resultSet.getDate("Expected Matured On"));
            activeLoans.setDescription(resultSet.getString("Description"));
            activeLoans.setDisbursedOn(resultSet.getDate("Disbursed Date"));
            activeLoans.setEmployerName(resultSet.getString("Employer Name"));


            activeLoans.setFeesOutstanding(resultSet.getDouble("Fees Outstanding"));
            activeLoans.setFeesOverdue(resultSet.getDouble("Fees Overdue"));
            activeLoans.setFeesRepaid(resultSet.getDouble("Fees Repaid"));

            activeLoans.setGender(resultSet.getString("Gender"));
            activeLoans.setInterestOverdue(resultSet.getDouble("Interest Overdue"));
            activeLoans.setInterestRate(resultSet.getDouble("Annual Nominal Interest Rate"));
            activeLoans.setPenaltiesDue(resultSet.getDouble("Penalties Overdue"));
            activeLoans.setPenaltiesOutstanding(resultSet.getDouble("Penalties Outstanding"));
            activeLoans.setPrincipalAmount(resultSet.getDouble("Loan Amount"));
            activeLoans.setPrincipalPaid(resultSet.getDouble("Principal Repaid"));
            activeLoans.setPrincipalOutstanding(resultSet.getDouble("Principal Outstanding"));
            activeLoans.setPrincpalOverdue(resultSet.getDouble("Principal Overdue"));

            activeLoans.setTotalDue(resultSet.getDouble("Total Due"));
            activeLoans.setAgingBrack(resultSet.getString("Aging Brack"));
            activeLoans.setInterestOutstanding(resultSet.getDouble("Interest Outstanding"));


        }
        catch (SQLException s){
            s.printStackTrace();
        }
        return activeLoans ;

    }
}
