package com.wese.weseaddons.bereaudechange.dao;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.action.TradingRatesAction;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.TradingRatesMapper;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.wese.weseaddons.bereaudechange.pojo.*;

public class TradingRatesDAO implements FxDAOService{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private long liveRateId = 0 ;


    private Consumer<TradingRates> tradingRatesConsumer = (e)->{

        long id = e.getCurrencyPair().getId();
        CurrencyPair currencyPair = (CurrencyPair) ForeignKeyHelper.get(new CurrencyPairDAO(tenant),id);
        if(currencyPair==null){
            return;
        }
        e.setCurrencyPair(currencyPair);
    };

    private Predicate<TradingRates> nullMapper = (e)->{
        if(e.getId()==0){
            return false ;
        }

        return true;
    };
    
    public TradingRatesDAO(){}


    public TradingRatesDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public TradingRates find(long id){

        String sql = "SELECT * FROM m_fx_trading_rates where id=?";
        Object args[] = new Object[]{
                id
        };

        List<TradingRates> tradingRatesList = jdbcTemplate.query(sql ,args ,new TradingRatesMapper());

        if (tradingRatesList.isEmpty()){
            return null ;
        }

        tradingRatesList.stream().forEach(tradingRatesConsumer);
        return tradingRatesList.get(0);

    }

    public List<TradingRates> findWhere(long id,String column){

        String sql = String.format("SELECT * FROM m_fx_trading_rates where %s=?",column);
        Object args[] = new Object[]{
                id
        };

        List<TradingRates> tradingRatesList = jdbcTemplate.query(sql ,args ,new TradingRatesMapper());

        if (tradingRatesList.isEmpty()){
            return new ArrayList<>() ;
        }

        tradingRatesList.stream().forEach(tradingRatesConsumer);

        return tradingRatesList.stream().filter(nullMapper).collect(Collectors.toList());

    }

    public List<TradingRates> findRatesWhereCurrencyPairId(long currencyId){


        String sql = "SELECT * FROM m_fx_trading_rates WHERE currency_pair_id= ?";

        Object args[] = new Object[]{
                currencyId
        };


        List<TradingRates> tradingRatesList = jdbcTemplate.query(sql ,args ,new TradingRatesMapper());

        if(tradingRatesList.isEmpty()){
            return new ArrayList<>();
        }

        tradingRatesList.stream().forEach(tradingRatesConsumer);

        return tradingRatesList;

    }

      public ObjectNode findRatesWherePairMatch(long baseCurrencyId,long quoteCurrencyId){

        MoneyAccountDAO fxMoneyAccountDAO = new MoneyAccountDAO(tenant);
        TradingRatesAction tradingRatesAction = new TradingRatesAction(tenant);
        
        MoneyAccount baseMoneyAccount = fxMoneyAccountDAO.findWhere(baseCurrencyId,"trading_currency_id");
        MoneyAccount quoteMoneyAccount = fxMoneyAccountDAO.findWhere(quoteCurrencyId,"trading_currency_id");


        CurrencyPairDAO currencyPairDAO = new CurrencyPairDAO(tenant);
        
        CurrencyPair currencyPair = null ;
        
        int state  = 0;

        currencyPair = currencyPairDAO.findWherePairMatch(baseMoneyAccount.getId(),quoteMoneyAccount.getId());

        if(currencyPair==null){

            currencyPair = currencyPairDAO.findWherePairMatch(quoteMoneyAccount.getId(),baseMoneyAccount.getId());
            
            if(currencyPair==null){

                return null ;
            }

            state = 1 ;

        }


        List<TradingRates> tradingRatesList = findWhere(currencyPair.getId(),"currency_pair_id");
        if(tradingRatesList.isEmpty()){
            return null ;
        }

        tradingRatesList.stream().forEach(tradingRatesConsumer);

        TradingRates tradingRates = null ;

        tradingRates = tradingRatesList.get(0);

        if(tradingRatesList.size() > 1){
        
            int index = tradingRatesList.size()-1;
            tradingRates = tradingRatesList.get(index);
        }

        ObjectNode objectNode = tradingRatesAction.createNode(tradingRates);
        objectNode.put("state" ,state);
        return objectNode ;
    
    }

    public List<TradingRates> findTrend(long id){


        TradingRates tradingRates = find(id);

        if(tradingRates==null){
            return new ArrayList<>();
        }


        String sql = "SELECT * FROM m_fx_trading_rates where currency_pair_id =?";

        Object args[] = new Object[]{
                tradingRates.getCurrencyPair().getId()
        };

        List<TradingRates> tradingRatesList = jdbcTemplate.query(sql ,args ,new TradingRatesMapper());

        if (tradingRatesList.isEmpty()){
            return null ;
        }

        tradingRatesList.stream().forEach(tradingRatesConsumer);
        return tradingRatesList;

    }

    public ObjectNode volumesTrade(long id){

        TradingRates tradingRates = find(id);

        if(tradingRates==null){

            return Helper.statusNodes(false);
        }


        String sql = "SELECT * FROM m_fx_trading_rates where currency_pair_id =?";

        Object args[] = new Object[]{
                tradingRates.getCurrencyPair().getId()
        };

        List<TradingRates> tradingRatesList = jdbcTemplate.query(sql ,args ,new TradingRatesMapper());

        if (tradingRatesList.isEmpty()){
            return Helper.statusNodes(false);
        }

        tradingRatesList.stream().forEach(tradingRatesConsumer);

        ObjectNode objectNode = Helper.createObjectNode();
        ArrayNode arrayNode = Helper.createArrayNode();

        for(TradingRates m : tradingRatesList){

            FxDealDAO fxDealDAO = new FxDealDAO(tenant);
            List<FxDeal> fxDealList = fxDealDAO.findWhere(m.getId() ,"trading_rates_id");

            double baseAmount = 0 ;
            double quoteAmount = 0 ;

            for(FxDeal fxDeal : fxDealList){

                baseAmount += fxDeal.getBlotter().getBaseAmount();
                quoteAmount += fxDeal.getBlotter().getQuoteAmount();
            }

            String rateToString = String.valueOf(m.getRate());

            ObjectNode objectNode1 = Helper.createObjectNode();

            objectNode1.put("label" ,rateToString);
            objectNode1.put("quoteAmount" , TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL ,quoteAmount));
            objectNode1.put("baseAmount" , TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL ,baseAmount));
            arrayNode.add(objectNode1);
        }

        objectNode.putPOJO("pageItems" ,arrayNode);
        objectNode.put("currencyPair" ,tradingRates.getCurrencyPair().getTick());

        return objectNode;

    }

    public List<TradingRates> findAll(){

        String sql = "SELECT * FROM m_fx_trading_rates";

        List<TradingRates> tradingRatesList = jdbcTemplate.query(sql ,new TradingRatesMapper());

        if (tradingRatesList.isEmpty()){
            return new ArrayList<>();
        }

        tradingRatesList.stream().forEach(tradingRatesConsumer);
        return tradingRatesList;

    }

    public boolean create(TradingRates tradingRates){

        String sql ="insert into m_fx_trading_rates(rate ,open_time ,currency_pair_id ,active ,sell_rate) values(? ,?,? ,? ,?)";
        
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1,tradingRates.getRate());
                    statement.setLong(2, Helper.timestampNow());
                    statement.setLong(3 ,tradingRates.getCurrencyPair().getId());
                    statement.setBoolean(4 ,true);
                    statement.setDouble(5 ,tradingRates.getSellRate());
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }

        if(id!=0){

            LiveRatesDAO liveRatesDAO = new LiveRatesDAO(tenant);
            liveRatesDAO.create(id ,liveRateId ,tradingRates.isHasGained() ,tradingRates.getPercentageChange());
            return true ;
        }

        return false ;

    }

    public void update(TradingRates tradingRates ,long liveRateId){

        long id = tradingRates.getId();
        this.liveRateId = liveRateId ;

        setInActive(id);
        create(tradingRates);

    }

    public void setInActive(long id){

        long closeTime = Helper.timestampNow() ;

        String sql = "update m_fx_trading_rates set active = ? ,close_time = ? where id=?";
        Object args[] = new Object[]{
                false ,
                closeTime ,
                id
        };

        jdbcTemplate.update(sql ,args);

    }
}

