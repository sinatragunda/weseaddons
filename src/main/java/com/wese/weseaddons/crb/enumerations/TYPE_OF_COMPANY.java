package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TYPE_OF_COMPANY{

	A("Public Limited Company"),
	B("sole Proprietorship"),
	C("Non Government Organization"),
	D("Government Institution"),
	E("Club/Association"),
	F("Private Limited company"),
	G("Church"),
	H("School");


	private String code ;

	TYPE_OF_COMPANY(String c){
		this.code =c ;
	}

	public String getCode() {
		return code;
	}

	public static TYPE_OF_COMPANY fromInt(int i){

		for(TYPE_OF_COMPANY c : TYPE_OF_COMPANY.values()){
			if(c.ordinal()==i){
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