package com.wese.weseaddons.bereaudechange.enumerations;

public enum PROFIT_CALC_METHOD {

	AVERAGE("Average"),
	WEIGHTED_AVERAGE("Weighted Average");

    private String code ;

    PROFIT_CALC_METHOD(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static PROFIT_CALC_METHOD fromInt(int i){

        for(PROFIT_CALC_METHOD c : PROFIT_CALC_METHOD.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }


}
