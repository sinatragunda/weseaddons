package com.wese.weseaddons.assetregister.enumeration;

import com.wese.weseaddons.enumerations.LISTABLE_ENUM;

public enum ASSET_TRANSACTION_TYPE{

    ACQUISITION("Acquisition"),
    REVALUATION("Revaluation"),
    ACCRUAL("Accrual"),
    DISPOSAL("Disposal");

    private String code ;

    ASSET_TRANSACTION_TYPE(String code){
        this.code = code ;
    }

    public String getCode(){
        return code ;
    }

    public static ASSET_TRANSACTION_TYPE fromInt(int arg){

        for(ASSET_TRANSACTION_TYPE u : values()){
            if(u.ordinal()==arg){
                return u ;
            }
        }

        return null ;
    }

}
