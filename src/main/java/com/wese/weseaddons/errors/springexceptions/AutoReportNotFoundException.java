/*Created by Sinatra Gunda
  At 2:51 AM on 2/17/2021 */

package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.errors.WeseErrorService;

public class AutoReportNotFoundException extends RuntimeException implements WeseErrorService {


    private String reportName ;

    public AutoReportNotFoundException(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public String getMessage() {
    	return String.format("%s can not be run as an auto report.",reportName);
    }
}
