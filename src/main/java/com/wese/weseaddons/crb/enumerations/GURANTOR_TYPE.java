/*Created by Sinatra Gunda
  At 4:44 PM on 2/23/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GURANTOR_TYPE {

    A("Directors Guarantee"),
    B("Personal Guarantee"),
    C("Corporate Guarantee"),
    D("Bank Guarantee"),
    E("Sovereign Guarantee");


    private String code ;

    GURANTOR_TYPE(String c){
        this.code =c ;
    }

    public String getCode() {
        return code;
    }

    public static GURANTOR_TYPE fromInt(int i){

        for(GURANTOR_TYPE c : GURANTOR_TYPE.values()){
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
