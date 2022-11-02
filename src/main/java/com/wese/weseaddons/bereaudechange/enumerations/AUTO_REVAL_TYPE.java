package com.wese.weseaddons.bereaudechange.enumerations;

public enum AUTO_REVAL_TYPE {

    DAILY("Daily") ,
    WEEKLY("Weekly") ,
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    private String code ;

    AUTO_REVAL_TYPE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static AUTO_REVAL_TYPE fromInt(int i){

        for(AUTO_REVAL_TYPE c : AUTO_REVAL_TYPE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }


}
