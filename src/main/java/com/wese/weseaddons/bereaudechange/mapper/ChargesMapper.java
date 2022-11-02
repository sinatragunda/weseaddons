package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.enumerations.CHARGE_CRITERIA;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.pojo.Charges;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ChargesMapper implements RowMapper<Charges> {

    @Override
    public Charges mapRow(ResultSet resultSet, int i) {

        Charges charges = null ;

        try{
            charges = new Charges();
            charges.setAmount(resultSet.getDouble("amount"));
            charges.setDescription(resultSet.getString("description"));
            charges.setId(resultSet.getLong("id"));
            charges.setName(resultSet.getString("name"));
            int chargeCriteria = resultSet.getInt("charge_criteria");
            charges.setChargeCriteria(chargeCriteria);

            int tAccount = resultSet.getInt("t_account");
            charges.settAccount(tAccount);

        }

        catch(SQLException s){
            s.printStackTrace();
        }

        return charges;
    }
}
