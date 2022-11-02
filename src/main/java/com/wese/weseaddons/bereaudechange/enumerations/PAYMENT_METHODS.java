package com.wese.weseaddons.bereaudechange.enumerations;

public enum PAYMENT_METHODS {


    CASH("Cash") ,
    MONEY_TRANSFER("Money Transfer") ,
    FNB_EWALLET("FNB E-Wallet") ,
    ECOCASH("EcoCash") ,
    ONE_WALLET("One Wallet");

    private String code ;

    PAYMENT_METHODS(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static  PAYMENT_METHODS fromInt(int i){

        for(PAYMENT_METHODS c : PAYMENT_METHODS.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}
