package com.wese.weseaddons.bereaudechange.dao;

import com.wese.weseaddons.bereaudechange.exceptions.CurrencyNameException;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.StandardCurrencyMapper;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
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

public class StandardCurrencyDAO implements FxDAOService {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private StandardCurrency standardCurrency ;

  
    public StandardCurrencyDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<StandardCurrency> findAll(){

        String sql = "SELECT * FROM m_fx_standard_currency";

        List<StandardCurrency> standardCurrencyList = jdbcTemplate.query(sql ,new StandardCurrencyMapper());

        if (standardCurrencyList.isEmpty()){
            return new ArrayList<>();
        }

        return standardCurrencyList;

    }

    public StandardCurrency find(long id){

        String sql = "SELECT * FROM m_fx_standard_currency where id=?";
        Object args[] = new Object[]{
                id
        };

        List<StandardCurrency> standardCurrencyList = jdbcTemplate.query(sql ,args ,new StandardCurrencyMapper());

        if (standardCurrencyList.isEmpty()){
            return null;
        }
        return standardCurrencyList.get(0);

    }


    /// should throw exception here when that value is geater than 3
    public boolean create(StandardCurrency standardCurrency){

        if(standardCurrency.getName().length() != 3){

            throw new CurrencyNameException("Currency symbol does not contain 3 characters");

        }

        String sql ="insert into m_fx_standard_currency(name ,country) values(? ,?)";
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,standardCurrency.getName());
                    statement.setString(2 ,standardCurrency.getCountry());

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
