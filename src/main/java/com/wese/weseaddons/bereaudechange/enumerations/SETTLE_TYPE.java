package com.wese.weseaddons.bereaudechange.enumerations;

public enum SETTLE_TYPE {

    DEPOSIT("103"),
    WITHDRAW("104");

    private String code ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    SETTLE_TYPE(String c){
        this.code =c ;
    }

    public static SETTLE_TYPE fromInt(int i){

        for(SETTLE_TYPE c : SETTLE_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }
}
