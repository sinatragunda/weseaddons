package com.wese.weseaddons.bereaudechange.enumerations;

public enum MONEY_ACCOUNT_TYPE {

    BANK_ACCOUNT("Bank Account") ,
    FUND_ACCOUNT("Fund Account") ,
    MOBILE_ACCOUNT("Mobile Account");

    String code ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    MONEY_ACCOUNT_TYPE(String s){
        this.code = s ;
    }

    public static  MONEY_ACCOUNT_TYPE fromInt(int i){

        for(MONEY_ACCOUNT_TYPE c : MONEY_ACCOUNT_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }
}
