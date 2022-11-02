package com.wese.weseaddons.ussd.exceptions;

public class ArrayBoundsException extends IndexOutOfBoundsException {

    private String message ;

    public ArrayBoundsException(String message){
        this.message = message;
    }

    public String getMessage(){

        return message ;
    }

}
