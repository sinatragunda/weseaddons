package com.wese.weseaddons.bereaudechange.exceptions;

public class CurrencyNameException extends RuntimeException {

    private String message ;

    public CurrencyNameException(String message){
        this.message = message ;
    }

    public String getMessage(){
        return this.message ;
    }
}
