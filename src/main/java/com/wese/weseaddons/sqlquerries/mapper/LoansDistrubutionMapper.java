package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;
import com.wese.weseaddons.sqlquerries.pojo.LoansDistrubution;


import java.sql.ResultSet;
import java.sql.SQLException;

public class LoansDistrubutionMapper implements MapperInterface{

    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        LoansDistrubution loansDistrubution = null ;

        try{

            loansDistrubution = new LoansDistrubution();
            loansDistrubution.setPrincipalAmount(resultSet.getDouble("Principal"));
            loansDistrubution.setProductName(resultSet.getString("Loan Product Name"));
        
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return loansDistrubution ;

    }
}
