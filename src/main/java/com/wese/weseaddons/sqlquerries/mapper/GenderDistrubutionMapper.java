package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;
import com.wese.weseaddons.sqlquerries.pojo.GenderDistrubution;


import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderDistrubutionMapper implements MapperInterface{

    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        GenderDistrubution genderDistrubution = null ;

        try{

            genderDistrubution = new GenderDistrubution();
            genderDistrubution.setPrincipalAmount(resultSet.getDouble("Principal"));
            genderDistrubution.setGender(resultSet.getString("Gender"));
            genderDistrubution.setCount(resultSet.getInt("Number of Clients"));
        
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return genderDistrubution ;

    }
}
