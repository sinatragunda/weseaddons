
package com.wese.weseaddons.batchprocessing.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.client.Client;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.batchprocessing.client.LoanProduct;
import com.wese.weseaddons.batchprocessing.client.User;
import com.wese.weseaddons.batchprocessing.wese.Office;


import java.util.List;

public class ResponseBuilder {



//    public static ObjectNode transactionsResponse(){
//
//
//
//    }

    public static ObjectNode getObjectNode(){

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.createObjectNode();

    }

    public static ObjectNode invalidDate(int validateDate){


        ObjectNode objectNode = getObjectNode();

        objectNode.put("status" ,false);

        if(validateDate==-1){

            objectNode.put("message" ,"Invalid date ,please enter in specified preferred format");
            return objectNode ;

        }

        objectNode.put("message" ,"Invalid date ,date is in the future");
        return objectNode ;
    }

    public static ObjectNode genericFailure(String message){

        ObjectNode objectNode = getObjectNode();

        objectNode.put("status" ,false);
        objectNode.put("message" ,message);
        return objectNode ;

    }
    public static ObjectNode authentication(boolean isAuthenticated ,User user){

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        if(!isAuthenticated){

            objectNode.put("authentication" ,isAuthenticated);
            return objectNode ;
        }


        objectNode.put("authentication" ,isAuthenticated);
        objectNode.put("username" ,user.getUsername());
        return objectNode ;

    }

    public static ObjectNode offices(List<Office> officeList){

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        ObjectNode objectNode = ResponseBuilder.getObjectNode();

        for(int i =0 ; i < officeList.size() ;++i){

            Office office = officeList.get(i);
            ObjectNode objectNode1 = ResponseBuilder.getObjectNode();
            objectNode1.put("id",office.getId());
            objectNode1.put("name",office.getName());
            arrayNode.addPOJO(objectNode1);
        }

        objectNode.putPOJO("offices",arrayNode);
        return objectNode ;

    }

    public static ObjectNode loanProducts(List<LoanProduct> loanProductList){

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        ObjectNode objectNode = ResponseBuilder.getObjectNode();

        for(int i =0 ; i < loanProductList.size() ;++i){

            LoanProduct loanProduct = loanProductList.get(i);
            ObjectNode objectNode1 = ResponseBuilder.getObjectNode();
            objectNode1.put("id",loanProduct.getId());
            objectNode1.put("name",loanProduct.getName());
            arrayNode.addPOJO(objectNode1);
        }

        objectNode.putPOJO("loanproducts",arrayNode);
        return objectNode ;

    }

    public static ObjectNode transactionError(ExcelClientData e, Client client){

        ObjectNode objectNode1 = ResponseBuilder.getObjectNode();
        objectNode1.put("Name",e.getName());
        objectNode1.put("NRC Number" ,e.getNrcNumber());

        if(client !=null){

            objectNode1.put("Account Number",client.getAccountNumber());
        }


        return objectNode1 ;

    }
}
