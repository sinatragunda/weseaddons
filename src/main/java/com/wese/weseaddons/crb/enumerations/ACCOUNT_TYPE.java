package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ACCOUNT_TYPE {

	CORPORATE("accountType.CORPORATE"),
	INDIVIDUAL("accountType.individual");


	String code ;

	public String getCode(){
		return code ;
	}

	ACCOUNT_TYPE(String code){
		this.code = code ;
	}

	public static ACCOUNT_TYPE fromString(String arg){

        for(ACCOUNT_TYPE c : ACCOUNT_TYPE.values()){
            if(c.getCode().equalsIgnoreCase(arg)){
                return c ;
            }
        }

        return ACCOUNT_TYPE.CORPORATE ;
    }

	@JsonValue
	public String value(){
		return name() ;
	}
}
