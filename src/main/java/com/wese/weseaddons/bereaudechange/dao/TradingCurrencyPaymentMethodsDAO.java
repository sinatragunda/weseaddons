package com.wese.weseaddons.bereaudechange.dao;


import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.TradingCurrencyPaymentMethodsMapper;
import com.wese.weseaddons.bereaudechange.pojo.*;

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

public class TradingCurrencyPaymentMethodsDAO {
    
	private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    public TradingCurrencyPaymentMethodsDAO(){

    }

    public TradingCurrencyPaymentMethodsDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<TradingCurrencyPaymentMethodsRM> findAll(){

        String sql = "SELECT * FROM m_fx_trading_currency_payment_method_mapping";

        List<TradingCurrencyPaymentMethodsRM> tradingCurrencyPaymentMethodsRMList = jdbcTemplate.query(sql ,new TradingCurrencyPaymentMethodsMapper());

        if (tradingCurrencyPaymentMethodsRMList.isEmpty()){
            return new ArrayList<>();
        }

        for(TradingCurrencyPaymentMethodsRM m: tradingCurrencyPaymentMethodsRMList){

            long paymentMethodId = m.getPaymentMethod().getId();
            PaymentMethods paymentMethod = (PaymentMethods)findForeignKey(new PaymentMethodsDAO(tenant) ,paymentMethodId);
            m.setPaymentMethod(paymentMethod);
        }


        return tradingCurrencyPaymentMethodsRMList;

    }

    public TradingCurrencyPaymentMethodsRM find(long id){

        String sql = "SELECT * FROM m_fx_trading_currency_payment_method_mapping where id=?";

        Object object[]= new Object[]{
                id
        };

        List<TradingCurrencyPaymentMethodsRM> tradingCurrencyPaymentMethodsRMList = jdbcTemplate.query(sql ,object,new TradingCurrencyPaymentMethodsMapper());

        if (tradingCurrencyPaymentMethodsRMList.isEmpty()){
            return null;
        }

        for(TradingCurrencyPaymentMethodsRM m: tradingCurrencyPaymentMethodsRMList){

            long paymentMethodId = m.getPaymentMethod().getId();
            PaymentMethods paymentMethod = (PaymentMethods)findForeignKey(new PaymentMethodsDAO(tenant) ,paymentMethodId);
            m.setPaymentMethod(paymentMethod);
        }

        return tradingCurrencyPaymentMethodsRMList.get(0) ;

    }

    public List<TradingCurrencyPaymentMethodsRM> findWhere(long id){

        String sql = "SELECT * FROM m_fx_trading_currency_payment_method_mapping where trading_currency_id=?";

        Object object[]= new Object[]{
                id
        };

        List<TradingCurrencyPaymentMethodsRM> tradingCurrencyPaymentMethodsRMList = jdbcTemplate.query(sql ,object,new TradingCurrencyPaymentMethodsMapper());

        if (tradingCurrencyPaymentMethodsRMList.isEmpty()){
            return new ArrayList<>();
        }

        for(TradingCurrencyPaymentMethodsRM m: tradingCurrencyPaymentMethodsRMList){

            long paymentMethodId = m.getPaymentMethod().getId();
            PaymentMethods paymentMethod = (PaymentMethods)findForeignKey(new PaymentMethodsDAO(tenant) ,paymentMethodId);
            m.setPaymentMethod(paymentMethod);
        }


        return tradingCurrencyPaymentMethodsRMList;

    }

    public boolean deactivatePaymentMethod(long tradingCurrencyId ,long paymentMethodId ,boolean value){

        String sql = "UPDATE m_fx_trading_currency_payment_method_mapping SET active = ? where trading_currency_id =? AND payment_method_id = ?";

        Object object[]= new Object[]{
                value ,
                tradingCurrencyId ,
                paymentMethodId
        };

        int affectedRows = jdbcTemplate.update(sql ,object);

        if(affectedRows > 0){
            return true ;
        }

        return false ;

    }

    public boolean create(TradingCurrencyPaymentMethodsRM tradingCurrencyPaymentMethodsRM){

        String sql ="insert into m_fx_trading_currency_payment_method_mapping(trading_currency_id ,payment_method_id) values(? ,?)";
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, tradingCurrencyPaymentMethodsRM.getTradingCurrency().getId());
                    statement.setLong(2 , tradingCurrencyPaymentMethodsRM.getPaymentMethod().getId());


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
            return true ;
        }

        return false ;

    }

    public Object findForeignKey(FxDAOService fxDAOService , long id){

        Object object =  fxDAOService.find(id);
        return object ;
    }
}
