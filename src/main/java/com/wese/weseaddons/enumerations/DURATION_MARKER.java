package com.wese.weseaddons.enumerations;

public enum DURATION_MARKER{

    DAY("DAY"),
    WEEK("WEEK"),
    MONTH("MONTH"),
    YEAR("YEAR");

    private String code ;

    DURATION_MARKER(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static DURATION_MARKER fromInt(int i){

        for(DURATION_MARKER c : DURATION_MARKER.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}

