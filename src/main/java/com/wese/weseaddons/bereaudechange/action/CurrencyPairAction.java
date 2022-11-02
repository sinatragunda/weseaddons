package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.CurrencyPairDAO;
import com.wese.weseaddons.bereaudechange.dao.LiveRatesDAO;
import com.wese.weseaddons.bereaudechange.dao.TradingCurrencyDAO;
import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.helper.StringHelper;
import com.wese.weseaddons.bereaudechange.helper.TradingRatesAnalyzer;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPairVariation;
import com.wese.weseaddons.bereaudechange.pojo.LiveRates;
import com.wese.weseaddons.bereaudechange.pojo.PaymentMethods;
import com.wese.weseaddons.bereaudechange.pojo.TradingCurrency;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.helper.Helper;

import java.util.List;


public class CurrencyPairAction {

    private String tenant ;
    private CurrencyPairDAO currencyPairsDAO ;

    public CurrencyPairAction(String tenant){

        this.tenant = tenant ;
        this.currencyPairsDAO = new CurrencyPairDAO(tenant);
        
    }

    public ObjectNode create(CurrencyPair currencyPair){

        return currencyPairsDAO.create(currencyPair);

    }

    public ObjectNode update(CurrencyPair currencyPair){
        return currencyPairsDAO.update(currencyPair);
    }

    public ObjectNode findAll(){

        List<CurrencyPair> currencyPairList = currencyPairsDAO.findAll();
        if(currencyPairList.isEmpty()){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        for(CurrencyPair f : currencyPairList){

            arrayNode.addPOJO(createNode(f));
        }

        objectNode.putPOJO("pageItems",arrayNode);
    
        return objectNode ;

    }

    public ObjectNode delete(long id){

        currencyPairsDAO.delete(id);
        return Helper.statusNodes(true);

    }

    public ObjectNode find(long id){

        CurrencyPair currencyPair = currencyPairsDAO.find(id);

        if(currencyPair==null){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = createNode(currencyPair);
        objectNode.put("status",true);

        return objectNode ;

    }

    public ObjectNode rateHistory(long currencyPairId){

        TradingRatesAnalyzer tradingRatesAnalyzer = new TradingRatesAnalyzer(tenant);
        return tradingRatesAnalyzer.analyse(currencyPairId);

    }

    public ObjectNode createNode(CurrencyPair currencyPair){

        if(currencyPair.getBaseCurrencyMoneyAccount().isActive() && currencyPair.getQuoteCurrencyMoneyAccount().isActive()){


            TradingRatesDAO tradingRatesDAO = new TradingRatesDAO(tenant);
            List<TradingRates> tradingRatesList = tradingRatesDAO.findWhere(currencyPair.getId() ,"currency_pair_id");

            ObjectNode objectNode= Helper.createObjectNode();
            objectNode.put("id",currencyPair.getId());
            objectNode.put("tick",currencyPair.getTick());
            objectNode.put("inverseTick" , StringHelper.getInverseTick(currencyPair.getTick()));
            objectNode.put("baseCurrency",currencyPair.getBaseCurrencyMoneyAccount().getName());
            objectNode.put("quoteCurrency",currencyPair.getQuoteCurrencyMoneyAccount().getName());
            objectNode.put("roundingRule",currencyPair.getRoundingRule().getCode());
            objectNode.put("markUp" ,currencyPair.getMarkUp());
            objectNode.put("marketTypeCode" ,currencyPair.getMarketType().getCode());
            objectNode.put("marketType",currencyPair.getMarketType().ordinal());


            if(!tradingRatesList.isEmpty()){
                
                int size = tradingRatesList.size();
                TradingRates tradingRates = tradingRatesList.get(size -1);
                objectNode.put("rate" ,tradingRates.getRate());
                objectNode.put("sellRate" ,tradingRates.getSellRate());

            }

            TradingCurrency quoteTradingCurrency = currencyPair.getQuoteCurrencyMoneyAccount().getTradingCurrency();

            List<PaymentMethods> quoteCurrencyPaymentMethodsList = quoteTradingCurrency.getPaymentMethodsList();

            if(!quoteCurrencyPaymentMethodsList.isEmpty()) {

            	PaymentMethodsAction paymentMethodsAction = new PaymentMethodsAction(tenant);
                ArrayNode arrayNode = Helper.createArrayNode();
                for(PaymentMethods paymentMethods : quoteCurrencyPaymentMethodsList) {

                	ObjectNode ob = paymentMethodsAction.createNode(paymentMethods);
                	arrayNode.add(ob);
                }

                objectNode.putPOJO("paymentMethods" ,arrayNode);
            }



            List<CurrencyPairVariation> currencyPairVariationList = currencyPair.getCurrencyPairVariationList();

            objectNode.put("hasCurrencyVariations",false);

            if(!currencyPairVariationList.isEmpty()){
                objectNode.put("hasCurrencyVariations",true);
            }

            ArrayNode arrayNode = Helper.createArrayNode();

            for(CurrencyPairVariation currencyPairVariation : currencyPairVariationList){

                ObjectNode objectNode1 = Helper.createObjectNode();
                objectNode1.put("id",currencyPairVariation.getId());
                objectNode1.put("name",currencyPairVariation.getExchangeVariation().getCode());
                objectNode1.put("value",currencyPairVariation.getValue());

                arrayNode.addPOJO(objectNode1);
            }

            objectNode.putPOJO("currencyVariations",arrayNode);
            return objectNode ;
        }

        return Helper.statusNodes(false);
    }
}

