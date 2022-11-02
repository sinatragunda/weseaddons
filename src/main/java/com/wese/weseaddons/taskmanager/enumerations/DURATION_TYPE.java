package com.wese.weseaddons.taskmanager.enumerations;

public enum DURATION_TYPE {

    HOUR("Hour"),
    DAYS("Days"),
    WEEKS("Weeks"),
    MONTHS("Months");

    private String code ;

    DURATION_TYPE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static DURATION_TYPE fromCode(String code){

        for(DURATION_TYPE state : DURATION_TYPE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }
        return null ;
    }
    public static DURATION_TYPE fromInt(int code){

        for(DURATION_TYPE state : DURATION_TYPE.values()){
            if(state.ordinal()==code){
                return state ;
            }
        }
        return null ;
    }
}
