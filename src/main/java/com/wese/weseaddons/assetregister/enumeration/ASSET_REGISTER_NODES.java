package com.wese.weseaddons.assetregister.enumeration;

public enum ASSET_REGISTER_NODES {

    ASSET_CLASSES("Asset Classes"),
    ASSET_CATEGORIES("Asset Categories") ,
    TAX_CODE("Tax Code"),
    INPUT_ERROR_CODE("Input Error Code");

    private String code ;

    ASSET_REGISTER_NODES(String code){
        this.code = code ;
    }

    public String getCode(){
        return code ;
    }

    public static ASSET_REGISTER_NODES fromInt(int arg){

        for(ASSET_REGISTER_NODES u : ASSET_REGISTER_NODES.values()){
            if(u.ordinal()==arg){
                return u ;
            }
        }

        return null ;
    }

}
