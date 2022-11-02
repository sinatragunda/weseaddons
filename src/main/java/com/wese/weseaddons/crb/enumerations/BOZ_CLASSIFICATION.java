/*Created by Sinatra Gunda
  At 5:29 PM on 2/23/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BOZ_CLASSIFICATION {

    P("0-89 Days"),
    S("Substandard (90-119 Days"),
    D("Doubtful 120-179 Days"),
    L("Loss (180+ Days)");

    String code ;

    BOZ_CLASSIFICATION(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }


    public static BOZ_CLASSIFICATION fromDays(int days){

        if(days < 89){
            return BOZ_CLASSIFICATION.P;
        }

        if(days > 89 && days < 119){
            return BOZ_CLASSIFICATION.S ;
        }

        if(days > 120 && days < 179){
            return BOZ_CLASSIFICATION.D ;
        }

        if(days > 180){
            return BOZ_CLASSIFICATION.L ;
        }
        return null ;
    }

    @JsonValue
    public String value(){
        return name() ;
    }
}
