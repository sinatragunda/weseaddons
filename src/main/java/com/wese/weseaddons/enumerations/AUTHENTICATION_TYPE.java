package com.wese.weseaddons.enumerations;

public enum AUTHENTICATION_TYPE {

    BASIC("Basic"),
    BEARER("Bearer") ;
    private String code ;

    AUTHENTICATION_TYPE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static AUTHENTICATION_TYPE fromInt(int i){

        for(AUTHENTICATION_TYPE c: AUTHENTICATION_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}
