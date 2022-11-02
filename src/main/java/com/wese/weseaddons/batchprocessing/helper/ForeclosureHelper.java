/*Created by Sinatra Gunda
  At 16:53 on 9/3/2020 */

package com.wese.weseaddons.batchprocessing.helper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.networkrequest.ForeclosureRequest;
import com.wese.weseaddons.batchprocessing.pojo.Foreclosure;
import com.wese.weseaddons.helper.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForeclosureHelper {


    private Foreclosure copy ;
    private ForeclosureRequest foreclosureRequest ;
    private List<Foreclosure> successfulForeclosure ;
    private List<Foreclosure> failedForeclosure ;
    private List<ObjectNode> objectNodesList ;

    public Consumer<Foreclosure> forecloseConsumer = (e)-> {

        e.setNote(copy.getNote());
        e.setTransactionDate(copy.getTransactionDate());

        Long clientId = foreclosureRequest.foreclose(e);

        if(clientId==null){
            failedForeclosure.add(e);
            return;
        }
        successfulForeclosure.add(e);

    };


    public ForeclosureHelper(String tenantIdentifier,Foreclosure copy){

        this.copy = copy ;
        this.foreclosureRequest = new ForeclosureRequest(tenantIdentifier);
        successfulForeclosure = new ArrayList<>();
        failedForeclosure = new ArrayList<>();
        objectNodesList = new ArrayList<>();
    }

    public void processUpdateNode(List<Foreclosure> list , String status , String description) {

        for(Foreclosure foreclosure : list){

            ObjectNode objectNode = ResponseBuilder.getObjectNode();
            objectNode.put("count", foreclosure.getCount());
            objectNode.put("status", status);
            objectNode.put("loanAccountNumber", foreclosure.getLoanAccountNumber());
            objectNode.put("clientName", foreclosure.getCustomerName());
            objectNode.put("resultsDescription", description);
            objectNodesList.add(objectNode);
        }
    }

    public ArrayNode results(){

        processUpdateNode(failedForeclosure ,"Failed" ,"Failed to close loan or loan already closed");
        processUpdateNode(successfulForeclosure ,"Successfull" ,"Sucusfully foreclosed");
        ArrayNode arrayNode = Helper.createArrayNode();
        for(ObjectNode objectNode : objectNodesList){
            arrayNode.add(objectNode);
        }

        return arrayNode ;
    }

}
