package com.wese.weseaddons.bereaudechange.dao;


import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.MoneyAccountChargesMapper;

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


public class MoneyAccountChargesDAO {

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;
    private MoneyAccountChargesRM moneyAccountChargesRM;

    public MoneyAccountChargesDAO(){

    }

    public MoneyAccountChargesDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<MoneyAccountChargesRM> findAll(){

        String sql = "SELECT * FROM m_fx_account_charge_mapping";

        List<MoneyAccountChargesRM> moneyAccountChargesRMList = jdbcTemplate.query(sql ,new MoneyAccountChargesMapper());

        if (moneyAccountChargesRMList.isEmpty()){
            return new ArrayList<>();
        }

        for(MoneyAccountChargesRM m: moneyAccountChargesRMList){

            long chargeId = m.getCharges().getId();
            Charges charges = (Charges)findForeignKey(new ChargesDAO(tenant) ,chargeId);
            m.setCharges(charges);
        }


        return moneyAccountChargesRMList;

    }

    public MoneyAccountChargesRM find(long id){

        String sql = "SELECT * FROM m_fx_account_charge_mapping where id=?";

        Object object[]= new Object[]{
                id
        };

        List<MoneyAccountChargesRM> moneyAccountChargesRMList = jdbcTemplate.query(sql ,object,new MoneyAccountChargesMapper());

        if (moneyAccountChargesRMList.isEmpty()){
            return null;
        }

        for(MoneyAccountChargesRM m: moneyAccountChargesRMList){

            long chargeId = m.getCharges().getId();
            Charges charges = (Charges)findForeignKey(new ChargesDAO(tenant) ,chargeId);
            m.setCharges(charges);
        }

        return moneyAccountChargesRMList.get(0) ;

    }

    public List<MoneyAccountChargesRM> findWhere(long id){

        String sql = "SELECT * FROM m_fx_account_charge_mapping where money_account_id=?";

        Object object[]= new Object[]{
                id
        };

        List<MoneyAccountChargesRM> moneyAccountChargesRMList = jdbcTemplate.query(sql ,object,new MoneyAccountChargesMapper());

        if (moneyAccountChargesRMList.isEmpty()){
            return new ArrayList<>();
        }

        for(MoneyAccountChargesRM m: moneyAccountChargesRMList){

            long chargeId = m.getCharges().getId();
            Charges charges = (Charges)findForeignKey(new ChargesDAO(tenant) ,chargeId);
            m.setCharges(charges);
        }


        return moneyAccountChargesRMList;

    }

    public boolean deactivateCharge(long moneyAccountId ,long chargeId ,boolean value){

        String sql = "UPDATE m_fx_account_charge_mapping SET active = ? where money_account_id =? AND charge_id = ?";

        Object object[]= new Object[]{
                value ,
                moneyAccountId ,
                chargeId
        };

        int affectedRows = jdbcTemplate.update(sql ,object);

        if(affectedRows > 0){
            return true ;
        }

        return false ;

    }

    public boolean create(MoneyAccountChargesRM moneyAccountChargesRM){

        String sql ="insert into m_fx_account_charge_mapping(money_account_id ,charge_id) values(? ,?)";
        long id = 0 ;
        try {


            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, moneyAccountChargesRM.getMoneyAccount().getId());
                    statement.setLong(2 , moneyAccountChargesRM.getCharges().getId());


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
