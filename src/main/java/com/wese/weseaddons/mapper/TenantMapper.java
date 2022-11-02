package com.wese.weseaddons.mapper;

import com.wese.weseaddons.pojo.WeseTenant;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TenantMapper implements RowMapper<WeseTenant> {

    public WeseTenant mapRow(ResultSet resultSet , int row){

        WeseTenant weseTenant = null ;
        try{
            weseTenant = new WeseTenant();
            weseTenant.setId(resultSet.getInt("id"));
            weseTenant.setTimeZone(resultSet.getString("timezone_id"));
        }

        catch (SQLException sql){
        }

        return weseTenant ;
    }
}
