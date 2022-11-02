package com.wese.weseaddons.assetregister.mapper;

import com.wese.weseaddons.assetregister.pojo.AssetCategory;
import com.wese.weseaddons.assetregister.pojo.AssetClass;
import com.wese.weseaddons.interfaces.MapperInterface;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AssetCategoryMapper implements RowMapper<AssetCategory> ,MapperInterface{

    @Override
    public AssetCategory mapRow(ResultSet rs , int rowNum){

        AssetCategory assetCategory = null ;

        try{
            assetCategory = new AssetCategory();
            assetCategory.setCode(rs.getString("code"));
            assetCategory.setDescription(rs.getString("description"));
            assetCategory.setAssetClass(new AssetClass(rs.getString("class")));
            assetCategory.setAccDepr(rs.getString("acc_depr"));
            assetCategory.setDefTaxBas(rs.getString("def_tax_bs"));
            assetCategory.setDefTaxIs(rs.getString("def_tax_is"));
            assetCategory.setProfit(rs.getString("profit"));
            assetCategory.setImpairement(rs.getString("impairment"));
            assetCategory.setRevalue(rs.getString("revalue"));

        }

        catch (SQLException s){
            s.printStackTrace();
        }

        return assetCategory ;
    }

}
