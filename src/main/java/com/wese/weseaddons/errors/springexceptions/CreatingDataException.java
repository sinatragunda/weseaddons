/*Created by Sinatra Gunda
  At 20:02 on 8/6/2020 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class CreatingDataException extends RuntimeException implements WeseErrorService{


    private String message ;

    public CreatingDataException(String message){
        this.message = message ;
    }

    public String getMessage(){

        return String.format("Failed to create %s item ,data exception",message);
    }
}
