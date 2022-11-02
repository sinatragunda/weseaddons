package com.wese.weseaddons.bereaudechange.enumerations;

import java.util.ArrayList;
import java.util.List;

public enum PAYMENT_ACTIONS {


    CASH("Cash") ,
    MONEY_TRANSFER("Money Transfer") ,
    FNB_EWALLET("FNB E-Wallet") ,
    ECOCASH("EcoCash") ,
    ONE_WALLET("One Wallet");

    private String code ;

    PAYMENT_ACTIONS(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static PAYMENT_ACTIONS fromInt(int i){

        for(PAYMENT_ACTIONS c : PAYMENT_ACTIONS.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }

    public static List<String> getPaymentsList(){

        List<String> paymentMethodsList = new ArrayList();

        for(PAYMENT_ACTIONS c : PAYMENT_ACTIONS.values()){
            paymentMethodsList.add(c.getCode());
        }

        return paymentMethodsList ;
    }
}
