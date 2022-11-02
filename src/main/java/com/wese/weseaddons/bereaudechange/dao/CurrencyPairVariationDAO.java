package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;

import com.wese.weseaddons.bereaudechange.mapper.CurrencyPairVariationMapper;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPairVariation;

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

public class CurrencyPairVariationDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private com.wese.weseaddons.bereaudechange.pojo.CurrencyPair CurrencyPair ;


    public CurrencyPairVariationDAO(){}

    public CurrencyPairVariationDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }


    public List<CurrencyPairVariation> findWhere(long currencyPairId){

        String sql = "SELECT * FROM m_fx_currency_pair_variation where currency_pair_id=?";
        Object args[] = new Object[]{
                currencyPairId
        };


        List<CurrencyPairVariation> currencyPairList = jdbcTemplate.query(sql ,args ,new CurrencyPairVariationMapper());

        if (currencyPairList.isEmpty()){

            return new ArrayList<>() ;
        }


        return currencyPairList;

    }


//    public ObjectNode update(CurrencyPair currencyPair){
//
//        CurrencyPair defaultCurrencyPair = find(currencyPair.getId());
//
//        if(currencyPair.getQuoteCurrencyMoneyAccount().getId()==0){
//            currencyPair.setQuoteCurrencyMoneyAccount(defaultCurrencyPair.getQuoteCurrencyMoneyAccount());
//        }
//
//        if(currencyPair.getBaseCurrencyMoneyAccount().getId()==0){
//            currencyPair.setBaseCurrencyMoneyAccount(defaultCurrencyPair.getBaseCurrencyMoneyAccount());
//        }
//
//
//        String sql = "UPDATE m_fx_currency_pair set tick = ? ,base_currency_id = ? ,quote_currency_id =? ,rounding_rule =? ,mark_up = ? WHERE id =?";
//
//        Object args[] = new Object[]{
//                StringHelper.makeTick(currencyPair ,tenant),
//                currencyPair.getBaseCurrencyMoneyAccount().getId(),
//                currencyPair.getQuoteCurrencyMoneyAccount().getId() ,
//                currencyPair.getRoundingRule().ordinal(),
//                currencyPair.getMarkUp(),
//                currencyPair.getId()
//        };
//
//        int affectedRows = jdbcTemplate.update(sql ,args);
//
//        if(affectedRows > 0){
//            return Helper.statusNodes(true);
//        }
//
//        return Helper.statusNodes(false).put("message" , Constants.failedUpdate);
//    }

    public ObjectNode create(CurrencyPairVariation currencyPairVariation){
        
        
        System.out.println(currencyPairVariation);


        String sql ="insert into m_fx_currency_pair_variation(currency_pair_id ,value,exchange_variation) values(? ,?,?)";

        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,currencyPairVariation.getCurrencyPair().getId());
                    statement.setDouble(2 ,currencyPairVariation.getValue());
                    statement.setInt(3 ,currencyPairVariation.getExchangeVariation().ordinal());

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
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);

    }



    public void delete(long id){

        String sql = "UPDATE m_fx_currency_pair SET id = ? WHERE id=?";
        Object args[] = new Object[]{
                id,
                id
        };

        jdbcTemplate.update(sql ,args);

    }


}

