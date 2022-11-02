package com.wese.weseaddons.bereaudechange.enumerations;

public enum FINANCIAL_INSTRUMENT_TYPE {
    CASH("Cash"),
    DERIVATIVES("Derivatives");

    private String code ;

    FINANCIAL_INSTRUMENT_TYPE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public static FINANCIAL_INSTRUMENT_TYPE fromInt(int i){

        for(FINANCIAL_INSTRUMENT_TYPE c : FINANCIAL_INSTRUMENT_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }
}
