package com.wese.weseaddons.weselicense.enumeration;


public enum BILL_STATE {

    RECEIPTED("Receipted"),
    PAID("Paid"),
    OVERDUE("Overdue");

    private String code ;

    BILL_STATE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static BILL_STATE fromCode(String code){

        for(BILL_STATE state : BILL_STATE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }
        return null ;
    }

    public static BILL_STATE fromInt(int code){

        for(BILL_STATE state : BILL_STATE.values()){
            if(state.ordinal() == code){
                return state ;
            }
        }
        return null ;
    }
}
