package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.LiveRatesDAO;
import com.wese.weseaddons.bereaudechange.pojo.LiveRates;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class LiveRatesAction {

    private String tenant ;
    private LiveRatesDAO liveRatesDAO ;

    public LiveRatesAction(String tenant) {
        this.tenant = tenant;
        this.liveRatesDAO = new LiveRatesDAO(tenant);
    }

    public ObjectNode findAll(){

        List<LiveRates> liveRatesList = liveRatesDAO.findAll();
        if(liveRatesList.isEmpty()){
            return Helper.statusNodes(false);
        }

        ArrayNode arrayNode = Helper.createArrayNode() ;

        for(LiveRates l : liveRatesList){

            ObjectNode objectNode = createNode(l);
            if(objectNode !=null){
                arrayNode.addPOJO(createNode(l));
            }
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.put("pageItems",arrayNode);
        return objectNode ;
    }

    public ObjectNode find(long id){

        LiveRates liveRates = liveRatesDAO.find(id);
        if(liveRates==null){
            return Helper.statusNodes(false);
        }

        ObjectNode objectNode = createNode(liveRates);
        return objectNode ;
    }
    
    public ObjectNode findWherePairMatch(long baseCurrencyId ,long quoteCurrencyId){

        LiveRates liveRates = liveRatesDAO.find(baseCurrencyId);
        if(liveRates==null){
            return Helper.statusNodes(false);
        }

        ObjectNode objectNode = createNode(liveRates);
        return objectNode ;
    }

    public ObjectNode createNode(LiveRates liveRates){

        TradingRates tradingRates = liveRates.getTradingRates();

        if(tradingRates.isActive()){

            TradingRatesAction tradingRatesAction = new TradingRatesAction();
            ObjectNode objectNode = tradingRatesAction.createNode(tradingRates);

            if(objectNode==null){
                return null ;
            }

            objectNode.put("id" ,liveRates.getId());
            objectNode.put("hasGained" ,liveRates.isHasGained());
            objectNode.put("percentageChange" ,"%"+liveRates.getPercentageChange());
            return objectNode ;
        }

        return null ;
    }
}
