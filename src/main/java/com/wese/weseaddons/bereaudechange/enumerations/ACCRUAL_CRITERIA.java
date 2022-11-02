package com.wese.weseaddons.bereaudechange.enumerations;

public enum ACCRUAL_CRITERIA {

    FX_DEAL_DATE("Use fx deal date"),
    REVALUATION_DATE("Use Revaluation Date");

    private String code ;

    ACCRUAL_CRITERIA(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static ACCRUAL_CRITERIA fromInt(int i){

        for(ACCRUAL_CRITERIA c : ACCRUAL_CRITERIA.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }

}
