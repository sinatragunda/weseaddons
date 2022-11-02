package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.mapper.RevaluationMapper;
import com.wese.weseaddons.bereaudechange.pojo.*;
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
import java.util.List;
import java.util.function.Consumer;

public class AccrualDAO {

    private String tenant ;
    private FxDealDAO fxDealDao ;
    private JdbcTemplate jdbcTemplate ;

    private Consumer<Revaluation> revaluationConsumer = (e)->{

        FxDeal fxDeal = fxDealDao.find(e.getFxDeal().getId());
        e.setFxDeal(fxDeal);

        TradingRates tradingRates = fxDeal.getTradingRates();
        CurrencyPair currencyPair = tradingRates.getCurrencyPair();

        e.setCurrencyPair(currencyPair);
        e.setTradingRates(tradingRates);
    };

    public AccrualDAO(String tenant){

        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
        this.fxDealDao = new FxDealDAO(tenant);

    }

    public List<Revaluation> findWhere(long id ,String column){

        String sql = String.format("SELECT * FROM m_fx_revaluation where %s=?",column);

        Object args[] = new Object[]{
                id
        };

        List<Revaluation> revaluationList = jdbcTemplate.query(sql ,args ,new RevaluationMapper());

        if (revaluationList.isEmpty()){
            return null;
        }

        System.out.println("Revaluation list is "+revaluationList.size());

        revaluationList.stream().forEach(revaluationConsumer);

        return revaluationList ;

    }

    public Revaluation find(long id ,String column){

        String sql = String.format("SELECT * FROM m_fx_revaluation where %s=?",column);

        Object args[] = new Object[]{
                id
        };

        List<Revaluation> revaluationList = jdbcTemplate.query(sql ,args ,new RevaluationMapper());

        if (revaluationList.isEmpty()){
            return null;
        }

        revaluationList.stream().forEach(revaluationConsumer);

        return revaluationList.get(0) ;

    }


    public ObjectNode create(Revaluation revaluation){

        String sql = "INSERT INTO m_fx_revaluation(fx_deal_id,fcy ,position_revaluation ,revaluation_profit ,accrued_date ,reval_rate) VALUES(? , ? ,? ,? ,? ,?)";
        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1,revaluation.getFxDeal().getId());
                    statement.setDouble(2,revaluation.getFcy());
                    statement.setDouble(3,revaluation.getPositionRevaluation());
                    statement.setDouble(4,revaluation.getRevaluationProfit());
                    statement.setLong(5 , TimeHelper.epochNow());
                    statement.setDouble(6 ,revaluation.getRevalRate());

                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();

            fxDealDao.update(revaluation.getFxDeal().getId());
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return Helper.statusNodes(false);
        }
        return Helper.statusNodes(true);
    }
}
