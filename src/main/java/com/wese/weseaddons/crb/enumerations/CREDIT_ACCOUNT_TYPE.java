/*Created by Sinatra Gunda
  At 12:05 PM on 2/24/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CREDIT_ACCOUNT_TYPE {

    I("Installment account representing transactions opening balance will be equal to the original"),
    R("Revolving credit account where purchases and payments are made within a given credit facility"),
    O("Open account without credit limit but where total owing, as reflected on monthly statement, is due and payable on a date as advised on the statement"),
    F("Credit Card Account (Banks only) - Do the banks in Zambia issue Credit Cards"),
    H("Home Loans / Mortgage"),
    E("Single Credit Facility (Multiple products under one facility"),
    V("Overdraft (to supply opening balance as overdraft limit and current balance as overdraft balance"),
    N("Pension backed lending"),
    T("Student Loan"),
    D("Debt recovery account, i.e. an account held by debt collection company"),
    C("Line of Credit"),
    G("Guarantee Type");

    private String code ;

    CREDIT_ACCOUNT_TYPE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static CREDIT_ACCOUNT_TYPE fromInt(int i){

        for(CREDIT_ACCOUNT_TYPE c : CREDIT_ACCOUNT_TYPE.values()){
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
