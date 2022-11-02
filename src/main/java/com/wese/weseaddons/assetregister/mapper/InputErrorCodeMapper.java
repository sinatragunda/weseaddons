package com.wese.weseaddons.assetregister.mapper;

import com.wese.weseaddons.assetregister.pojo.AssetClass;
import com.wese.weseaddons.assetregister.pojo.InputErrorCode;
import com.wese.weseaddons.interfaces.MapperInterface;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InputErrorCodeMapper implements RowMapper<InputErrorCode> ,MapperInterface{

    @Override
    public InputErrorCode mapRow(ResultSet rs , int rowNum){

        InputErrorCode inputErrorCode = null ;

        try{
            inputErrorCode = new InputErrorCode();
            inputErrorCode.setCode(rs.getString("code"));
            inputErrorCode.setReason(rs.getString("reason"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return inputErrorCode;
    }


}
