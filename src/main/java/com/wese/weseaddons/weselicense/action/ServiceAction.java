package com.wese.weseaddons.weselicense.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.weselicense.enumeration.SERVICE_TYPE;


public class ServiceAction {

    public ObjectNode getServices(){

        ArrayNode arrayNode = Helper.createArrayNode();


        for(SERVICE_TYPE type : SERVICE_TYPE.values()){

            ObjectNode objectNode = Helper.createObjectNode();
            objectNode.put("type",type.toString());
            arrayNode.addPOJO(objectNode);
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.putPOJO("pageItems",arrayNode);
        return objectNode ;
    }
}
