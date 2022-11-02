package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountTransactionsDAO;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.helper.TransactionHelper;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountTransactions;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;

import java.util.List;


public class MoneyAccountTransactionsAction {

    private String tenant ;
    private MoneyAccountTransactionsDAO moneyAccountTransactionsDAO ;

    public MoneyAccountTransactionsAction(String tenant) {

        this.tenant = tenant;
        this.moneyAccountTransactionsDAO = new MoneyAccountTransactionsDAO(tenant);
    }

    public ObjectNode find(long id,String sql){

        List<MoneyAccountTransactions> moneyAccountTransactionsList = moneyAccountTransactionsDAO.findWhere(id ,sql);

        if(moneyAccountTransactionsList.isEmpty()){
            return Helper.statusNodes(false);
        }

        ArrayNode arrayNode = Helper.createArrayNode();
        ObjectNode objectNode = Helper.createObjectNode();

        TransactionHelper transactionHelper = new TransactionHelper();

        MoneyAccountTransactions moneyAccountTransactions= moneyAccountTransactionsList.get(0);

        MoneyAccount moneyAccount = moneyAccountTransactions.getMoneyAccount();


        double uCharges = transactionHelper.unrealizedChargesProfit(moneyAccountTransactionsList);
        double netFloat = transactionHelper.netFloat(moneyAccount);
        double availableFloat = transactionHelper.availableFloat(moneyAccount);


        for(MoneyAccountTransactions m : moneyAccountTransactionsList){

            ObjectNode objectNode1 = createNode(m);
            arrayNode.addPOJO(objectNode1);

        }

        objectNode.putPOJO("pageItems",arrayNode);

        objectNode.put("availableFloat" ,availableFloat);
        objectNode.put("netBalance" ,netFloat);
        objectNode.put("unrealizedCharges" ,uCharges);
        objectNode.put("moneyAccountName",moneyAccountTransactions.getMoneyAccount().getName());


        return objectNode ;

    }

    public ObjectNode createNode(MoneyAccountTransactions m){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id" ,m.getId());
        objectNode.put("charges",m.getCharges());
        objectNode.put("amount",m.getAmount());
        objectNode.put("dealId" ,m.getFxDeal().getId());
//
//        ROUNDING_RULE r = m.getFxDeal().getTradingRates().getCurrencyPair().getRoundingRule();

        double temp = TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL ,m.getRunningBalance());

        objectNode.put("runningBalance",temp);

        String date = TimeHelper.timestampToStringWithTimeForSecond(m.getFxDeal().getDate());
        objectNode.put("date" ,date);

        objectNode.put("tAccount" ,m.gettAccount().getCode());

        return objectNode ;
    }
}
