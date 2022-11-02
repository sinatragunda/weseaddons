/*

    Created by Sinatra Gunda
    At 4:39 AM on 3/21/2022

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.enumerations.PORTFOLIO_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.SSB_REPORT_TYPE;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.batchprocessing.pojo.SsbPaymentsResults;
import com.wese.weseaddons.enumerations.STATUS;

import java.util.List;
import java.util.Optional;

public class SsbPaymentsResultsNodeHelper {


    public static void fillSpecificNode(ExcelClientData excelClientData , SSB_REPORT_TYPE ssbReportType , PORTFOLIO_TYPE portfolioType , ArrayNode arrayNode) {

            Optional.ofNullable(excelClientData).ifPresent(e->{
                ObjectNode objectNode = ResponseBuilder.getObjectNode();
                objectNode.put("count", excelClientData.getCount());
                objectNode.put("amount", excelClientData.getAmount());
                objectNode.put("externalId", excelClientData.getNrcNumber());
                objectNode.put("status", excelClientData.getStatus().name());
                objectNode.put("portfolioType",portfolioType.getCode());
                objectNode.put("accountNumber", excelClientData.getLoanAccountNumber());
                objectNode.put("clientName", excelClientData.getName());
                objectNode.put("resultsDescription", excelClientData.getStatusDescription());
                objectNode.put("resourceId" ,excelClientData.getResourceId());
                objectNode.put("timestamp" ,excelClientData.getTimestamp());
                objectNode.put("reportType" ,ssbReportType.name());
                objectNode.put("reversed",excelClientData.isReversed());
                objectNode.put("objectId" ,excelClientData.getObjectId());

                excelClientData.setSsbReportType(ssbReportType);
                excelClientData.setPortfolioType(portfolioType);

                arrayNode.add(objectNode);
            });
    }


}
