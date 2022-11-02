package com.wese.weseaddons.ussd.exceptions;

public class ParseDoubleException extends NumberFormatException{

    @Override
    public String getMessage(){

        return "Int failed to parse to double";

    }

    public ParseDoubleException(){

    }



}
