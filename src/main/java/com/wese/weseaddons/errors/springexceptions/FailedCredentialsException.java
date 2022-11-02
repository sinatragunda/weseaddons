/*

    Created by Sinatra Gunda
    At 9:26 AM on 9/14/2022

*/
package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class FailedCredentialsException extends RuntimeException implements WeseErrorService {

    public FailedCredentialsException(){}

    public String getMessage(){
        return "Failed to validate credentails";
    }
}
