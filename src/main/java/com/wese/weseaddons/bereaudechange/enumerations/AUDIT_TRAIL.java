package com.wese.weseaddons.bereaudechange.enumerations;

public enum AUDIT_TRAIL {


    CLEAR_WORKING_SET("Clear Working Set"),
    REVERSED_FX_DEAL("Reverse Fx Deal");

    private String code ;

    AUDIT_TRAIL(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static AUDIT_TRAIL fromInt(int i){

        for(AUDIT_TRAIL c : AUDIT_TRAIL.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }



}
