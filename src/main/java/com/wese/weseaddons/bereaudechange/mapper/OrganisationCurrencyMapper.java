package com.wese.weseaddons.bereaudechange.mapper;

import com.wese.weseaddons.bereaudechange.fxrequest.OrganisationCurrencyRequest;
import com.wese.weseaddons.bereaudechange.pojo.OrganisationCurrency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganisationCurrencyMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {

        OrganisationCurrency organisationCurrency =  null;
        try{
            organisationCurrency = new OrganisationCurrency();
            organisationCurrency.setCode(resultSet.getString("code"));
        }

        catch (SQLException s){

        }

        return organisationCurrency ;
    }
}
