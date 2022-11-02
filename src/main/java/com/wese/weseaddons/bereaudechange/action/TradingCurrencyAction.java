package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.TradingCurrencyDAO;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrency;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;

import java.util.List;

public class TradingCurrencyAction {

    private String tenant ;
    private TradingCurrencyDAO tradingCurrencyDAO ;

    public TradingCurrencyAction(String t){

        this.tenant = t ;
        this.tradingCurrencyDAO = new TradingCurrencyDAO(tenant);
    }

    public ObjectNode create(TradingCurrency tradingCurrency){
        
        if(tradingCurrency==null) {
            return null ;
        }

        return tradingCurrencyDAO.create(tradingCurrency);

    }

    public ObjectNode findAll(){

        List<TradingCurrency> tradingCurrencyList = tradingCurrencyDAO.findAll();
        if(tradingCurrencyList.isEmpty()){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        for(TradingCurrency f : tradingCurrencyList){

            arrayNode.addPOJO(createNode(f));
        }

        objectNode.putPOJO("pageItems",arrayNode);
        return objectNode ;

    }


    public ObjectNode find(long id){


        TradingCurrency tradingCurrency = tradingCurrencyDAO.find(id);

        if(tradingCurrency==null){

            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        return createNode(tradingCurrency);

    }

    public ObjectNode edit(TradingCurrency tradingCurrency){

        boolean status =tradingCurrencyDAO.edit(tradingCurrency);
        return Helper.statusNodes(status);

    }

    public ObjectNode createNode(TradingCurrency tradingCurrency){

        ObjectNode objectNode= Helper.createObjectNode();
        objectNode.put("id",tradingCurrency.getId());
        objectNode.put("name",tradingCurrency.getName());
        objectNode.put("symbol",tradingCurrency.getSymbol());
        objectNode.put("description" ,tradingCurrency.getDescription());
        objectNode.put("country",tradingCurrency.getStandardCurrency().getCountry());
        objectNode.put("addedDate", TimeHelper.timestampToString(tradingCurrency.getAddedDate()));
        objectNode.put("financialInstrumentType" ,tradingCurrency.getFinancialInstrument().getName());
        objectNode.put("standardCurrency" ,tradingCurrency.getStandardCurrency().getName());
        objectNode.put("standardCurrencyId" ,tradingCurrency.getStandardCurrency().getId());

        return objectNode ;
    }
}
