/*Created by Sinatra Gunda
  At 11:32 AM on 2/24/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum  GENDER {

    M("Male"),
    F("Female");

    String code ;

    public String getCode(){
        return code ;
    }

    GENDER(String code){
        this.code = code ;
    }

    public static GENDER fromString(String arg){

        for(GENDER c : GENDER.values()){
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
