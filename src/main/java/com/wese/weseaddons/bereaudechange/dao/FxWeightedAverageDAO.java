package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.mapper.FxWeightedAverageMapper;
import com.wese.weseaddons.bereaudechange.pojo.FxWeightedAverage;
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

public class FxWeightedAverageDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public FxWeightedAverageDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<FxWeightedAverage> findAll(){

        String sql = "SELECT * FROM m_fx_weighted_averages";

        List<FxWeightedAverage> myList = jdbcTemplate.query(sql ,new FxWeightedAverageMapper());

        if (myList.isEmpty()){
            return new ArrayList<>();
        }

        return myList;

    }
    
    public ObjectNode update(FxWeightedAverage	fxWeightedAverage){

        if(fxWeightedAverage == null){
            return Helper.statusNodes(false);
        }
        

        String sql = "update m_fx_weighted_averages set weighted_average_buy_price = ?, total_currency_bought = ? where currency_symbol = ?";
        Object args[]= new Object[]{
        		fxWeightedAverage.getWeightedAverageBuyPrice(),
        		fxWeightedAverage.getTotalCurrencyBought(),
        		fxWeightedAverage.getCurrencySymbol()
        };

        int rows = jdbcTemplate.update(sql ,args);

        if(rows > 0){
            return Helper.statusNodes(true);
        }
        
        return Helper.statusNodes(false);
    }

    public FxWeightedAverage find(long id){

        String sql = "SELECT * FROM m_fx_weighted_averages where id=?";

        Object args[] = new Object[]{
                id
        };

        List<FxWeightedAverage> myList = jdbcTemplate.query(sql ,args ,new FxWeightedAverageMapper());

        if (myList.isEmpty()){
            return null;
        }

        return myList.get(0);

    }
    
    public FxWeightedAverage findByCurrencySymbol(String currencySymbol){

        String sql = "SELECT * FROM m_fx_weighted_averages where currency_symbol = ?";

        Object args[] = new Object[]{
        		currencySymbol
        };

        List<FxWeightedAverage> myList = jdbcTemplate.query(sql ,args ,new FxWeightedAverageMapper());

        if (myList.isEmpty()){
            return null;
        }

        return myList.get(0);

    }

    public boolean create(FxWeightedAverage fxWeightedAverage){

        String sql ="insert into m_fx_weighted_averages(currency_symbol, weighted_average_buy_price, total_currency_bought) values(?, ?, ?)";
        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,fxWeightedAverage.getCurrencySymbol());
                    statement.setDouble(2 ,fxWeightedAverage.getWeightedAverageBuyPrice());
                    statement.setDouble(3 ,fxWeightedAverage.getTotalCurrencyBought());
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
}
