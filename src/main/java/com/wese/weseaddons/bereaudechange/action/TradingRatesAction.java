package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.TradingRatesDAO;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;


import java.util.List;

public class TradingRatesAction {

    private String tenant ;
    private TradingRatesDAO tradingRatesDAO ;

    public TradingRatesAction() {
    }

    public TradingRatesAction(String tenant) {

        this.tenant = tenant;
        this.tradingRatesDAO = new TradingRatesDAO(tenant);
    }

    public ObjectNode update(TradingRates tradingRates ,long liveRateId){

        tradingRatesDAO.update(tradingRates,liveRateId);
        return Helper.statusNodes(true);

    }

    public ObjectNode findAll(){

        List<TradingRates> tradingRatesList = tradingRatesDAO.findAll();
        if(tradingRatesList.isEmpty()){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        for(TradingRates f : tradingRatesList){

            ObjectNode objectNode1 = createNode(f);
            if(objectNode1!=null){
                arrayNode.addPOJO(createNode(f));
            }
        }

        objectNode.putPOJO("pageItems",arrayNode);

        return objectNode ;

    }

    public ObjectNode find(long id){

        TradingRates tradingRates= tradingRatesDAO.find(id);
        if(tradingRates==null){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        arrayNode.add(createNode(tradingRates));
        return objectNode ;

    }

     public ObjectNode findRatesWherePairMatch(long baseCurrencyId ,long quoteCurrencyId){

        ObjectNode objectNode = tradingRatesDAO.findRatesWherePairMatch(baseCurrencyId ,quoteCurrencyId);
        
        if(objectNode==null){
            return Helper.statusNodes(false);
        };

        return objectNode ;

    }

    public ObjectNode findTrend(long id){

        List<TradingRates> tradingRatesList= tradingRatesDAO.findTrend(id);

        if(tradingRatesList.isEmpty()){
            return Helper.statusNodes(false);
        };

        ObjectNode objectNode = Helper.statusNodes(true);
        ArrayNode arrayNode = Helper.createArrayNode();

        for(TradingRates f : tradingRatesList){

            ObjectNode objectNode1 = createNode(f);
            if(objectNode1 !=null){
                arrayNode.addPOJO(createNode(f));
            }
        }

        objectNode.put("currencyPair" ,tradingRatesList.get(0).getCurrencyPair().getTick());
        objectNode.putPOJO("pageItems",arrayNode);

        return objectNode ;

    }

    public ObjectNode volumesTrade(long id){

        return tradingRatesDAO.volumesTrade(id);

    }

    public ObjectNode createNode(TradingRates tradingRates){

        CurrencyPair currencyPair = tradingRates.getCurrencyPair();

        if(currencyPair !=null){

            String tick = currencyPair.getTick();

            if(tick !=null){

                ObjectNode objectNode= Helper.createObjectNode();
                objectNode.put("tradingRateId",tradingRates.getId());
                objectNode.put("tick",tradingRates.getCurrencyPair().getTick());
                double boughtRate = tradingRates.getRate();
                objectNode.put("rate", boughtRate);
                double tradedRate = boughtRate*(tradingRates.getCurrencyPair().getMarkUp()/100)+boughtRate;
                objectNode.put("tradedRate", tradedRate);;
                objectNode.put("sellRate" ,tradingRates.getSellRate());
                String openingTime = TimeHelper.timestampToStringWithTimeForSecond(tradingRates.getOpenTime());
                objectNode.put("openTime",openingTime);
                objectNode.put("active",tradingRates.isActive());
                objectNode.put("closeTime" ,"Live Rate");
                objectNode.put("state" ,0);
                objectNode.put("marketType",tradingRates.getCurrencyPair().getMarketType().ordinal());

                if(!tradingRates.isActive()){
                    String closeTime = TimeHelper.timestampToStringWithTimeForSecond(tradingRates.getCloseTime());
                    objectNode.put("closeTime",closeTime);
                }


                objectNode.put("edit",false);
                objectNode.put("currencyPairId",tradingRates.getCurrencyPair().getId());



                return objectNode ;

            }
        }

        return null ;

    }
}
