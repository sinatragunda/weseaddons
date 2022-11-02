/*Created by Sinatra Gunda
  At 10:11 AM on 2/27/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum STATUS_UPDATE_REASON {

    MONITORING_CREDIT("Monitoring credit / payments update"),
    LOAN_DISBURSEMENT("Loan Disbusrement"),
    LOAN_APPLICATION_REJECT("Reject"),
    NEW_LOAN("Loan Application");


    private String code ;

    STATUS_UPDATE_REASON(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static STATUS_UPDATE_REASON fromInt(int i){

        for(STATUS_UPDATE_REASON c : STATUS_UPDATE_REASON.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }
        return null ;
    }

    @JsonValue
    public String value(){
        return name() ;
    }
}
