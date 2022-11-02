/*Created by Sinatra Gunda
  At 10:21 on 2/17/2021 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class SqlQueryEmptyResultsException extends RuntimeException  implements WeseErrorService{


    public SqlQueryEmptyResultsException(){}

    @Override
    public String getMessage() {
        return String.format("The report you have run has empty results ,no data available at the moment");
    }
}
