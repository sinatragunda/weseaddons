/*

    Created by Sinatra Gunda
    At 9:18 AM on 9/29/2022

*/
package com.wese.weseaddons.helper;

import com.wese.weseaddons.batchprocessing.enumerations.TRANSACTION_STATUS;
import com.wese.weseaddons.pojo.DeveloperErrors;
import com.wese.weseaddons.pojo.MakerCheckerEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map ;

public class ErrorMessageTranslator {

    public static Map<String ,String> message = new HashMap<>();

    public static String getMessage(String key){

        if(message.isEmpty()){
            message.put("validation.msg.sharesaccount.purchase.transaction.date.cannot.be.before.existing.transactions","Failed shares purchase ,transaction date cannot be before existing transactions");
            message.put("error.msg.loan.transaction.cannot.be.before.disbursement.date" ,"Loan transaction cannot be before disbursement date");
        }

        return message.getOrDefault(key ,"No valid error message");
    }

    public static String getMessage(MakerCheckerEntry makerCheckerEntry){
        List<DeveloperErrors> errors = makerCheckerEntry.getErrors();
        DeveloperErrors developerErrors = errors.stream().findFirst().get();
        String key = developerErrors.getUserMessageGlobalisationCode();
        return getMessage(key);
    }

    public static void transactionStatus(MakerCheckerEntry makerCheckerEntry ,TRANSACTION_STATUS transactionStatus){
        String message = getMessage(makerCheckerEntry);
        transactionStatus.setCode(message);
    }
}
