package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.FxValidationHelper;
import com.wese.weseaddons.bereaudechange.helper.StringHelper;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.CurrencyPairMapper;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
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
import java.util.function.Consumer;

import com.wese.weseaddons.bereaudechange.pojo.*;

public class CurrencyPairDAO implements FxDAOService{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private CurrencyPairVariationDAO currencyPairVariationDAO ;



    private Consumer<CurrencyPair> currencyPairConsumer = (currencyPair)->{

        MoneyAccount baseCurrency = findForeignKey(currencyPair.getBaseCurrencyMoneyAccount().getId());
        MoneyAccount quoteCurrency = findForeignKey(currencyPair.getQuoteCurrencyMoneyAccount().getId());

        if(baseCurrency!=null && quoteCurrency!=null){

            currencyPair.setBaseCurrencyMoneyAccount(baseCurrency);
            currencyPair.setQuoteCurrencyMoneyAccount(quoteCurrency);
        }
        
        
        List<CurrencyPairVariation> currencyPairVariationList = currencyPairVariationDAO.findWhere(currencyPair.getId());
        currencyPair.setCurrencyPairVariationList(currencyPairVariationList);

    };

    public CurrencyPairDAO(){}

    public CurrencyPairDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
        this.currencyPairVariationDAO = new CurrencyPairVariationDAO(tenant);
    }

    public CurrencyPair find(long id){

        String sql = "SELECT * FROM m_fx_currency_pair where id=?";
        Object args[] = new Object[]{
                id
        };

        List<CurrencyPair> currencyPairList = jdbcTemplate.query(sql ,args ,new CurrencyPairMapper());

        if (currencyPairList.isEmpty()){
            return null ;
        }

        currencyPairList.stream().forEach(currencyPairConsumer);
        return currencyPairList.get(0);

    }

    public CurrencyPair findWherePairMatch(long baseCurrencyId ,long quoteCurrencyId){

        String sql = "SELECT * FROM m_fx_currency_pair WHERE base_currency_id=? AND quote_currency_id = ?";
        Object args[] = new Object[]{
                baseCurrencyId ,
                quoteCurrencyId
        };

        List<CurrencyPair> currencyPairList = jdbcTemplate.query(sql ,args ,new CurrencyPairMapper());

        if (currencyPairList.isEmpty()){
            return null ;
        }

        currencyPairList.stream().forEach(currencyPairConsumer);
        return currencyPairList.get(0);

    }

    public List<CurrencyPair> findAll(){

        String sql = "SELECT * FROM m_fx_currency_pair";

        List<CurrencyPair> currencyPairList = jdbcTemplate.query(sql ,new CurrencyPairMapper());

        if (currencyPairList.isEmpty()){
            return new ArrayList<>();
        }

       currencyPairList.stream().forEach(currencyPairConsumer);

        return currencyPairList;

    }

    public ObjectNode update(CurrencyPair currencyPair){

        CurrencyPair defaultCurrencyPair = find(currencyPair.getId());

        if(currencyPair.getQuoteCurrencyMoneyAccount().getId()==0){
            currencyPair.setQuoteCurrencyMoneyAccount(defaultCurrencyPair.getQuoteCurrencyMoneyAccount());
        }

        if(currencyPair.getBaseCurrencyMoneyAccount().getId()==0){
            currencyPair.setBaseCurrencyMoneyAccount(defaultCurrencyPair.getBaseCurrencyMoneyAccount());
        }


        String sql = "UPDATE m_fx_currency_pair set tick = ? ,base_currency_id = ? ,quote_currency_id =? ,rounding_rule =? ,mark_up = ? WHERE id =?";

        Object args[] = new Object[]{
                StringHelper.makeTick(currencyPair ,tenant),
                currencyPair.getBaseCurrencyMoneyAccount().getId(),
                currencyPair.getQuoteCurrencyMoneyAccount().getId() ,
                currencyPair.getRoundingRule().ordinal(),
                currencyPair.getMarkUp(),
                currencyPair.getId()
        };

        int affectedRows = jdbcTemplate.update(sql ,args);

        if(affectedRows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false).put("message" ,Constants.failedUpdate);
    }

    public ObjectNode create(CurrencyPair currencyPair){

        String tick = currencyPair.getTick();

        if(!isValidTick(tick)){

            return Helper.statusNodes(false).put("message" , Constants.invalidTick);
        }

        if(FxValidationHelper.clientPairExists(this ,currencyPair)){

            return Helper.statusNodes(false).put("message" ,"Currency pair already exist ,please try to edit that one to fit your needs");
        }

        String sql ="insert into m_fx_currency_pair(tick ,base_currency_id,quote_currency_id,rounding_rule ,mark_up) values(? ,?,? ,?,?)";
        
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,currencyPair.getTick());
                    statement.setLong(2 ,currencyPair.getBaseCurrencyMoneyAccount().getId());
                    statement.setLong(3 ,currencyPair.getQuoteCurrencyMoneyAccount().getId());
                    statement.setLong(4 ,currencyPair.getRoundingRule().ordinal());
                    statement.setDouble(5 ,currencyPair.getMarkUp());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

            TradingRates tradingRates = new TradingRates(currencyPair.getRate() ,currencyPair.getSellRate());
            tradingRates.setCurrencyPair(new CurrencyPair(id));

            TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
            tradingRatesDAO.create(tradingRates);

            currencyPair.setId(id);

            List<CurrencyPairVariation> currencyPairVariationList = currencyPair.getCurrencyPairVariationList();

            for(CurrencyPairVariation currencyPairVariation : currencyPairVariationList){
                currencyPairVariation.setCurrencyPair(currencyPair);
                currencyPairVariationDAO.create(currencyPairVariation);
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }

        if(id!=0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);

    }

    public boolean isValidTick(String tick){

        if(!tick.contains("/")){
            return false ;
        }

        if(tick.length() != 7){
            return false ;
        }

        return true ;
    }

    public void delete(long id){

        String sql = "delete from m_fx_currency_pair where id=?";
        Object args[] = new Object[]{
                id
        };

        jdbcTemplate.update(sql ,args);

    }


    public MoneyAccount findForeignKey(long id){

        MoneyAccountDAO moneyAccountDAO = new MoneyAccountDAO(tenant);
        return moneyAccountDAO.find(id);

    }
}

