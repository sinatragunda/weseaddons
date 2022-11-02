package com.wese.weseaddons.bereaudechange.enumerations;

public enum T_ACCOUNT {

	DEPOSIT("Deposit"),
    WITHDRAW("Withdraw"),
    SETTLE("Settle");

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code ;

    T_ACCOUNT(String c){
        this.code =c ;
    }

    public static T_ACCOUNT fromInt(int i){

        for(T_ACCOUNT c : T_ACCOUNT.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}
