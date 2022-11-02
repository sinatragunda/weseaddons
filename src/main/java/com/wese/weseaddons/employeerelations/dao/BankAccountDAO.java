/*Created by Sinatra Gunda
  At 02:34 on 9/30/2020 */

package com.wese.weseaddons.employeerelations.dao;

import com.wese.weseaddons.cache.services.Cachable;
import com.wese.weseaddons.daofactory.DAOQuery;
import com.wese.weseaddons.employeerelations.cache.EmployeeRelationsCache;
import com.wese.weseaddons.employeerelations.mapper.BankAccountMapper;
import com.wese.weseaddons.employeerelations.mapper.DisbursedLoanBankAccountMapper;
import com.wese.weseaddons.employeerelations.mapper.EmployerMapper;
import com.wese.weseaddons.employeerelations.pojo.*;
import com.wese.weseaddons.helper.ListHelper;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BankAccountDAO {


    private JdbcTemplate jdbcTemplate;
    private String tenantIdentifier;
    private BankAccount bankAccount;

    public BankAccountDAO(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
        jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenantIdentifier);
        bankAccount = new BankAccount();
    }

    public List<BankAccount> findWhere(String column ,Long clientId) {

        String sql = String.format("SELECT * FROM %s WHERE %s = ?",bankAccount.getSchema() ,column);
        Object args[]= new Object[]{
            clientId
        };
        List<BankAccount> bankAccountList = jdbcTemplate.query(sql ,args ,new BankAccountMapper());
        return bankAccountList ;

    }

    public DisbursedLoanBankAccount findDisbursedLoanBankAccount(Long loanId) {

        DisbursedLoanBankAccount disbursedLoanBankAccount = new DisbursedLoanBankAccount();

        String sql = String.format("SELECT * FROM %s WHERE loan_id = ?",disbursedLoanBankAccount.getSchema());
        Object args[]= new Object[]{
                loanId
        };

        List<DisbursedLoanBankAccount> disbursedLoanBankAccountList = jdbcTemplate.query(sql ,args ,new DisbursedLoanBankAccountMapper());
        if(!disbursedLoanBankAccountList.isEmpty()){
            disbursedLoanBankAccountList.stream().forEach((e)->{

                List<BankAccount> bList = findWhere("id",e.getBankAccount().getId());
                BankAccount bankAccount = ListHelper.get(bList ,0);
                e.setBankAccount(bankAccount);
            });
        }

        return ListHelper.get(disbursedLoanBankAccountList ,0) ;

    }


    public Long createDisbursedLoanBankAccount(DisbursedLoanBankAccount disbursedLoanBankAccount){

        String sql = String.format("INSERT INTO %s(bank_account_id ,loan_id) VALUES(? , ?)", new DisbursedLoanBankAccount().getSchema());
        Long id = null ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,disbursedLoanBankAccount.getBankAccount().getId());
                    statement.setLong(2,disbursedLoanBankAccount.getLoan().getId());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return id ;
    }

    public Boolean deactivateBankAccount(BankAccount bankAccount){

        String sql = String.format("UPDATE %s SET active = ? WHERE id =? ",bankAccount.getSchema());

        Object args[] = new Object[]{
            false ,
            bankAccount.getId()
        };

        int rows = jdbcTemplate.update(sql ,args);
        boolean status = rows==0 ? false : true ;
        return status ;
    }



    public Long create(BankAccount bankAccount){

        String sql = String.format("INSERT INTO %s(bank_name ,branch,account_number ,account_name ,client_id) VALUES(? , ? ,? ,? ,?)",bankAccount.getSchema());
        Long id = null ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,bankAccount.getBankName());
                    statement.setString(2,bankAccount.getBranch());
                    statement.setString(3,bankAccount.getAccountNumber());
                    statement.setString(4,bankAccount.getAccountName());
                    statement.setLong(5 ,bankAccount.getClient().getId());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return id ;
    }
}
