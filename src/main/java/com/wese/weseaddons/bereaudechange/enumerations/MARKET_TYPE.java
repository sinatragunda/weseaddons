package com.wese.weseaddons.bereaudechange.enumerations;

public enum MARKET_TYPE {

    LOCAL("Local Currency"),
    CROSS("Cross Currency");

    private String code ;

    MARKET_TYPE(String code){
        this.code = code ;
    }

    public String getCode(){
        return this.code ;
    }

    public static MARKET_TYPE fromInt(int arg){

        for(MARKET_TYPE marketType : MARKET_TYPE.values()){
            if(marketType.ordinal()==arg){
                return marketType;
            }
        }
        return null ;
    }
}
