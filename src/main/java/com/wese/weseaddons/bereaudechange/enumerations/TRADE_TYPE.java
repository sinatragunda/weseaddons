package com.wese.weseaddons.bereaudechange.enumerations;

public enum TRADE_TYPE {
    BUY("Buy") ,
    SELL("Sell");

    private String code ;

    TRADE_TYPE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static TRADE_TYPE fromInt(int c){

        for(TRADE_TYPE tradeType : TRADE_TYPE.values()){
            if(tradeType.ordinal()==c){
                return tradeType ;
            }
        }

        return null ;
    }
}
