package com.wese.weseaddons.taskmanager.enumerations;

public enum TASK_TYPE {

    LOAN("Loan"),
    SAVINGS("Savings"),
    REPAYMENTS("Repayment"),
    ANALYSIS("Analysis");


    TASK_TYPE(String code){
        this.code = code ;
    }

    private String code ;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static TASK_TYPE fromCode(String code){

        for(TASK_TYPE state : TASK_TYPE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }
        return null ;
    }

    public static TASK_TYPE fromInt(int code){

        for(TASK_TYPE state : TASK_TYPE.values()){
            if(state.ordinal() ==code ){
                return state ;
            }
        }
        return null ;
    }
}
