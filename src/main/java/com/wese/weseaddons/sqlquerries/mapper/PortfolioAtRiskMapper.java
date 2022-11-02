package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.PortfolioAtRisk;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PortfolioAtRiskMapper implements MapperInterface{

    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        PortfolioAtRisk portfolioAtRisk= null;
        try{
            portfolioAtRisk = new PortfolioAtRisk();

            portfolioAtRisk.setPrincipalOutstanding(resultSet.getDouble("Principal Outstanding"));
            portfolioAtRisk.setRisk(resultSet.getDouble("Portfolio at Risk"));

        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return portfolioAtRisk ;

    }
}
