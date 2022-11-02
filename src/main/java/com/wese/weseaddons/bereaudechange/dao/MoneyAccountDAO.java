package com.wese.weseaddons.bereaudechange.dao;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.MoneyAccountMapper;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.helper.Constants;
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

public class MoneyAccountDAO implements FxDAOService{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private MoneyAccount moneyAccount ;

    public MoneyAccountDAO(){}



    public MoneyAccountDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public MoneyAccount find(long id){

        String sql = "SELECT * FROM m_fx_money_account where id=?";
        Object args[] = new Object[]{
                id
        };

        List<MoneyAccount> moneyAccountList = jdbcTemplate.query(sql ,args ,new MoneyAccountMapper());

        if (moneyAccountList.isEmpty()){
            return null ;
        }

        MoneyAccount moneyAccount = moneyAccountList.get(0); ///moneyAccount.setChargesList(new ArrayList<>());

        moneyAccount.setChargesList(findCharges(moneyAccountList));
        TradingCurrency t = findForeignKey(moneyAccount.getTradingCurrency().getId());

        if(t!=null){

            moneyAccount.setTradingCurrency(t);

        }

        return moneyAccount;

    }

      public MoneyAccount findWhere(long id ,String column){

        String sql = String.format("SELECT * FROM m_fx_money_account where %s =?",column);
        
        Object args[] = new Object[]{
                id
        };

        List<MoneyAccount> moneyAccountList = jdbcTemplate.query(sql ,args ,new MoneyAccountMapper());

        if (moneyAccountList.isEmpty()){
            return null ;
        }

        MoneyAccount moneyAccount = moneyAccountList.get(0); ///moneyAccount.setChargesList(new ArrayList<>());

        moneyAccount.setChargesList(findCharges(moneyAccountList));
        TradingCurrency t = findForeignKey(moneyAccount.getTradingCurrency().getId());

        if(t!=null){

            moneyAccount.setTradingCurrency(t);

        }

        return moneyAccount;

    }


    public List<MoneyAccount> findAll(){

        String sql = "SELECT * FROM m_fx_money_account";

        List<MoneyAccount> moneyAccountList = jdbcTemplate.query(sql ,new MoneyAccountMapper());

        if (moneyAccountList.isEmpty()){
            return new ArrayList<>();
        }


        for(MoneyAccount m : moneyAccountList){

            m.setChargesList(findCharges(moneyAccountList));
            TradingCurrency t = findForeignKey(m.getTradingCurrency().getId());

            if(t!=null){
                m.setTradingCurrency(t);
            }
        }


        return moneyAccountList;

    }

    public void deactivate(long id ,boolean value){

        String sql = "update m_fx_money_account set active= ? where id=?";
        Object args[] = new Object[]{
                value ,
                id
        };

        int rows = jdbcTemplate.update(sql ,args);

    }


    public boolean transact(MoneyAccount moneyAccount ,double amount){

        long id = moneyAccount.getId() ;
        double balance =amount ;

        boolean updated = false ;
        String sql = "update m_fx_money_account set balance = ? where id = ?";

        Object args[] = new Object[]{
                balance ,
                moneyAccount.getId()
        } ;

        int row = jdbcTemplate.update(sql ,args);

        if(row > 0){
            updated = true ;
        }

        return updated;

    }

    public ObjectNode edit(MoneyAccount moneyAccount){

        String sql = "UPDATE m_fx_money_account set name = ? ,account_number = ? ,lower_limit = ? ,upper_limit = ? where id = ?";

        Object args[] = new Object[]{
                moneyAccount.getName(),
                moneyAccount.getAccountNumber(),
                moneyAccount.getLowerLimit(),
                moneyAccount.getUpperLimit(),
                moneyAccount.getId()
        };

        int affectedRows = jdbcTemplate.update(sql ,args);
        if(affectedRows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);

    }

    public ObjectNode create(MoneyAccount moneyAccount){

        String sql ="insert into m_fx_money_account(name ,account_number,upper_limit,lower_limit,balance ,trading_currency_id ,money_account_type) values(? ,?,? ,?,?,? ,?)";
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,moneyAccount.getName());
                    statement.setString(2 ,moneyAccount.getAccountNumber());
                    statement.setDouble(3 ,moneyAccount.getUpperLimit());
                    statement.setDouble(4 ,moneyAccount.getLowerLimit());
                    statement.setDouble(5 ,moneyAccount.getBalance());
                    statement.setLong(6 ,moneyAccount.getTradingCurrency().getId());
                    statement.setInt(7 ,moneyAccount.getMoneyAccountType().ordinal());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return Helper.statusNodes(false);

        }

        if(id!=0){
            
            moneyAccount.setId(id);

            for(Charges c : moneyAccount.getChargesList()){

                MoneyAccountChargesRM moneyAccountChargesRM = new MoneyAccountChargesRM(moneyAccount ,c);

                MoneyAccountChargesDAO moneyAccountChargesDAO =new MoneyAccountChargesDAO(tenant);
                moneyAccountChargesDAO.create(moneyAccountChargesRM);
            }
            return Helper.statusNodes(true).put("id",id) ;
        }

        return Helper.statusNodes(false).put("message" , Constants.failedCreate) ;

    }

    public List<Charges> findCharges(List<MoneyAccount> moneyAccountList){

        List<Charges> chargesList = new ArrayList<>();

        for(MoneyAccount m : moneyAccountList){

            MoneyAccountChargesDAO moneyAccountChargesDAO =new MoneyAccountChargesDAO(tenant);
            List<MoneyAccountChargesRM> moneyAccountChargesRMList = moneyAccountChargesDAO.findWhere(m.getId());

            for (MoneyAccountChargesRM moneyAccountChargesRM : moneyAccountChargesRMList){

                long id  = moneyAccountChargesRM.getCharges().getId();
                ChargesDAO chargesDAO = new ChargesDAO(tenant);
                Charges charges = chargesDAO.find(id);

                if(charges !=null){
                    chargesList.add(charges);
                }
            }
        }
        return chargesList ;
    }

    public TradingCurrency findForeignKey(long id){


        TradingCurrencyDAO tradingCurrencyDAO = new TradingCurrencyDAO(tenant);
        return tradingCurrencyDAO.find(id);

    }
    
//    public double findProfitLoss(){
//    	
//    	String sql = "SELECT * FROM m_fx_money_account where id=?";
//        Object args[] = new Object[]{
//                id
//        };
//
//        List<MoneyAccount> moneyAccountList = jdbcTemplate.query(sql ,args ,new MoneyAccountMapper());
//    	
//    	return 500;
//    } 
}
