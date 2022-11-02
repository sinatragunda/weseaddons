package com.wese.weseaddons.bereaudechange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FailedFxDealException extends RuntimeException {

    private String message ;

    public FailedFxDealException(String s){

        super(s);
        this.message = s ;
    }

    public String getMessage(){
        return message;
    }
}
