/*

    Created by Sinatra Gunda
    At 12:48 PM on 6/11/2021

*/
package com.wese.weseaddons.batchprocessing.enumerations;

public enum PRE_LOADING_ACCOUNT {

    SHARE_ACCOUNT,
    SAVINGS_ACCOUNT;

    public PRE_LOADING_ACCOUNT fromInt(int arg){

        for(PRE_LOADING_ACCOUNT a : PRE_LOADING_ACCOUNT.values()){
            if(a.ordinal()==arg){
                return a ;
            }
        }
        return null ;
    }
}
