package com.wese.weseaddons.bereaudechange.dao;

import com.wese.weseaddons.bereaudechange.helper.ForeignKeyHelper;
import com.wese.weseaddons.bereaudechange.mapper.FxEquivalentMapper;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxEquivalent;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.jdbc.JdbcTemplateInit;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Consumer;

public class FxEquivalentDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    private Consumer<FxEquivalent> fxEquivalantConsumer = (e)->{

        FxDeal fxDeal = (FxDeal) ForeignKeyHelper.get(new FxDealDAO(tenant) ,e.getFxDeal().getId());
        TradingRates orignalTradingRates = fxDeal.getTradingRates();

        TradingRates conversionTradingRates = (TradingRates)ForeignKeyHelper.get(new TradingRatesDAO(tenant) ,e.getConversionTradingRates().getId());

        e.setFxDeal(fxDeal);
        e.setDealTradingRates(orignalTradingRates);
        e.setConversionTradingRates(conversionTradingRates);
    };


    public FxEquivalentDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public FxEquivalent find(long id){

        String sql = "SELECT * FROM m_fx_equivalent where id=?";

        Object args[] = new Object[]{
                id
        };

        List<FxEquivalent> fxEquivalantList = jdbcTemplate.query(sql ,args ,new FxEquivalentMapper());

        if (fxEquivalantList.isEmpty()){
            return null ;
        }


        fxEquivalantList.stream().forEach(fxEquivalantConsumer);

        return fxEquivalantList.get(0);

    }

    public FxEquivalent findWhere(long id ,String column){

        String sql = String.format("SELECT * FROM m_fx_equivalent where %s=?",column);

        Object args[] = new Object[]{
                id
        };

        List<FxEquivalent> fxEquivalantList = jdbcTemplate.query(sql ,args ,new FxEquivalentMapper());

        if (fxEquivalantList.isEmpty()){
            return null ;
        }

        fxEquivalantList.stream().forEach(fxEquivalantConsumer);

        return fxEquivalantList.get(0);

    }



    public List<FxEquivalent> findAll(){

        String sql = "SELECT * FROM m_fx_equivalant";

        List<FxEquivalent> fxEquivalantList = jdbcTemplate.query(sql ,new FxEquivalentMapper());

        if (fxEquivalantList.isEmpty()){
            return null ;
        }


        fxEquivalantList.stream().forEach(fxEquivalantConsumer);

        return fxEquivalantList;

    }



    public void create(FxEquivalent fxEquivalant){

        String sql= "insert into m_fx_equivalent(fx_deal_id ,conversion_trading_rates_id ,deal_trading_rates_id ,local_amount ,equivalant_amount) values (? ,? ,? ,? ,?)";
        try{

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,fxEquivalant.getFxDeal().getId());
                    statement.setLong(2 ,fxEquivalant.getConversionTradingRates().getId());
                    statement.setLong(3 ,fxEquivalant.getDealTradingRates().getId());
                    statement.setDouble(4 ,fxEquivalant.getLocalAmount());
                    statement.setDouble(5 ,fxEquivalant.getEquivalantAmount());
                    return statement;
                }
            }, holder);

        //    customId = holder.getKey().longValue();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
