package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxDealDAO;
import com.wese.weseaddons.bereaudechange.dao.FxWeightedAverageDAO;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxWeightedAverage;
import com.wese.weseaddons.helper.Helper;

public class FxWeightedAverageAction {

    private String tenant ;
    private FxWeightedAverageDAO fxWeightedAverageDAO ;
    private FxDealDAO fxDealDAO ;

    public FxWeightedAverageAction() {
    }

    public FxWeightedAverageAction(String tenant){
        this.tenant = tenant ;
        this.fxWeightedAverageDAO = new FxWeightedAverageDAO(tenant);
        this.fxDealDAO = new FxDealDAO(tenant);
    }

    public ObjectNode findAll(){

        ArrayNode arrayNode = Helper.createArrayNode();

        for(FxWeightedAverage c : fxWeightedAverageDAO.findAll()){

            arrayNode.addPOJO(createNode(c));
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems",arrayNode);
        return  objectNode ;

    }
    
    public ObjectNode update(FxWeightedAverage fxWeightedAverage){

    	FxWeightedAverage currentRecord = fxWeightedAverageDAO.findByCurrencySymbol(fxWeightedAverage.getCurrencySymbol());
    	FxWeightedAverage newRecord = new FxWeightedAverage();
    	if(currentRecord == null) {
    		newRecord.setCurrencySymbol(fxWeightedAverage.getCurrencySymbol());
    		newRecord.setTotalCurrencyBought(fxWeightedAverage.getQuantityCurrencyBought());
    		newRecord.setWeightedAverageBuyPrice(fxWeightedAverage.getBuyPrice());
    		
    		fxWeightedAverageDAO.create(newRecord);
    	}
    	newRecord = new FxWeightedAverage();

    	double newQuantityCurrencyBought = fxWeightedAverage.getQuantityCurrencyBought();
    	double newBuyPrice = fxWeightedAverage.getBuyPrice();
    	
    	double oldSum = currentRecord.getTotalCurrencyBought();
    	double oldWeightedAverageBuyPrice = currentRecord.getWeightedAverageBuyPrice();
    	
    	double newWeightedBuyPrice = ((1/(oldSum+newQuantityCurrencyBought)) * ((oldWeightedAverageBuyPrice*oldSum) + (newBuyPrice * newQuantityCurrencyBought)));
    	
		newRecord.setCurrencySymbol(currentRecord.getCurrencySymbol());
    	newRecord.setWeightedAverageBuyPrice(newWeightedBuyPrice);
    	newRecord.setTotalCurrencyBought(oldSum+newQuantityCurrencyBought);

    	return fxWeightedAverageDAO.update(newRecord);

    }
    
    public ObjectNode createMyPojo(FxWeightedAverage fxWeightedAverage){

        boolean status = fxWeightedAverageDAO.create(fxWeightedAverage);

        if(status){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);

    }


    public ObjectNode find(long id){

        ObjectNode objectNode = Helper.statusNodes(true);
        FxWeightedAverage fxWeightedAverage = fxWeightedAverageDAO.find(id);
        objectNode = createNode(fxWeightedAverage);

        return objectNode ;

    }
    
    public ObjectNode findByCurrencySymbol(String currencySymbol){

        ObjectNode objectNode = Helper.statusNodes(true);
        FxWeightedAverage fxWeightedAverage = fxWeightedAverageDAO.findByCurrencySymbol(currencySymbol);
        objectNode = createNode(fxWeightedAverage);

        return objectNode ;

    }
    
    public ObjectNode profit(long fxDealId){
    	
        FxDeal fxDeal = null  ;
        
        try {
        	fxDeal = fxDealDAO.find(fxDealId);
        	}
        
        catch(Exception i) {
        	i.printStackTrace();
        	}
        
        String currencySymbol = fxDeal.getTradingRates().getCurrencyPair().getBaseCurrencyMoneyAccount().getTradingCurrency().getSymbol();
        FxWeightedAverage fxWeightedAverage = fxWeightedAverageDAO.findByCurrencySymbol(currencySymbol);
        
        double sellRate = TradingAlgorithm.getRateWithMarkUp(fxDeal);
        double buyRate = fxWeightedAverage.getWeightedAverageBuyPrice();
        double rateDifference = ((sellRate - buyRate)/buyRate);
        double nominalProfitLoss = rateDifference*fxDeal.getBlotter().getNetBaseAmount();
        
        ObjectNode objectNode= Helper.createObjectNode();
        objectNode.put("currencySymbol",fxWeightedAverage.getCurrencySymbol());
        objectNode.put("totalCurrencyBought",fxWeightedAverage.getTotalCurrencyBought());
        objectNode.put("sellRate",sellRate);
        objectNode.put("percentageProfitLoss",rateDifference*100);    
        objectNode.put("weightedAverageBuyPrice" ,buyRate);
        objectNode.put("nominalProfitLoss",nominalProfitLoss);
        if(rateDifference > 0) {objectNode.put("profitPosition","Profit");}
        else if(rateDifference < 0) {objectNode.put("profitPosition","Loss");}
        else if(rateDifference == 0) {objectNode.put("profitPosition","Equilibrium");}
        else {objectNode.put("profitPosition","undefined");}
        
        return objectNode ;

    }
    

    public ObjectNode createNode(FxWeightedAverage fxWeightedAverage){

        ObjectNode objectNode= Helper.createObjectNode();
        objectNode.put("id", fxWeightedAverage.getId());
        objectNode.put("currencySymbol",fxWeightedAverage.getCurrencySymbol());
        objectNode.put("totalCurrencyBought",fxWeightedAverage.getTotalCurrencyBought());
        objectNode.put("weightedAverageBuyPrice" ,fxWeightedAverage.getWeightedAverageBuyPrice());

        return objectNode ;
    }
}
