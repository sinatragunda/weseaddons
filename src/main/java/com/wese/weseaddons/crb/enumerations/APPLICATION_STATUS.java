package com.wese.weseaddons.crb.enumerations ;

import com.fasterxml.jackson.annotation.JsonValue;

public enum APPLICATION_STATUS {
		
	A("loanStatusType.submitted.and.pending.approval"),
	B("Awaiting Docs"),
	C("Securities Perfection"),
	D("loanStatusType.rejected"),
	E("loanStatusType.withdrawn.by.client"),
	F("loanStatusType.active"),
	G("Pending disbursement");


    private String code ;

    APPLICATION_STATUS(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static APPLICATION_STATUS fromString(String i){

        for(APPLICATION_STATUS c : APPLICATION_STATUS.values()){
            if(c.getCode().equalsIgnoreCase(i)){
                return c ;
            }
        }
        return APPLICATION_STATUS.F ;
    }

    @JsonValue
    public String value(){
        return name() ;
    }
}