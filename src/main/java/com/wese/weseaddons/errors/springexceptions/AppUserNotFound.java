/*Created by Sinatra Gunda
  At 10:54 on 7/27/2020 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class AppUserNotFound extends RuntimeException implements WeseErrorService{


    private long appUserId ;
    private String message = String.format("Failed to find app user data for user with id %d\n",appUserId);

    public AppUserNotFound(long id){
        this.appUserId = id ;
    }

    public String getMessage(){
        return message ;
    }
}
