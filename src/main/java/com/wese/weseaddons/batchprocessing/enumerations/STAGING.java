/*

    Created by Sinatra Gunda
    At 1:00 PM on 6/23/2021

*/
package com.wese.weseaddons.batchprocessing.enumerations;

import javax.validation.constraints.Null;

public enum STAGING {

    NONE(""),
    FAILED_ON_CLIENT_SEARCH(""),
    FAILED_ON_DDA_DEPOSIT(""),
    FAILED_ON_SHARES_PURCHASE(""),
    STOPPED_ON_SHARES_PENDING(""),
    FAILED_ON_NO_ACTIVE_LOANS(""),
    FAILED_ON_SAVINGS_DEPOSIT(""),
    FAILED_ON_LOAN_REPAYMENT("") ,
    FAILED_ON_MOVING_EXCESS_BALANCE(""),
    SUCCESS("");

    private String code;

    STAGING(String code){
        this.code = code ;
    }
    public String getCode(){
        return this.code ;
    }

    public static STAGING fromInt(Integer arg){
        try {
            for (STAGING staging : values()) {
                if (arg == staging.ordinal()) {
                    return staging;
                }
            }
        }
        catch (NullPointerException n){

        }
        return null ;
    }
}
