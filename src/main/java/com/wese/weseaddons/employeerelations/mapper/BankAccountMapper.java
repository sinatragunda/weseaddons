/*Created by Sinatra Gunda
  At 02:22 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.mapper;

import com.wese.weseaddons.employeerelations.pojo.BankAccount;
import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.pojo.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountMapper implements RowMapper<BankAccount> ,MapperInterface {


    @Override
    public BankAccount mapRow(ResultSet resultSet, int i) {

        BankAccount bankAccount = null ;
        try{
            bankAccount = new BankAccount();
            bankAccount.setId(resultSet.getLong("id"));
            bankAccount.setAccountName(resultSet.getString("account_name"));
            bankAccount.setBankName(resultSet.getString("bank_name"));
            bankAccount.setAccountNumber(resultSet.getString("account_number"));
            bankAccount.setBranch(resultSet.getString("branch"));
            bankAccount.setActive(resultSet.getBoolean("active"));

            Long clientId = resultSet.getLong("client_id");
            bankAccount.setClient(new Client(clientId));

        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return bankAccount;
    }
}
