package com.wese.weseaddons.assetregister.enumeration;

public enum ASSET_TYPE {

    OWNED("Owned"),
    LEASED("Leased");

    private String code ;

    ASSET_TYPE(String code){
        this.code = code ;
    }

    public String getCode(){
        return code ;
    }

    public static ASSET_TYPE fromInt(int arg){

        for(ASSET_TYPE u : ASSET_TYPE.values()){
            if(u.ordinal()==arg){
                return u ;
            }
        }

        return null ;
    }

}
