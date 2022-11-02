/*Created by Sinatra Gunda
  At 6:32 PM on 2/27/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CRB_RISK_GRADE {

    YY("No accounts / Not enough information"),
    Z1("Under 18 years of age"),
    Z2("Individual is deceased");

    private String code ;

    CRB_RISK_GRADE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static CRB_RISK_GRADE fromInt(int i){

        for(CRB_RISK_GRADE c : CRB_RISK_GRADE.values()){
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
