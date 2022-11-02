package com.wese.weseaddons.sqlquerries.enumerations;

public enum WORKING_SET_NAME{
	ACCOUNTING,
	CONFIGURATION,
	PORTFOLIO,
	ALL;


	public static WORKING_SET_NAME fromInt(int arg){
		for(WORKING_SET_NAME set_name : values()){
			if(set_name.ordinal()==arg){
				return set_name;
			}
		}
		return null ;
	}
};
