/*

    Created by Sinatra Gunda
    At 9:52 AM on 9/29/2022

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.batchprocessing.enumerations.SSB_REPORT_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.TRANSACTION_STATUS;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;

import java.util.Optional;

public class TransactionStatusHelper {

    public static String description(SSB_REPORT_TYPE ssbReportType , ExcelClientData excelClientData){

        TRANSACTION_STATUS transactionStatus = excelClientData.getTransactionStatus();

        String message[] ={ssbReportType.getCode()};
        Optional.ofNullable(transactionStatus).ifPresent(e->{
            message[0] = transactionStatus.getCode();
        });
        return message[0];
    }
}
