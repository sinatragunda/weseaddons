package com.wese.weseaddons.bereaudechange.enumerations;

public enum ROUNDING_RULE {

    NATURAL("Natural"),
    ROUND_DOWN("Round Down"),
    ROUND_UP("Round Up"),
    OPTIONAL("Optional");


    private String code ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    ROUNDING_RULE(String c){
        this.code =c ;
    }

    public static ROUNDING_RULE fromInt(int i){

        for(ROUNDING_RULE c : ROUNDING_RULE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }
}
