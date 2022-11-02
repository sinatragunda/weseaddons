package com.wese.weseaddons.bereaudechange.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.impl.FxDAOService;
import com.wese.weseaddons.bereaudechange.mapper.PaymentMethodsMapper;
import com.wese.weseaddons.bereaudechange.pojo.PaymentMethods;
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

public class PaymentMethodsDAO implements FxDAOService{

    private String tenant ;
    private JdbcTemplate jdbcTemplate ;


    public PaymentMethodsDAO(String tenant){
        this.tenant = tenant ;
        this.jdbcTemplate = JdbcTemplateInit.getJdbcTemplate(tenant);
    }

    public List<PaymentMethods> findAll(){

        String sql = "SELECT * FROM m_fx_payment_methods";

        List<PaymentMethods> paymentMethodsList = jdbcTemplate.query(sql ,new PaymentMethodsMapper());

        if (paymentMethodsList.isEmpty()){
            return new ArrayList<>();
        }

        return paymentMethodsList;

    }

    public PaymentMethods find(long id){

        String sql = "SELECT * FROM m_fx_payment_methods where id=?";

        Object args[] = new Object[]{
                id
        };

        List<PaymentMethods> paymentMethodsList = jdbcTemplate.query(sql ,args ,new PaymentMethodsMapper());

        if (paymentMethodsList.isEmpty()){
            return null;
        }

        return paymentMethodsList.get(0);

    }

    public ObjectNode edit(PaymentMethods paymentMethods){

        if(paymentMethods==null){
            return Helper.statusNodes(false);
        }

        String sql = "update m_fx_payment_methods set name = ? ,description= ? ,charge_criteria = ? ,t_account = ? ,amount=? where id = ?";
        Object args[]= new Object[]{
        		paymentMethods.getName() ,
        		paymentMethods.getDescription() ,
        		paymentMethods.gettAccount().ordinal() ,
        		paymentMethods.getId()
        };

        int rows = jdbcTemplate.update(sql ,args);

        if(rows > 0){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);
    }


    public boolean create(PaymentMethods paymentMethods){

        String sql ="insert into m_fx_payment_methods(name ,description,payment_method,t_account) values(?,?,?,?)";
        long id = 0 ;
        try {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1,paymentMethods.getName());
                    statement.setString(2 ,paymentMethods.getDescription());
                    statement.setInt(3 ,paymentMethods.getPaymentMethod().ordinal());
                    statement.setInt(4 ,paymentMethods.gettAccount().ordinal());
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
