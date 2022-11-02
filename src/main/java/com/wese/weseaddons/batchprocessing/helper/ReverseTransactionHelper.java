/*

    Created by Sinatra Gunda
    At 12:10 PM on 3/18/2022

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.enumerations.PROCESS_STATUS;
import com.wese.weseaddons.batchprocessing.init.SSBPaymentsInit;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.batchprocessing.enumerations.PORTFOLIO_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.SSB_REPORT_TYPE;
import com.wese.weseaddons.batchprocessing.pojo.SSbTransactionsAdapter;
import com.wese.weseaddons.batchprocessing.pojo.SsbPaymentsResults;
import com.wese.weseaddons.enumerations.STATUS;
import com.wese.weseaddons.errors.springexceptions.SsbPaymentsReverseOnFailException;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.helper.ObjectNodeHelper;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.networkrequests.SharesRequest;
import com.wese.weseaddons.pojo.DeveloperErrors;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.UndoTransaction;
import com.wese.weseaddons.utility.ThreadContext;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReverseTransactionHelper {

    public static void reverseTransactions(List<ExcelClientData> excelClientDataList){

        MakerCheckerEntry makerCheckerEntry = null ;

        ArrayNode arrayNode = ObjectNodeHelper.createArrayNode();

        // sort this list using timestamp with the most recent having to be reversed ,the last transaction being reverse first
        // Collections.reverse(excelClientDataList);
        Comparator<ExcelClientData> transactionsSorter = Comparator.comparing(ExcelClientData::getTimestamp);

        Predicate<ExcelClientData> filterNull = (e)-> Optional.ofNullable(e.getTimestamp()).isPresent();
    
        List<ExcelClientData> timeSortedList =  excelClientDataList.stream().filter(filterNull).sorted(transactionsSorter.reversed()).collect(Collectors.toList());

        for (ExcelClientData clientData : timeSortedList) {

            PORTFOLIO_TYPE portfolioType = clientData.getPortfolioType();
            SSB_REPORT_TYPE ssbReportType = clientData.getSsbReportType();

            boolean isReversable = SsbPaymentsResultHelper.isTransactionReversable(ssbReportType);

            if (isReversable){
                makerCheckerEntry = reverseTransactions(clientData, portfolioType);
                clientData = updateTransactionStatus(makerCheckerEntry, clientData);
                SsbPaymentsResultHelper.updateResults(clientData, ssbReportType, portfolioType);
                SsbPaymentsResultsNodeHelper.fillSpecificNode(clientData,ssbReportType ,portfolioType ,arrayNode);
            }
        }

        ObjectNode objectNode = ObjectNodeHelper.createObjectNode();
        objectNode.put("pageItems" ,arrayNode);
        SSBPaymentsInit.getInstance().saveProcessResults(objectNode);

        // here results been updated maybe fill our results node ? ,is there a map or something
    }

    private static Long getResourceId(ExcelClientData excelClientData){
        return excelClientData.getResourceId();
    }


    private static ExcelClientData updateTransactionStatus(MakerCheckerEntry makerCheckerEntry , ExcelClientData excelClientData){

        Optional.ofNullable(makerCheckerEntry).ifPresent(e->{
            //boolean success = makerCheckerEntry.success();
            excelClientData.setReversed(true);
            //excelClientData.getStatusDescription();
            excelClientData.setStatus(STATUS.REVERSED);
            String description = String.format("%s transaction reversed sucessfully",excelClientData.getPortfolioType().name());

            excelClientData.setStatusDescription(description);
            boolean valid = makerCheckerEntry.getErrors().isEmpty();

            if(!valid){
                DeveloperErrors developerErrors = makerCheckerEntry.getErrors().get(0);
                String error = developerErrors.getDeveloperMessage();
                description = String.format("%s failed to reverse ,%s" ,excelClientData.getPortfolioType().name() ,error);
                excelClientData.setStatusDescription(description);
                excelClientData.setStatus(STATUS.FAILED);
            }
        });

        return excelClientData;
    }

    private static MakerCheckerEntry reverseTransactions(ExcelClientData excelClientData,PORTFOLIO_TYPE portfolioType){

        Long transactionId = getResourceId(excelClientData);
        Long accountId = excelClientData.getObjectId();
        String transactionDate = excelClientData.getTransactionDate();
        MakerCheckerEntry makerCheckerEntry = null ;
        UndoTransaction undoTransaction = new UndoTransaction(transactionDate);

        String jsonRequest = JsonHelper.objectToJson(undoTransaction);

        switch (portfolioType){
            case LOANS:
                makerCheckerEntry = LoansRequest.reverseLoanTransaction(accountId , transactionId ,jsonRequest);
                break;
            case SAVINGS:
                makerCheckerEntry = SavingsRequest.reverseTransaction(accountId ,transactionId ,jsonRequest);
                break;
            case SHARES:
                undoTransaction.setTransactionAmount(null);
                jsonRequest = JsonHelper.objectToJson(undoTransaction);
                makerCheckerEntry = SharesRequest.reverseShareTransaction(transactionId ,jsonRequest);
                break;

        }
        return makerCheckerEntry ;
    }

    /**
     * Added 14/10/2022 at 0334
     * Fail safe reversal function
     * Logs the transaction that has failed and reverse all transactions
     */

    public static void reverseOnFail(ExcelClientData record ,SSbTransactionsAdapter sSbTransactionsAdapter, boolean isReverseOnFail ,SsbPaymentsResults ssbPaymentResult){
        if(isReverseOnFail){
            String tenant = ThreadContext.getTenant();
            SSBPaymentsInit.getInstance().updateProcessStatus(tenant, PROCESS_STATUS.FAILED);
            SSBPaymentsInit.getInstance().saveProcessResults(tenant,ssbPaymentResult ,null);
            reverseTransactions(sSbTransactionsAdapter);
            throw new SsbPaymentsReverseOnFailException(record);
        }
    }
}
