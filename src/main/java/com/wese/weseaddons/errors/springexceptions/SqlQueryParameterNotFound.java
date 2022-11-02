/*Created by Sinatra Gunda
  At 2:51 AM on 2/17/2021 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class SqlQueryParameterNotFound extends RuntimeException implements WeseErrorService {


    private String param ;

    public SqlQueryParameterNotFound(String param) {
        this.param = param ;
    }

    @Override
    public String getMessage() {
        return String.format("%s is missing a valid input .Please not its a mandatory parameter for this report.",param);
    }
}
