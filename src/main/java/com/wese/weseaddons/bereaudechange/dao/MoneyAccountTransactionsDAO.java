package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.MoneyAccountTransactionsMapper;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountTransactions;
import com.wese.weseaddons.helper.Helper;
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
import com.wese.weseaddons.bereaudechange.pojo.*;


public class MoneyAccountTransactionsDAO implements FxDAOService{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private MoneyAccountTransactions moneyAccountTransactions ;

    public MoneyAccountTransactionsDAO(){}



    public MoneyAccountTransactionsDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public MoneyAccountTransactions find(long id){

        String sql = "SELECT * FROM m_fx_transactions where id=?";
        Object args[] = new Object[]{
                id
        };

        List<MoneyAccountTransactions> moneyAccountTransactionsList = jdbcTemplate.query(sql ,args ,new MoneyAccountTransactionsMapper());

        if (moneyAccountTransactionsList.isEmpty()){
            return null ;
        }

        MoneyAccountTransactions moneyAccountTransactions = moneyAccountTransactionsList.get(0);

        FxDeal fxDeal = (FxDeal)findForeignKey(new FxDealDAO(tenant) ,moneyAccountTransactions.getFxDeal().getId());
        
        if(fxDeal!=null){
            moneyAccountTransactions.setFxDeal(fxDeal);
        }

        MoneyAccount moneyAccount= (MoneyAccount)findForeignKey(new MoneyAccountDAO(tenant) ,moneyAccountTransactions.getMoneyAccount().getId());
        if(moneyAccount!=null){
            moneyAccountTransactions.setMoneyAccount(moneyAccount);
        } 

        return moneyAccountTransactions;

    }
    public List<MoneyAccountTransactions> findWhere(long id ,String column){


        String sql = String.format("SELECT * FROM m_fx_transactions where %s =?",column);

        Object args[] = new Object[]{
                id
        };

        List<MoneyAccountTransactions> moneyAccountTransactionsList = jdbcTemplate.query(sql ,args ,new MoneyAccountTransactionsMapper());

        if (moneyAccountTransactionsList.isEmpty()){
            return new ArrayList<>();
        }


        for(MoneyAccountTransactions m : moneyAccountTransactionsList){

            FxDeal fxDeal = (FxDeal)findForeignKey(new FxDealDAO(tenant) ,m.getFxDeal().getId());
            if(fxDeal!=null){
                m.setFxDeal(fxDeal);
            }

            MoneyAccount moneyAccount= (MoneyAccount)findForeignKey(new MoneyAccountDAO(tenant) ,m.getMoneyAccount().getId());
            if(moneyAccount!=null){
                m.setMoneyAccount(moneyAccount);
            }
        }

        return moneyAccountTransactionsList;

    }


    public List<MoneyAccountTransactions> findAll(){

        String sql = "SELECT * FROM m_fx_transactions";

        List<MoneyAccountTransactions> moneyAccountTransactionsList = jdbcTemplate.query(sql ,new MoneyAccountTransactionsMapper());

        if (moneyAccountTransactionsList.isEmpty()){
            return new ArrayList<>();
        }


        for(MoneyAccountTransactions m : moneyAccountTransactionsList){
          
            FxDeal fxDeal = (FxDeal)findForeignKey(new FxDealDAO(tenant) ,m.getFxDeal().getId());
            if(fxDeal!=null){
                m.setFxDeal(fxDeal);
            }

            MoneyAccount moneyAccount= (MoneyAccount)findForeignKey(new MoneyAccountDAO(tenant) ,m.getMoneyAccount().getId());
            if(moneyAccount!=null){
                m.setMoneyAccount(moneyAccount);
            }  
        }

        return moneyAccountTransactionsList;

    }

   
    public ObjectNode create(MoneyAccountTransactions moneyAccountTransactions){

        String sql ="insert into m_fx_transactions(fx_deal_id,t_account,money_account_id ,charges,amount,running_balance) values(? ,?,? ,?,? ,?)";
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,moneyAccountTransactions.getFxDeal().getId());
                    statement.setInt(2 ,moneyAccountTransactions.gettAccount().ordinal());
                    statement.setLong(3 ,moneyAccountTransactions.getMoneyAccount().getId());
                    statement.setDouble(4 ,moneyAccountTransactions.getCharges());
                    statement.setDouble(5 ,moneyAccountTransactions.getAmount());
                    statement.setDouble(6 ,moneyAccountTransactions.getRunningBalance());
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            e.printStackTrace();
            return Helper.statusNodes(false);

        }

        if(id!=0){

            ///we now need this id to create linkages to charges transaction mapper
            moneyAccountTransactions.setId(id);

            Runnable runnable = ()->{

                createChargeLinkage(moneyAccountTransactions);

            };

            OffshoreThread.run(runnable);

            return Helper.statusNodes(true) ;
        }

        return Helper.statusNodes(false) ;

    }


    public Object findForeignKey(FxDAOService fxDAOService , long id){
        
        Object object = fxDAOService.find(id);
        return object;

    }

    public void createChargeLinkage(MoneyAccountTransactions moneyAccountTransactions){

        MoneyAccount moneyAccount = moneyAccountTransactions.getMoneyAccount() ;

        TradingAlgorithm tradingAlgorithm = new TradingAlgorithm(tenant);
        List<Charges> chargesList = tradingAlgorithm.getActiveCharges(moneyAccount);

        for(Charges charges : chargesList){

            TransactionCharge transactionCharge = new TransactionCharge();
            transactionCharge.setCharges(charges);
            transactionCharge.setMoneyAccountTransactions(moneyAccountTransactions);

            TransactionChargeDAO transactionChargeDAO = new TransactionChargeDAO(tenant);
            transactionChargeDAO.create(transactionCharge);
        }

    }
}
