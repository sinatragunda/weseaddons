package com.wese.weseaddons.sqlquerries.mapper;

import com.wese.weseaddons.interfaces.MapperInterface;
import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;
import com.wese.weseaddons.sqlquerries.pojo.LoansSector;


import java.sql.ResultSet;
import java.sql.SQLException;

public class LoansSectorMapper implements MapperInterface{

    @Override
    public PojoInterface mapRow(ResultSet resultSet, int i) {

        LoansSector loansSector = null ;

        try{

            loansSector = new LoansSector();
            loansSector.setPrincipalAmount(resultSet.getDouble("Principal"));
            loansSector.setLoanPurpose(resultSet.getString("Loan Purpose"));
        
        }
        catch (SQLException s){
            s.printStackTrace();
        }

        return loansSector ;

    }
}
