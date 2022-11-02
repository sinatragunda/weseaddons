
package com.wese.weseaddons.dashboard.enumerations ;


public enum AGING_DETAILS_ENUM{

	ONE_TO_30("30"),
	THIRTY_TO_60("60") ,
	SIXTY_TO_90("90") ,
	NINETY_TO_180("180") ,
	ONE_80_TO_360("360") ,
	YEAR("Year and above") ;

	private String code ;

    AGING_DETAILS_ENUM(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static AGING_DETAILS_ENUM fromInt(int i){

        for(AGING_DETAILS_ENUM c : AGING_DETAILS_ENUM.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }

}