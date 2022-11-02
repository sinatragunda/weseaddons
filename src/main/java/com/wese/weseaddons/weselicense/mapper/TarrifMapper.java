package com.wese.weseaddons.weselicense.mapper;

import com.wese.weseaddons.weselicense.enumeration.TIME_UNIT_TYPE;
import com.wese.weseaddons.weselicense.pojo.Tarrif;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TarrifMapper implements RowMapper<Tarrif> {

    public Tarrif mapRow(ResultSet resultSet ,int rowNum){

        Tarrif tarrif = null;

        try{
            tarrif = new Tarrif();
            tarrif.setAmount(resultSet.getFloat("amount"));
            tarrif.setId(resultSet.getLong("id"));
            tarrif.setName(resultSet.getString("name"));
            tarrif.setTimeUnit(resultSet.getInt("time_unit"));

            int timeUnitType = resultSet.getInt("time_unit_type");
            tarrif.setTimeUnitType(TIME_UNIT_TYPE.fromInt(timeUnitType));
        }
        catch (SQLException s){

            s.printStackTrace();
        }
        return tarrif ;
    }
}
