package com.wese.weseaddons.weseaa.module.charges.enumerations;

public enum CHARGE_DEDUCTION_TYPE{

    DEPOSIT_PLUS_CHARGE("Deposit Plus Charge"),
    WITHDRAW_MINUS_CHARGE("Withdraw Minus Charge"),
    SPLIT_CHARGE("Split Charge");

    private String code ;

    CHARGE_DEDUCTION_TYPE(String code){
        this.code = code  ;
    }


    public static CHARGE_DEDUCTION_TYPE fromInt(int val){

        for(CHARGE_DEDUCTION_TYPE charge_deduction_type : CHARGE_DEDUCTION_TYPE.values()){

            if(charge_deduction_type.ordinal()==val){
                return charge_deduction_type ;
            }
        }

        return null ;
    }

}
