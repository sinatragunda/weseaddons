package com.wese.weseaddons.bereaudechange.enumerations;

public enum FUNDS_ACTION_TYPE{
	
   // TRANSFER("Transfer"),
    DEPOSIT("Deposit"),
    WITHDRAW("Withdraw");
    
    private String code ;
   
	
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    FUNDS_ACTION_TYPE(String c){
        this.code =c ;
    }

    public static FUNDS_ACTION_TYPE fromInt(int i){

        for(FUNDS_ACTION_TYPE c : FUNDS_ACTION_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}