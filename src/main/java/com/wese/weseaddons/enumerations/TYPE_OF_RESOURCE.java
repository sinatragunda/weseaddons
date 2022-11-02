/*Created by Sinatra Gunda
  At 5:54 AM on 2/25/2020 */

package com.wese.weseaddons.enumerations;

public enum TYPE_OF_RESOURCE {

    CREDIT_UPDATE("Credit Update"),
    LOAN("Loan") ;

    private String code ;

    TYPE_OF_RESOURCE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static TYPE_OF_RESOURCE fromInt(int i){

        for(TYPE_OF_RESOURCE c: TYPE_OF_RESOURCE.values()){
            if(c.ordinal()==i){
                return c ;
            }
        }

        return null ;
    }
}
