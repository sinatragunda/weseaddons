package com.wese.weseaddons.taskmanager.enumerations;

public enum ACCOUNT_TYPE {

    NAN("Nan"),
    LOAN("Loan"),
    SAVINGS("Savings");
  
    ACCOUNT_TYPE(String code){
        this.code = code ;
    }

    private String code ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ACCOUNT_TYPE fromCode(String code){

        for(ACCOUNT_TYPE state : ACCOUNT_TYPE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }
        return null ;
    }

    public static ACCOUNT_TYPE fromInt(int code){

        for(ACCOUNT_TYPE state : ACCOUNT_TYPE.values()){
            if(state.ordinal()== code){
                return state ;
            }
        }
        return null ;
    }

}
