/*

    Created by Sinatra Gunda
    At 5:05 AM on 6/20/2021

*/
package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class SSbReportEmptyException extends RuntimeException implements WeseErrorService {

    String type ;

    public SSbReportEmptyException(String type){
        this.type = type ;
    }

    @Override
    public String getMessage() {
        return String.format("%s is empty ,no valid data from last processing",type);
    }
}
