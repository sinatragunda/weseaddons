package com.wese.weseaddons.enumerations;

public enum TRANSACTION_TYPE{
	
	FX_DEAL("Fx Deal");
	
	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code ;

    TRANSACTION_TYPE(String c){
        this.code =c ;
    }

    public static TRANSACTION_TYPE fromInt(int i){

        for(TRANSACTION_TYPE c : TRANSACTION_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}