package com.wese.weseaddons.taskmanager.enumerations;

public enum STATE {

    ASSIGNED("Assigned") ,
    OPENED("Opened"),
    REVIEWED("Reviewed") ,
    CLOSED("Closed") ,
    HUDLE("Hudle") ,
    INPROCESS("In Process"),
    POSTPONDED("PostPoned"),
    FEEDBACK("Feedback");



    private String code ;

    STATE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static STATE fromCode(String code){

        for(STATE state : STATE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }

        return null ;
    }

    public static STATE fromInt(int code){

        for(STATE state : STATE.values()){
            if(state.ordinal()==code){
                return state ;
            }
        }
        return null ;
    }
}
