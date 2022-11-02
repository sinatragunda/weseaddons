package com.wese.weseaddons.bereaudechange.exceptions;
import com.wese.weseaddons.errors.WeseErrorService;

public class FxDealSumsException extends RuntimeException implements WeseErrorService{

    @Override
    public String getMessage(){

        return new String("Failed to proceed one or more accounting settings not set in Fx Organisational Settings");

    }
}