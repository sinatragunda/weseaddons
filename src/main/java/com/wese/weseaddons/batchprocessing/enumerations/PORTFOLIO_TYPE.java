/*

    Created by Sinatra Gunda
    At 4:23 PM on 8/17/2021

*/
package com.wese.weseaddons.batchprocessing.enumerations;

public enum PORTFOLIO_TYPE {

    LOANS("Loans"),
    SAVINGS("Savings"),
    SHARES("Shares"),
    CLIENTS("Clients"),
    DISCARD("Discard");

    PORTFOLIO_TYPE(String code){
        this.code = code ;
    }

    private String code ;

    public String getCode() {
        return code;
    }

    public static PORTFOLIO_TYPE fromInt(int arg){
        for(PORTFOLIO_TYPE val : values()){
            if(val.ordinal()==arg){
                return val ;
            }
        }
        return null ;
    }
}
