/*

    Created by Sinatra Gunda
    At 9:28 AM on 3/12/2021

*/
package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class FailedToReadWorkbookException extends RuntimeException implements WeseErrorService{

    private String message = "System has failed to process your excel file ,please reupload a valid excel file";

    public String getMessage(){
        return message ;
    }
}
