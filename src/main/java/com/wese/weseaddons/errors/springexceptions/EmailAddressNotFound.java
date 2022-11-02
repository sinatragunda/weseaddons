/*Created by Sinatra Gunda
  At 10:51 on 7/27/2020 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class EmailAddressNotFound extends RuntimeException implements WeseErrorService{

    private String message = "Failed to send email ,recipient email address not found";

    public String getMessage(){
        return message ;
    }
}
