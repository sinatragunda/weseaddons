package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.FinancialInstrumentBody;
import com.wese.weseaddons.bereaudechange.dao.FinancialInstrumentsDAO;
import com.wese.weseaddons.bereaudechange.pojo.FinancialInstrument;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class FinancialInstrumentsAction {

    private String tenant ;
    private FinancialInstrumentsDAO financialInstrumentsDAO ;

    public FinancialInstrumentsAction(String s){
        this.tenant = s ;
        this.financialInstrumentsDAO = new FinancialInstrumentsDAO(s);
    }

    public ObjectNode create(FinancialInstrumentBody financialInstrumentBody){

        if(financialInstrumentsDAO.create(financialInstrumentBody)){
            return Helper.statusNodes(true);
        };

        return Helper.statusNodes(false);
    }

    public ObjectNode edit(FinancialInstrument financialInstrument){

        if(financialInstrumentsDAO.edit(financialInstrument)){
            return Helper.statusNodes(true);
        };

        return Helper.statusNodes(false);
    }

    public ObjectNode findAll(){

        List<FinancialInstrument> financialInstrumentList = financialInstrumentsDAO.findAll();
        if(financialInstrumentList.isEmpty()){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        for(FinancialInstrument f : financialInstrumentList){

            arrayNode.addPOJO(createNode(f));
        }

        objectNode.putPOJO("pageItems",arrayNode);
        return objectNode ;

    }


    public ObjectNode find(long id){

        FinancialInstrument financialInstrument = financialInstrumentsDAO.find(id);
        
        if(financialInstrument ==null){

            return Helper.statusNodes(false);
        }

        ObjectNode objectNode = createNode(financialInstrument);
        objectNode.put("status" ,true);

        return objectNode ;

    }

    public ObjectNode createNode(FinancialInstrument financialInstrument){

        ObjectNode objectNode= Helper.createObjectNode();
        objectNode.put("id",financialInstrument.getId());
        objectNode.put("name",financialInstrument.getName());
        objectNode.put("description",financialInstrument.getDescription());
        objectNode.put("instrumentType",financialInstrument.getFinancialInstrumentType().name());

        return objectNode ;
    }


}
