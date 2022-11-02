package com.wese.weseaddons.bereaudechange.dao;


import com.wese.weseaddons.bereaudechange.impl.FxDAODelete;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.BlotterMapper;
import com.wese.weseaddons.bereaudechange.pojo.Blotter;
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

public class BlotterDAO implements FxDAOService ,FxDAODelete {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public BlotterDAO(String tenant){

        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<Blotter> findAll(){

        String sql = "SELECT * FROM m_fx_blotter";

        List<Blotter> blotterList = jdbcTemplate.query(sql ,new BlotterMapper());

        if(blotterList.isEmpty()){
            return new ArrayList<>();
        }

//        List<Blotter> blotterList =  DaoHelper<Blotter>.findAll(tenant);

        return blotterList;

    }

    @Override
    public Blotter find(long id){

        String sql = "SELECT * FROM m_fx_blotter where id=?";

        Object args[] = new Object[]{
                id
        };

        List<Blotter> blotterList = jdbcTemplate.query(sql ,args ,new BlotterMapper());

        if (blotterList.isEmpty()){
            return null;
        }

        return blotterList.get(0);

    }


    public long create(Blotter blotter){

        String sql ="insert into m_fx_blotter(base_amount ,base_charges ,net_base_amount , quote_amount ,quote_charges ,net_quote_amount) values(? ,?,? ,?,?,?)";
        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1,blotter.getBaseAmount());
                    statement.setDouble(2 ,blotter.getBaseCharges());
                    statement.setDouble(3 ,blotter.getNetBaseAmount());
                    statement.setDouble(4 ,blotter.getQuoteAmount());
                    statement.setDouble(5 ,blotter.getQuoteCharges());
                    statement.setDouble(6 ,blotter.getNetQuoteAmount());
                    return statement;
                }
            }, holder);

            id = holder.getKey().longValue();
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return id ;
    }
    
    public Runnable delete(long id){

        Runnable runnable = ()->{

            String sql = "DELETE FROM m_fx_blotter WHERE id = ?";

            Object args[]= new Object[]{
                id
            };

            jdbcTemplate.update(sql ,args);    
        };

        return runnable ;

    } 
}
