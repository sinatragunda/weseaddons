package com.wese.weseaddons.bereaudechange.enumerations;

public enum ALLOCATION_TYPE {

    EQUAL_SHARE("Equal Share Agreement"),
    OPTIONAL("Optional Share") ;

    private String code ;

    ALLOCATION_TYPE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static ALLOCATION_TYPE fromInt(int i){

        for(ALLOCATION_TYPE c : ALLOCATION_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}
