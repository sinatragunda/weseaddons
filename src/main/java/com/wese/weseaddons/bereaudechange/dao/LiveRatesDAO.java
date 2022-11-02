package com.wese.weseaddons.bereaudechange.dao;

import com.wese.weseaddons.bereaudechange.mapper.LiveRatesMapper;
import com.wese.weseaddons.bereaudechange.pojo.LiveRates;
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

public class LiveRatesDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private LiveRates LiveRates ;

    public LiveRatesDAO(){}

    public LiveRatesDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public LiveRates find(long id){

        String sql = "SELECT * FROM m_fx_live_rates where trading_rates_id=?";

        Object args[] = new Object[]{
                id
        };

        List<LiveRates> liveRatesList = jdbcTemplate.query(sql ,args ,new LiveRatesMapper());

        if (liveRatesList.isEmpty()){
            return null ;
        }

        LiveRates liveRates = liveRatesList.get(0);
        TradingRates tradingRates = findForeignKey(liveRates);
        if(tradingRates !=null){

            liveRates.setTradingRates(tradingRates);
            return liveRates ;
        }

  
        return null;

    }

    public List<LiveRates> findAll(){

        String sql = "SELECT * FROM m_fx_live_rates";

        List<LiveRates> liveRatesList = jdbcTemplate.query(sql ,new LiveRatesMapper());

        if (liveRatesList.isEmpty()){
            return new ArrayList<>();
        }

        for(LiveRates liveRates : liveRatesList){
            
            TradingRates tradingRates = findForeignKey(liveRates);
            if(tradingRates!=null){
                liveRates.setTradingRates(tradingRates);
                continue ;
            }

            liveRatesList.remove(liveRates);
        }

        return liveRatesList;

    }

    public TradingRates findForeignKey(LiveRates liveRates){

        TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
        return tradingRatesDAO.find(liveRates.getTradingRates().getId());
    }

  

    public void create(final long tradingRateId,long liveRateId ,boolean hasGained ,double percentageChange){

        long customId = 0 ;

        if(liveRateId==0){

            String sql= "insert into m_fx_live_rates(trading_rates_id ,has_gained) values (? ,?)";
            try{

                GeneratedKeyHolder holder = new GeneratedKeyHolder();
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        statement.setLong(1,tradingRateId);
                        statement.setBoolean(2 ,true);
                        return statement;
                    }
                }, holder);

                customId = holder.getKey().longValue();
            }

            catch (Exception e) {
                e.printStackTrace();
                return ;
            }

            return;
        }

        String sql = "update m_fx_live_rates set trading_rates_id = ? ,percentage_change = ? ,has_gained = ? where id=?";

        Object args[] = new Object[]{
                tradingRateId,
                percentageChange ,
                hasGained ,
                liveRateId
        };
        int rows = jdbcTemplate.update(sql ,args);
        if(rows != 1){
        }
    }
}

