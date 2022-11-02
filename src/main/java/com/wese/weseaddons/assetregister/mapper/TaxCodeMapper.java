package com.wese.weseaddons.assetregister.mapper;

import com.wese.weseaddons.assetregister.pojo.InputErrorCode;
import com.wese.weseaddons.assetregister.pojo.TaxCode;
import com.wese.weseaddons.interfaces.MapperInterface;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxCodeMapper implements RowMapper<TaxCode> ,MapperInterface{

    @Override
    public TaxCode mapRow(ResultSet rs , int rowNum){

        TaxCode taxCode = null ;

        try{
            taxCode = new TaxCode();
            taxCode.setCode(rs.getString("code"));
            taxCode.setDescription(rs.getString("description"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return taxCode;
    }

}
