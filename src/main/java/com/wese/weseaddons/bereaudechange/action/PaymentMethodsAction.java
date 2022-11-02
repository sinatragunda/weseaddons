package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.PaymentMethodsDAO;
import com.wese.weseaddons.bereaudechange.pojo.PaymentMethods;
import com.wese.weseaddons.helper.Helper;

public class PaymentMethodsAction {

    private String tenant ;
    private PaymentMethodsDAO paymentMethodsDAO ;

    public PaymentMethodsAction() {
    }

    public PaymentMethodsAction(String tenant){
        this.tenant = tenant ;
        paymentMethodsDAO = new PaymentMethodsDAO(tenant);
    }

    public ObjectNode createPaymentMethod(PaymentMethods paymentMethods){

        boolean status = paymentMethodsDAO.create(paymentMethods);

        if(status){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);

    }

    public ObjectNode findAll(){

        ArrayNode arrayNode = Helper.createArrayNode();

        for(PaymentMethods c : paymentMethodsDAO.findAll()){

            arrayNode.addPOJO(createNode(c));
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems",arrayNode);
        return  objectNode ;

    }


    public ObjectNode find(long id){

        ObjectNode objectNode = Helper.statusNodes(true);
        PaymentMethods paymentMethod =paymentMethodsDAO.find(id);
        objectNode = createNode(paymentMethod);

        return objectNode ;

    }

    public ObjectNode edit(PaymentMethods paymentMethods){

        return paymentMethodsDAO.edit(paymentMethods);

    }

    public ObjectNode createNode(PaymentMethods paymentMethods){

        ObjectNode objectNode= Helper.createObjectNode();
        objectNode.put("id",paymentMethods.getId());
        objectNode.put("name",paymentMethods.getName());
        objectNode.put("description",paymentMethods.getDescription());
        objectNode.put("tAccount",paymentMethods.gettAccount().getCode());
        objectNode.put("paymentMethod" ,paymentMethods.getPaymentMethod().getCode());


        return objectNode ;
    }
}
