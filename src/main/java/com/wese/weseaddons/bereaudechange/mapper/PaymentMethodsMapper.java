package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.enumerations.PAYMENT_METHODS;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.pojo.PaymentMethods;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PaymentMethodsMapper implements RowMapper<PaymentMethods> {

    @Override
    public PaymentMethods mapRow(ResultSet resultSet, int i) {

    	PaymentMethods paymentMethods = null ;

        try{
        	paymentMethods = new PaymentMethods();
        	paymentMethods.setDescription(resultSet.getString("description"));
        	paymentMethods.setId(resultSet.getLong("id"));
        	paymentMethods.setName(resultSet.getString("name"));
            int paymentMethod = resultSet.getInt("payment_method");
            paymentMethods.setPaymentMethod(paymentMethod);

            int tAccount = resultSet.getInt("t_account");
            paymentMethods.settAccount(tAccount);

        }

        catch(SQLException s){
            s.printStackTrace();
        }

        return paymentMethods;
    }
}
