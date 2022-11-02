package com.wese.weseaddons.batchprocessing.enumerations;


public enum TRANSACTION_STATUS {

    REVERSED("Reversed",false),
    FAILED("",false),
    CLIENT_NOT_FOUND("",false) ,
    NO_ACTIVE_LOANS("",false) ,
    NO_ACTIVE_SAVINGS_ACCOUNT("",false) ,
    FAILED_DEPOSIT("",false),
    ADJUSTED_DEPOSIT("",true),
    NO_ACTIVE_SHARE_ACCOUNT("",false),
    SHARES_BOUGHT_SUCCESSFULLY("",true),
    SHARE_TRANSACTION_PENDING("",true),
    MAXIMUM_SHARES_REACHED("",true),
    NO_PRESHARE("",true),
    LOAN_REAPYMENT_WAITING_APPROVAL("",true),
    LOAN_REPAYMENT_SUCCESS("",true),
    LOAN_REPAYMENT_FAILED("",false),
    LOAN_REPAYMENT_SKIPPED("",true),
    SHARES_INTERNAL_ERROR("",false);

    private String code ;
    private boolean success;

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code ;
    }

    public boolean isSuccess(){
        return this.success;
    }

    TRANSACTION_STATUS(String code ,boolean isSuccess){
        this.code = code ;
        this.success = isSuccess;
    }


}
