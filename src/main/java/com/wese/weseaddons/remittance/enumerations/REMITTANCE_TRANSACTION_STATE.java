
package com.wese.weseaddons.remittance.enumerations ;


public enum REMITTANCE_TRANSACTION_STATE{

	OPENED("Opened") ,
	CLOSED ("Closed"),
	HOLD("Hold") ,
	BOUNCED("Bounced") ;

	private String code ;

    REMITTANCE_TRANSACTION_STATE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static REMITTANCE_TRANSACTION_STATE fromInt(int i){
        for(REMITTANCE_TRANSACTION_STATE c : REMITTANCE_TRANSACTION_STATE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }


}