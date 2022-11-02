package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.ChargesDAO;
import com.wese.weseaddons.bereaudechange.pojo.Charges;
import com.wese.weseaddons.helper.Helper;

public class ChargesAction {

    private String tenant ;
    private ChargesDAO chargesDAO ;

    public ChargesAction() {
    }

    public ChargesAction(String tenant){
        this.tenant = tenant ;
        chargesDAO = new ChargesDAO(tenant);
    }

    public ObjectNode createCharge(Charges charges){

        boolean status = chargesDAO.create(charges);

        if(status){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);

    }

    public ObjectNode findAll(){

        ArrayNode arrayNode = Helper.createArrayNode();

        for(Charges c : chargesDAO.findAll()){

            arrayNode.addPOJO(createNode(c));
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems",arrayNode);
        return  objectNode ;

    }


    public ObjectNode find(long id){

        ObjectNode objectNode = Helper.statusNodes(true);
        Charges charges =chargesDAO.find(id);
        objectNode = createNode(charges);

        return objectNode ;

    }

    public ObjectNode edit(Charges charges){

        return chargesDAO.edit(charges);

    }

    public ObjectNode createNode(Charges charges){

        ObjectNode objectNode= Helper.createObjectNode();
        objectNode.put("id",charges.getId());
        objectNode.put("name",charges.getName());
        objectNode.put("description",charges.getDescription());
        objectNode.put("tAccount",charges.gettAccount().getCode());
        objectNode.put("chargeCriteria",charges.getChargeCriteria().getCode());
        objectNode.put("amount" ,charges.getAmount());

        return objectNode ;
    }
}
