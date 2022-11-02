package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.StandardCurrencyDAO;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class StandardCurrencyAction {

    private String tenant ;

    public StandardCurrencyAction(String tenant) {
        this.tenant = tenant;
    }

    public ObjectNode create(StandardCurrency standardCurrency){

        StandardCurrencyDAO standardCurrencysDAO = new StandardCurrencyDAO(tenant);
        
        if(standardCurrencysDAO.create(standardCurrency)){
            return Helper.statusNodes(true);
        };

        return Helper.statusNodes(false);
    }

    public ObjectNode findAll(){

        StandardCurrencyDAO standardCurrencyDAO = new StandardCurrencyDAO(tenant);
        List<StandardCurrency> standardCurrencyList = standardCurrencyDAO.findAll();
        if(standardCurrencyList.isEmpty()){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        for(StandardCurrency f : standardCurrencyList){

            arrayNode.addPOJO(createNode(f));
        }

        objectNode.putPOJO("pageItems",arrayNode);
        return objectNode ;

    }

    public ObjectNode createNode(StandardCurrency standardCurrency){

        ObjectNode objectNode= Helper.createObjectNode();
        objectNode.put("id",standardCurrency.getId());
        objectNode.put("name",standardCurrency.getName());
        objectNode.put("country",standardCurrency.getCountry());

        return objectNode ;
    }
}
