package com.wese.weseaddons.bereaudechange.dao;

import com.wese.weseaddons.bereaudechange.body.FinancialInstrumentBody;
import com.wese.weseaddons.bereaudechange.mapper.FinancialInstrumentMapper;
import com.wese.weseaddons.bereaudechange.pojo.FinancialInstrument;
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

public class FinancialInstrumentsDAO {

    private JdbcTemplate jdbcTemplate ;
    private FinancialInstrumentBody financialInstrumentBody ;


    public FinancialInstrumentsDAO(String tenant){
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<FinancialInstrument> findAll(){

        String sql = "SELECT * FROM m_fx_financial_instruments";

        List<FinancialInstrument> financialInstrumentList = jdbcTemplate.query(sql ,new FinancialInstrumentMapper());

        if (financialInstrumentList.isEmpty()){
            return new ArrayList<>();
        }

        return financialInstrumentList;

    }

    public FinancialInstrument find(long id){

        String sql = "SELECT * FROM m_fx_financial_instruments where id=?";

        Object args[] = new Object[]{
                id
        };

        List<FinancialInstrument> financialInstrumentList = jdbcTemplate.query(sql ,args ,new FinancialInstrumentMapper());

        if (financialInstrumentList.isEmpty()){
            return null;
        }

        return financialInstrumentList.get(0);

    }

    public boolean edit(FinancialInstrument financialInstrument){

        long id = financialInstrument.getId();
        FinancialInstrument defaultFinancialInstrument = find(id);

        if(financialInstrument.getFinancialInstrumentType()==null){
            financialInstrument.setFinancialInstrumentType(defaultFinancialInstrument.getFinancialInstrumentType().ordinal());
        }

        String sql = "UPDATE m_fx_financial_instruments SET instrument_type = ? ,name = ? ,description = ? WHERE id =?";
        
        Object args[] = new Object[]{
            financialInstrument.getFinancialInstrumentType().ordinal(),
            financialInstrument.getName(),
            financialInstrument.getDescription(),
            financialInstrument.getId()
        };

        int affectedRows = jdbcTemplate.update(sql ,args);

        if(affectedRows > 0){
            return true ;
        }
        return false ;
    }

    public boolean create(FinancialInstrumentBody financialInstrumentBody){

        String sql ="insert into m_fx_financial_instruments(name ,description,instrument_type) values(? ,?,?)";
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,financialInstrumentBody.getName());
                    statement.setString(2 ,financialInstrumentBody.getDescription());
                    statement.setInt(3 ,financialInstrumentBody.getInstrumentType());

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
