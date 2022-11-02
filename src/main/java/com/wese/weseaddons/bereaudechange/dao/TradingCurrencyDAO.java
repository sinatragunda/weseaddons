package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.fxrequest.OrganisationCurrencyRequest;
import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.mapper.TradingCurrencyMapper;
import com.wese.weseaddons.bereaudechange.pojo.FinancialInstrument;
import com.wese.weseaddons.bereaudechange.pojo.PaymentMethods;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrency;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrencyPaymentMethodsRM;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
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

public class TradingCurrencyDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    private Consumer<TradingCurrency> tradingCurrencyConsumer = (e)->{

        FinancialInstrument f = getForeignKey(e.getFinancialInstrument().getId());
        if(f!=null){
            e.setFinancialInstrument(f);
        }

        StandardCurrency standardCurrency = (StandardCurrency)ForeignKeyHelper.get(new StandardCurrencyDAO(tenant) , e.getStandardCurrency().getId());
        e.setStandardCurrency(standardCurrency);
        
        
        TradingCurrencyPaymentMethodsDAO tr = new TradingCurrencyPaymentMethodsDAO(tenant);
        List<TradingCurrencyPaymentMethodsRM > paymentMethodsRMList = tr.findWhere(e.getId());
        
        List<PaymentMethods> paymentMethodsList = new ArrayList<>();
        
        for(TradingCurrencyPaymentMethodsRM m : paymentMethodsRMList) {
        	
        	PaymentMethods p = m.getPaymentMethod();
        	paymentMethodsList.add(p);
        }
        
        e.setPaymentMethodsList(paymentMethodsList);
        
    };

  
    public TradingCurrencyDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<TradingCurrency> findAll(){

        String sql = "SELECT * FROM m_fx_trading_currency";

        List<TradingCurrency> tradingCurrencyList = jdbcTemplate.query(sql ,new TradingCurrencyMapper());

        if (tradingCurrencyList.isEmpty()){
            return new ArrayList<>();
        }

        tradingCurrencyList.stream().forEach(tradingCurrencyConsumer);

        return tradingCurrencyList;

    }

    public TradingCurrency find(long id){

        String sql = "SELECT * FROM m_fx_trading_currency where id=?";
        Object args[] = new Object[]{
                id
        };

        List<TradingCurrency> tradingCurrencyList = jdbcTemplate.query(sql ,args ,new TradingCurrencyMapper());

        if (tradingCurrencyList.isEmpty()){
            return null;
        }


        tradingCurrencyList.stream().forEach(tradingCurrencyConsumer);

        return tradingCurrencyList.get(0);

    }

    public boolean edit(TradingCurrency tradingCurrency){

        TradingCurrency defaultTradingCurrency = find(tradingCurrency.getId());

        if(tradingCurrency.getFinancialInstrument().getId()==0){
            tradingCurrency.setFinancialInstrument(defaultTradingCurrency.getFinancialInstrument());
        }

        if(tradingCurrency.getStandardCurrency().getId()==0){
            tradingCurrency.setStandardCurrency(defaultTradingCurrency.getStandardCurrency());
        }

        String sql = "UPDATE m_fx_trading_currency SET symbol =? ,standard_currency_id = ? ,name=?,description =? ,financial_instrument_id =? where id =?";

        Object[] args = new Object[]{
                tradingCurrency.getSymbol() ,
                tradingCurrency.getStandardCurrency().getId() ,
                tradingCurrency.getName() ,
                tradingCurrency.getDescription(),
                tradingCurrency.getFinancialInstrument().getId(),
                tradingCurrency.getId()
        };

        int affectedRows = jdbcTemplate.update(sql ,args);

        if(affectedRows > 0){
            return true ;
        }

        return false ;
    }


    /// should throw exception here when that value is greater than 3
    public ObjectNode create(TradingCurrency tradingCurrency){

        String symbol = tradingCurrency.getSymbol();

        if(!isValidSymbol(symbol)){
            return Helper.statusNodes(false).put("message" ,Constants.invalidTick);
        }

        String sql ="insert into m_fx_trading_currency(name ,symbol,added_date ,financial_instrument_id ,standard_currency_id, description) values(? ,?,? ,?,?,?)";
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,tradingCurrency.getName());
                    statement.setString(2 ,tradingCurrency.getSymbol());
                    long timestamp = TimeHelper.epochNow();
                    statement.setLong(3 ,timestamp);
                    statement.setLong(4 ,tradingCurrency.getFinancialInstrument().getId());
                    statement.setLong(5 ,tradingCurrency.getStandardCurrency().getId());
                    statement.setString(6,tradingCurrency.getDescription());

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
        	
        	tradingCurrency.setId(id);

            TradingCurrency tradingCurrency1 = find(id);
            
            System.out.println(tradingCurrency.getPaymentMethodsList().size());
            
            for(PaymentMethods paymentMethod : tradingCurrency.getPaymentMethodsList()){
            	
                TradingCurrencyPaymentMethodsRM tradingCurrencyPaymentMethodsRM = new TradingCurrencyPaymentMethodsRM(tradingCurrency ,paymentMethod);

                TradingCurrencyPaymentMethodsDAO tradingCurrencyPaymentMethodsDAO =new TradingCurrencyPaymentMethodsDAO(tenant);
                tradingCurrencyPaymentMethodsDAO.create(tradingCurrencyPaymentMethodsRM);
            }
            
            
            OrganisationCurrencyRequest organisationCurrencyRequest = new OrganisationCurrencyRequest(tenant);
            organisationCurrencyRequest.create(tradingCurrency1.getStandardCurrency());
            return Helper.statusNodes(true).put("id",id) ;
        }


        return Helper.statusNodes(false).put("message" , "Failed to create trading currency .Please try again");

    }

    public FinancialInstrument getForeignKey(long id){

        FinancialInstrumentsDAO financialInstrumentsDAO = new FinancialInstrumentsDAO(tenant);
        return financialInstrumentsDAO.find(id);

    }

    public boolean isValidSymbol(String tick){

        if(tick.length() != 3){
            return false ;
        }

        return true ;
    }
}
