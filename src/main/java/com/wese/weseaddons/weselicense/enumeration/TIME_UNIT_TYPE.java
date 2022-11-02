package com.wese.weseaddons.weselicense.enumeration;

import com.wese.weseaddons.taskmanager.enumerations.DURATION_TYPE;

public enum TIME_UNIT_TYPE {

    SECONDS("Seconds"),
    MINUTES("Minutes"),
    HOURS("Hours"),
    DAYS("Days"),
    WEEKS("Weeks"),
    MONTHS("Months"),
    YEARS("Years");

    private String code ;

    TIME_UNIT_TYPE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static TIME_UNIT_TYPE fromCode(String code){

        for(TIME_UNIT_TYPE state : TIME_UNIT_TYPE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }
        return null ;
    }

    public static TIME_UNIT_TYPE fromInt(int code){

        for(TIME_UNIT_TYPE state : TIME_UNIT_TYPE.values()){
            if(state.ordinal() == code){
                return state ;
            }
        }
        return null ;
    }

}
