/*

    Created by Sinatra Gunda
    At 3:40 AM on 10/14/2022

*/
package com.wese.weseaddons.errors.springexceptions;

import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.errors.WeseErrorService;

public class SsbPaymentsReverseOnFailException extends RuntimeException implements WeseErrorService {

    private ExcelClientData record ;

    public SsbPaymentsReverseOnFailException(ExcelClientData record){
        this.record = record;

    }

    @Override
    public String getMessage() {
        return String.format("Auto reverse activated ,processing failed on transaction with details : %s",record.processDetails());
    }
}
