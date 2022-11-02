package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.ChargesDAO;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountChargesDAO;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountDAO;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.pojo.Charges;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountChargesRM;
import com.wese.weseaddons.helper.Helper;

import java.util.List;



public class MoneyAccountAction {

    private String tenant ;
    private MoneyAccountDAO moneyAccountDAO ;

    public MoneyAccountAction(String tenant) {

        this.tenant = tenant;
        moneyAccountDAO = new MoneyAccountDAO(tenant);
    }

    public ObjectNode create(MoneyAccount moneyAccount){

        return moneyAccountDAO.create(moneyAccount);
    }

    public ObjectNode findAll(){

        List<MoneyAccount> moneyAccountList = moneyAccountDAO.findAll();

        if(moneyAccountList.isEmpty()){
            return Helper.statusNodes(false);
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        for(MoneyAccount m : moneyAccountList){

            arrayNode.add(createNode(m));

        }

        objectNode.putPOJO("pageItems",arrayNode);
        return objectNode ;

    }

    public ObjectNode find(long id){

        MoneyAccount moneyAccount = moneyAccountDAO.find(id);

        if(moneyAccount==null){
            return Helper.statusNodes(false);
        }

        ObjectNode objectNode = createNode(moneyAccount);
        objectNode.put("status",true);
        return objectNode ;

    }

    public ObjectNode deactivate(long id ,boolean value){

        moneyAccountDAO.deactivate(id,value);
        return Helper.statusNodes(true);
    }

    public ObjectNode deactivateCharge(long moneyAccountId ,long chargeId ,boolean value){

        MoneyAccountChargesDAO moneyAccountChargesDAO = new MoneyAccountChargesDAO(tenant);
        boolean affectedRows = moneyAccountChargesDAO.deactivateCharge(moneyAccountId ,chargeId ,value);

        return Helper.statusNodes(affectedRows);

    }

    public ObjectNode update(MoneyAccount moneyAccount){

        return moneyAccountDAO.edit(moneyAccount);
    }


    public ObjectNode createNode(MoneyAccount moneyAccount){

        MoneyAccountChargesDAO moneyAccountChargesDAO = new MoneyAccountChargesDAO(tenant);
        List<MoneyAccountChargesRM> moneyAccountChargesList = moneyAccountChargesDAO.findWhere(moneyAccount.getId());


        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",moneyAccount.getId());
        objectNode.put("accountNumber",moneyAccount.getAccountNumber());
        objectNode.put("name",moneyAccount.getName());
        objectNode.put("balance" ,TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL,moneyAccount.getBalance()));
        objectNode.put("upperLimit",moneyAccount.getUpperLimit());
        objectNode.put("lowerLimit",moneyAccount.getLowerLimit());
        objectNode.put("symbol" ,moneyAccount.getTradingCurrency().getSymbol());
        objectNode.put("tradingCurrency",moneyAccount.getTradingCurrency().getName());
        objectNode.put("moneyAccountType" ,moneyAccount.getMoneyAccountType().getCode());
        objectNode.put("availableFloat",TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL,(moneyAccount.getBalance() - moneyAccount.getLowerLimit())));
        objectNode.put("profitLossPosition", moneyAccount.getProfitLossPosition());
        objectNode.put("buyPrice", moneyAccount.getBuyPrice());

        boolean hasCharges = moneyAccount.getChargesList().isEmpty() ? false : true ;

        objectNode.put("hasCharges",hasCharges);

        if(hasCharges){

            ArrayNode arrayNode = Helper.createArrayNode();
            
            for(Charges c : moneyAccount.getChargesList()){
                
                ChargesDAO chargesDAO = new ChargesDAO(tenant);
                Charges charges = chargesDAO.find(c.getId());

                boolean isActive = chargeActiveStatus(charges ,moneyAccountChargesList);
                ChargesAction chargesAction = new ChargesAction();

                ObjectNode objectNode1 = chargesAction.createNode(charges);
                objectNode1.put("active" ,isActive);

                String activeString = isActive == true ? "Deactivate":"Activate";

                objectNode1.put("activeString" ,activeString);
                arrayNode.addPOJO(objectNode1);
            }

            objectNode.putPOJO("charges",arrayNode);

        }

        return objectNode ;
    }

    public boolean chargeActiveStatus(Charges charges ,List<MoneyAccountChargesRM> moneyAccountChargesList){

        boolean status = false;

        for(MoneyAccountChargesRM m : moneyAccountChargesList){
            if(charges.getId()==m.getCharges().getId()){
                status = m.isActive();
                break ;
            }
        }

        return status ;
    }

}
