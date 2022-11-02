package com.wese.weseaddons.weselicense.mapper;

import com.wese.weseaddons.weselicense.pojo.Tenant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class TenantMapper implements RowMapper<Tenant> {

    public Tenant mapRow(ResultSet resultSet ,int rowNum){

        Tenant tenant = null ;
        try{
            tenant = new Tenant();
            tenant.setId(resultSet.getLong("id"));
            tenant.setExemption(resultSet.getBoolean("exemption"));
            tenant.setGracePeriod(resultSet.getInt("grace_period"));
            tenant.setTenantName(resultSet.getString("name"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return tenant ;
    }
}
