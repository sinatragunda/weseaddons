/*Created by Sinatra Gunda
  At 21:38 on 8/6/2020 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class DependantNull extends RuntimeException implements WeseErrorService {

    public String getMessage(){
        return "Data dependency is null ";
    }
}
