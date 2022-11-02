package com.wese.weseaddons.assetregister.pojo;

import com.wese.weseaddons.interfaces.ExecutableClass;
import com.wese.weseaddons.interfaces.ListingFamily;
import com.wese.weseaddons.interfaces.PojoInterface;

public class AssetClass implements PojoInterface{

    private String code ;
    private String description ;


    public AssetClass() {
    }


    public AssetClass(String code) {
        this.code = code;
    }


    @Override
    public Long getId() {
        return null;
    }

    @Override
    public String getSchema(){
        return "m_asset_class";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
