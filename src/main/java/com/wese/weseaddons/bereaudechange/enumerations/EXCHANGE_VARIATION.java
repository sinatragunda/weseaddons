package com.wese.weseaddons.bereaudechange.enumerations;

public enum EXCHANGE_VARIATION {

    STRICT("Strict"),
    SMALL_NOTES("Small Notes"),
    BIG_NOTES("Big Notes"),
    NEGOTIATE("Negotiate");

    private String code ;

    EXCHANGE_VARIATION(String code){
        this.code = code ;
    }

    public String getCode(){
        return this.code ;
    }

    public static EXCHANGE_VARIATION fromInt(int arg){

        for(EXCHANGE_VARIATION marketType : EXCHANGE_VARIATION.values()){
            if(marketType.ordinal()==arg){
                return marketType;
            }
        }
        return null ;
    }
}
