/*Created by Sinatra Gunda
  At 11:52 AM on 2/24/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ACCOUNT_STATUS {


    A("Active"),
    C("Closed (obligations met)"),
    D("Disputed"),
    I("Credit Card Revoked"),
    R("Repossession (Goods have been repossessed due to non-payment"),
    L("Handed Over/Legal (Account handed over to attorney or collection agency for recovery"),
    N("Non-Performing Loan"),
    B("Paid Up (Account paid up but can be active e.g. Revolving Credit"),
    T("Early Settlement (Loan settled early by customer"),
    W("Written Off/Charge off (Account written off as a bad debt due to non-payment"),
    X("Paid up default (Account paid up subsequent to a Status Code of J,I,L or W"),
    O("Overdrafts"),
    U("Unpaid Credit Card"),
    J("Returned/ Bounced Cheques");


    String code ;

    public String getCode(){
        return code ;
    }

    ACCOUNT_STATUS(String code){
        this.code = code ;
    }

    public static ACCOUNT_STATUS fromString(String arg){

        for(ACCOUNT_STATUS c : ACCOUNT_STATUS.values()){
            if(c.getCode().equalsIgnoreCase(arg)){
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
