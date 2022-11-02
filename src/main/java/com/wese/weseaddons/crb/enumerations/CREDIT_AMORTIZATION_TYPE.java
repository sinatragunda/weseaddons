package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CREDIT_AMORTIZATION_TYPE {

    A("amortizationType.equal.installments"),
    B("amortizationType.equal.principal"),
    C("Single Payment"),
    D("Irregular repayment schedule");

    String code ;

    CREDIT_AMORTIZATION_TYPE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }


    public static CREDIT_AMORTIZATION_TYPE fromString(String arg){

        for(CREDIT_AMORTIZATION_TYPE c : CREDIT_AMORTIZATION_TYPE.values()){
            if(c.getCode().equalsIgnoreCase(arg)){
                return c ;
            }
        }
        
        return null ;
    }

    @JsonValue
    public String value(){
        return name() ;
    }
}
