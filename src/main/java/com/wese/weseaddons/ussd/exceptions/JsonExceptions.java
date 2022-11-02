package com.wese.weseaddons.ussd.exceptions;

import org.json.JSONException;

public class JsonExceptions extends RuntimeException{


    private String message ;

    public JsonExceptions(){}

    public JsonExceptions(String message){
        this.message = message ;
    }

    public String getMessage(){

        return message ;
    }
}
