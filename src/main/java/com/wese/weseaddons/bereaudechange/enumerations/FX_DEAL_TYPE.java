package com.wese.weseaddons.bereaudechange.enumerations;

public enum FX_DEAL_TYPE {

    SPOT("Spot"),
    FOWARD("Forward"),
    BULK("Bulk");

    private String code ;

    FX_DEAL_TYPE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static FX_DEAL_TYPE fromInt(int i){

        for(FX_DEAL_TYPE c : FX_DEAL_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }
}
