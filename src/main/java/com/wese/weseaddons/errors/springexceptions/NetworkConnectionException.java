/*Created by Sinatra Gunda
  At 3:50 AM on 2/25/2020 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class NetworkConnectionException extends RuntimeException implements WeseErrorService{


    @Override
    public String getMessage() {
        return "Network error ,failed to connect to server .Please check your internet settings";
    }
}
