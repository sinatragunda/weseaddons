/*Created by Sinatra Gunda
  At 08:30 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.AccruedInterest;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccruedInterestMapper implements MapperInterface{

    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        AccruedInterest accruedInterest = null ;
        try{
            accruedInterest = new AccruedInterest();
            accruedInterest.setAccruedInterest(resultSet.getDouble("Accrued Interest"));

        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return accruedInterest ;

    }
}
