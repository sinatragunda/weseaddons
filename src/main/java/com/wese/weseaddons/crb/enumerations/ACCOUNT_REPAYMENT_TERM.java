/*Created by Sinatra Gunda
  At 11:49 AM on 2/24/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ACCOUNT_REPAYMENT_TERM {

    ODD("On demand"),
    DLY("Daily"),
    WKY("Weekly"),
    FNY("Fortnightly"),
    MTH("Months"),
    QTR("Quarterly"),
    HYR("Half-yearly"),
    ANN("Annually"),
    BUL("Bullet (one lump sum upon maturity"),
    REV("Revolving (e.g. for revolving credit, letter of credit, trade and foreign exchange contract"),
    IDF("Indefinite (e.g. for overdraft"),
    IRR("Irregular schedule");

    String code ;

    public String getCode(){
        return code ;
    }

    ACCOUNT_REPAYMENT_TERM(String code){
        this.code = code ;
    }

    public static ACCOUNT_REPAYMENT_TERM fromString(String arg){
        
        System.err.print("Repayment terms is "+arg);

        for(ACCOUNT_REPAYMENT_TERM c : ACCOUNT_REPAYMENT_TERM.values()){
            if(c.getCode().equalsIgnoreCase(arg)){
                return c ;
            }
        }
        return ACCOUNT_REPAYMENT_TERM.MTH ;
    }

    @JsonValue
    public String value(){
        return name() ;
    }
}
