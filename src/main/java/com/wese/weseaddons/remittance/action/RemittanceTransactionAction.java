package com.wese.weseaddons.remittance.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.remittance.dao.RemittanceTransactionDAO;
import com.wese.weseaddons.remittance.enumerations.REMITTANCE_TRANSACTION_STATE;
import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;
import com.wese.weseaddons.helper.Helper;

public class RemittanceTransactionAction {

    private String tenant ;
    private RemittanceTransactionDAO remittanceTransactionDAO ;


    public RemittanceTransactionAction(String tenant){
        this.tenant = tenant ;
        remittanceTransactionDAO = new RemittanceTransactionDAO(tenant);
    }

    public ObjectNode findAll(){

        ArrayNode arrayNode = Helper.createArrayNode();
        for(RemittanceTransaction c : remittanceTransactionDAO.findAll()){

            arrayNode.addPOJO(createNode(c));
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems",arrayNode);
        return  objectNode ;

    }


    public ObjectNode find(long id){

        ObjectNode objectNode = Helper.statusNodes(true);
        RemittanceTransaction remittanceTransaction = remittanceTransactionDAO.find(id);
        objectNode = createNode(remittanceTransaction);

        return objectNode ;

    }

    public ObjectNode findWhere(String key){

        ObjectNode objectNode = Helper.statusNodes(true);
        RemittanceTransaction remittanceTransaction = remittanceTransactionDAO.findWhereKey(key);

        if(remittanceTransaction==null){
            return Helper.statusNodes(false).put("message" , Constants.transactionNotFound);
        }

        objectNode = createNode(remittanceTransaction);
        return objectNode ;

    }

    public ObjectNode updateTransactionState(long id ,int state){

        return remittanceTransactionDAO.updateTransactionState(id ,state);

    }


    public ObjectNode createNode(RemittanceTransaction remittanceTransaction) {

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id", remittanceTransaction.getId());
        objectNode.put("fundsKey", remittanceTransaction.getFundsKey());
        objectNode.put("state", remittanceTransaction.getRemittanceTransactionState().getCode());
        objectNode.put("amount", remittanceTransaction.getAmount());
        objectNode.put("commission", remittanceTransaction.getCommission());
        objectNode.put("senderName", remittanceTransaction.getRemitterSender().getName());
        objectNode.put("fxCashierSender", remittanceTransaction.getFxCashierSender().getTellerName());
        objectNode.put("senderPhoneNumber" ,remittanceTransaction.getRemitterSender().getPhoneNumber());
        objectNode.put("currency",remittanceTransaction.getStandardCurrency().getName());
        objectNode.put("recieverName", remittanceTransaction.getRemitterReciever().getName());
        objectNode.put("recieverPhoneNumber" ,remittanceTransaction.getRemitterReciever().getPhoneNumber());


        if (remittanceTransaction.getRemittanceTransactionState() == REMITTANCE_TRANSACTION_STATE.CLOSED) {
            objectNode.put("fxCashierReciever", remittanceTransaction.getFxCashierReciever().getTellerName());
            String date = TimeHelper.timestampToStringTime(remittanceTransaction.getDisbursedDate());
            objectNode.put("disbursed_date",date);

        }

        String date = TimeHelper.timestampToStringTime(remittanceTransaction.getTransactionDate());
        objectNode.put("date",date);

        return objectNode;

    }

}
