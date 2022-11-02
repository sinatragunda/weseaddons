package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;


import java.sql.ResultSet;
import java.sql.SQLException;

public class AgingDetailsMapper implements MapperInterface{

    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        AgingDetails agingDetail =null ;

        try{
            agingDetail = new AgingDetails();

            agingDetail.setPrincipalOverdue(resultSet.getDouble("Principal Overdue"));
            agingDetail.setDaysInArrears(resultSet.getInt("Days in Arrears"));
            agingDetail.setInterestOverdue(resultSet.getDouble("Interest Overdue"));

        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return agingDetail ;

    }
}
