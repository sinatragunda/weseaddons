package com.wese.weseaddons.weselicense.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.weselicense.pojo.Tenant;

import java.util.List;
import java.util.concurrent.TimeoutException;

public class ObjectNodeBuilder {


    public static ObjectNode buildExchangeRateConverterNode(String currencyCode , double amount){

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("currency" ,currencyCode);
        objectNode.put("amount" ,amount);

        return objectNode ;

    }

    public static ObjectNode accountStatusNode(boolean status , long daysRemaining ,boolean showWarning){

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("status" ,status);
        objectNode.put("daysRemaining" ,daysRemaining);
        objectNode.put("showWarning" ,showWarning);

        return objectNode ;

    }
}
