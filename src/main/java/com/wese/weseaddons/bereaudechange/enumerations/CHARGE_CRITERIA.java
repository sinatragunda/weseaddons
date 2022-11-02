package com.wese.weseaddons.bereaudechange.enumerations;

public enum CHARGE_CRITERIA {
	
	FIXED_AMOUNT("Fixed Amount") ,
    PERCENTAGE_OF_AMOUNT("Percentage of Amount") ,
    THRESHOLD_AMOUNT("Threashold Charging");

    private String code ;

    CHARGE_CRITERIA(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static  CHARGE_CRITERIA fromInt(int i){

        for(CHARGE_CRITERIA c : CHARGE_CRITERIA.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}
