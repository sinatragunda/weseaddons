package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum COMPANY_STATUS{
	
	A("Actively Trading"),
	B("Under receivership/Liquidation"),
	C("Dormant"),
	D("Dissolved"),
	E("Liquidated"),
	U("Under Management");


	String code ;

	COMPANY_STATUS(String code){
		this.code = code ;
	}

	public String getCode() {
		return code;
	}


	public static COMPANY_STATUS fromString(String arg){

		for(COMPANY_STATUS c : COMPANY_STATUS.values()){
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