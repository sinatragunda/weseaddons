package com.wese.weseaddons.weselicense.enumeration;


public enum SERVICE_TYPE {

    ADMINISTRATION("Administration"),
    CONSUTATION("Consultation"),
    TRAINING("Training");

    private String code ;

    SERVICE_TYPE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static SERVICE_TYPE fromCode(String code){

        for(SERVICE_TYPE state : SERVICE_TYPE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }
        return null ;
    }

    public static SERVICE_TYPE fromInt(int code){

        for(SERVICE_TYPE state : SERVICE_TYPE.values()){
            if(state.ordinal() == code){
                return state ;
            }
        }
        return null ;
    }
}
