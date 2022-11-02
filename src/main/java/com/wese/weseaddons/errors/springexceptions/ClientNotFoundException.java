/*Created by Sinatra Gunda
  At 4:08 AM on 2/25/2020 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class ClientNotFoundException extends RuntimeException implements WeseErrorService{


    private long clientId ;

    public ClientNotFoundException(long clientId){
        this.clientId = clientId ;
    }

    @Override
    public String getMessage() {
        return String.format("Corresponding client with id %d has not been found",clientId);
    }
}
