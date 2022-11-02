package com.wese.weseaddons.remittance.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxCashierDAO;
import com.wese.weseaddons.bereaudechange.dao.StandardCurrencyDAO;
import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.remittance.enumerations.REMITTANCE_TRANSACTION_STATE;
import com.wese.weseaddons.remittance.mapper.RemittanceTransactionMapper;
import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;
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

import com.wese.weseaddons.bereaudechange.pojo.*;

public class RemittanceTransactionDAO{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    private Consumer<RemittanceTransaction> remittanceTransactionConsumer = (remittanceTransaction)->{

        StandardCurrency standardCurrency = findForeignKey(remittanceTransaction.getStandardCurrency().getId());

        if(standardCurrency !=null){

            remittanceTransaction.setStandardCurrency(standardCurrency);
        }
        

        FxCashier fxCashierSender = (FxCashier) ForeignKeyHelper.get(new FxCashierDAO(tenant),remittanceTransaction.getFxCashierSender().getId());
        remittanceTransaction.setFxCashierSender(fxCashierSender);


        if(remittanceTransaction.getRemittanceTransactionState()==REMITTANCE_TRANSACTION_STATE.CLOSED){
            
            long fxCashierRecieverId =  remittanceTransaction.getFxCashierReciever().getId();
            FxCashier fxCashierReciever = (FxCashier) ForeignKeyHelper.get(new FxCashierDAO(tenant),fxCashierRecieverId);
            remittanceTransaction.setFxCashierSender(fxCashierReciever);
       
        }

        
    };

    public RemittanceTransactionDAO(){}

    public RemittanceTransactionDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public RemittanceTransaction find(long id){

        String sql = "SELECT * FROM m_rx_remittance where id=?";
        Object args[] = new Object[]{
                id
        };

        List<RemittanceTransaction> remittanceTransactionList = jdbcTemplate.query(sql ,args ,new RemittanceTransactionMapper());

        if (remittanceTransactionList.isEmpty()){
            return null ;
        }

        remittanceTransactionList.stream().forEach(remittanceTransactionConsumer);
        return remittanceTransactionList.get(0);

    }

    public RemittanceTransaction findWhereKey(String key){

        String sql = "SELECT * FROM m_rx_remittance where funds_key=?";
        Object args[] = new Object[]{
                key
        };

        List<RemittanceTransaction> remittanceTransactionList = jdbcTemplate.query(sql ,args ,new RemittanceTransactionMapper());

        if (remittanceTransactionList.isEmpty()){
            return null ;
        }

        remittanceTransactionList.stream().forEach(remittanceTransactionConsumer);
        return remittanceTransactionList.get(0);

    }


    public List<RemittanceTransaction> findAll(){

        String sql = "SELECT * FROM m_rx_remittance";

        List<RemittanceTransaction> remittanceTransactionList = jdbcTemplate.query(sql ,new RemittanceTransactionMapper());

        if (remittanceTransactionList.isEmpty()){
            return new ArrayList<>();
        }

        remittanceTransactionList.stream().forEach(remittanceTransactionConsumer);

        return remittanceTransactionList;

    }

    public ObjectNode closeTransaction(RemittanceTransaction remittanceTransaction){


        String sql = "UPDATE m_rx_remittance set state = ? ,fx_cashier_reciever_id = ? ,disbursed_date = ? WHERE id =?";

        Object args[] = new Object[]{
            REMITTANCE_TRANSACTION_STATE.CLOSED.ordinal() ,
            remittanceTransaction.getFxCashierReciever().getId() ,
            TimeHelper.dateNow(),
            remittanceTransaction.getId()    
        };
        
        int affectedRows = jdbcTemplate.update(sql ,args);

        if(affectedRows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false).put("message" ,Constants.failedUpdate);
    }

     public ObjectNode updateTransactionState(long id ,int state){


        String sql = "UPDATE m_rx_remittance set state = ? WHERE id =?";
        
        Object args[] = new Object[]{
            REMITTANCE_TRANSACTION_STATE.CLOSED.ordinal() ,
            id   
        };
        
        int affectedRows = jdbcTemplate.update(sql ,args);

        if(affectedRows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false).put("message" ,Constants.failedUpdate);
    }

    public ObjectNode create(RemittanceTransaction remittanceTransaction){

        String sql ="insert into m_rx_remittance(funds_key ,amount,commission,state ,remitter_sender ,remitter_reciever ,date ,fx_cashier_sender_id ,standard_currency_id) values(?,?,?,?,? ,? ,? ,? ,?)";
        
        FxCashierDAO fxCashierDAO = new FxCashierDAO(tenant);

        long cashierId = remittanceTransaction.getFxCashierSender().getId();
        FxCashier fxCashier = fxCashierDAO.findWhere(cashierId ,"id");
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,remittanceTransaction.getFundsKey());
                    statement.setDouble(2 ,remittanceTransaction.getAmount());
                    statement.setDouble(3 ,remittanceTransaction.getCommission());
                    statement.setInt(4 ,remittanceTransaction.getRemittanceTransactionState().ordinal());
                    statement.setString(5 ,remittanceTransaction.getRemitterSender().toString());
                    statement.setString(6 ,remittanceTransaction.getRemitterReciever().toString());
                    statement.setLong(7 ,remittanceTransaction.getTransactionDate());
                    statement.setLong(8 ,fxCashier.getId());
                    statement.setLong(9  ,remittanceTransaction.getStandardCurrency().getId());
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }

        if(id!=0){
            return Helper.statusNodes(true).put("id",id);
        }

        return Helper.statusNodes(false).put("message",Constants.failedTransaction);

    }



    public void delete(long id){

        String sql = "delete from m_rx_remittance where id=?";
        Object args[] = new Object[]{
                id
        };

        jdbcTemplate.update(sql ,args);

    }


    public StandardCurrency findForeignKey(long id){

        StandardCurrencyDAO standardCurrencyDAO = new StandardCurrencyDAO(tenant);
        return standardCurrencyDAO.find(id);

    }
}

