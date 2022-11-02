package com.wese.weseaddons.assetregister.enumeration ;

public enum USEFULL_LIFE{

	DAYS("Days"),
	WEEKS("Weeks"),
	MONTHS("Months"),
	YEARS("Years") ;

	private String code ;

	USEFULL_LIFE(String code){
		this.code = code ;
	}

	public String getCode(){
		return code ;
	}

	public USEFULL_LIFE fromInt(int arg){
		
		for(USEFULL_LIFE u : USEFULL_LIFE.values()){
			if(u.ordinal()==arg){
				return u ;
			}
		}
		return YEARS ;
	}
}