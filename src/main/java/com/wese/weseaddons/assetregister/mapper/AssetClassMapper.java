
package com.wese.weseaddons.assetregister.mapper ;

import com.wese.weseaddons.assetregister.pojo.AssetClass;
import com.wese.weseaddons.interfaces.MapperInterface;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssetClassMapper implements RowMapper<AssetClass>, MapperInterface {

	@Override
    public AssetClass mapRow(ResultSet rs , int rowNum){

        AssetClass assetClass = null ;
        
        try{
            assetClass = new AssetClass();
            assetClass.setCode(rs.getString("code"));
            assetClass.setDescription(rs.getString("description"));
        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return assetClass;
    }


}