/*Created by Sinatra Gunda
  At 20:39 on 6/29/2020 */

package com.wese.weseaddons.bereaudechange.exceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class ClientNotRegisteredToService extends RuntimeException implements WeseErrorService{

    public String getMessage(){
        return new String("Client is not registered for this service");
    }
}
