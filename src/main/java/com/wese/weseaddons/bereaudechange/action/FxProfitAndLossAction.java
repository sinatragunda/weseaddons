package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.AccrualDAO;
import com.wese.weseaddons.bereaudechange.dao.FxDealDAO;

import com.wese.weseaddons.bereaudechange.dao.MoneyAccountTransactionsDAO;
import com.wese.weseaddons.bereaudechange.enumerations.ACCRUAL_CRITERIA;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.helper.AccrualHelper;
import com.wese.weseaddons.bereaudechange.helper.RevaluationHelper;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountTransactions;
import com.wese.weseaddons.bereaudechange.pojo.Revaluation;
import com.wese.weseaddons.bereaudechange.profitandloss.FxDifferentialRatesMethod;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class FxProfitAndLossAction{

	private String tenant ;
	private FxDealDAO fxDealDAO ;
	private AccrualDAO accrualDAO ;
	private AccrualHelper accrualHelper;

	private Predicate<FxDeal> fxDealPredicate = (e)->{
		return !e.isAccrued();
	};

	public FxProfitAndLossAction(String s){
		this.tenant =s ;
		this.fxDealDAO = new FxDealDAO(tenant);
		this.accrualDAO = new AccrualDAO(tenant);
		this.accrualHelper = new AccrualHelper(tenant);

	}

	public ObjectNode accrue(String date){

		long tillDate = TimeHelper.angularStringToDate(date);
		List<FxDeal> fxDealList = fxDealDAO.findWhereDateBefore(tillDate);
		AccrualHelper accrualHelper = new AccrualHelper(tenant);
		List<FxDeal> toAccrueList = fxDealList.stream().filter(fxDealPredicate).collect(Collectors.toList());
		accrualHelper.accrue(toAccrueList);
		return Helper.statusNodes(true).put("message", "You have run profit evaluation succesfully");

	}

	public ObjectNode findAll(){

		List<FxDeal> fxDealList = fxDealDAO.findAll();

		if(fxDealList.isEmpty()){
			return Helper.statusNodes(false);
		}

		ArrayNode arrayNode = Helper.createArrayNode();

		for(FxDeal fxDeal : fxDealList){

			RevaluationHelper revaluationHelper = new RevaluationHelper(fxDeal ,tenant);
			Revaluation revaluation = revaluationHelper.profitAndLoss();
			
			if(revaluation==null) {
			    continue ;
			}
			
			ObjectNode objectNode = createNode(revaluation);
			if(objectNode==null) {
			    continue ;
			}

			arrayNode.add(createNode(revaluation));
		}

		ObjectNode objectNode = Helper.createObjectNode();
		objectNode.putPOJO("pageItems" ,arrayNode);
		objectNode.put("status",true);

		return objectNode ;

	}

	public ObjectNode find(long id){


		FxDeal fxDeal = fxDealDAO.find(id);

		if(fxDeal ==null){
			return Helper.statusNodes(false);
		}

		RevaluationHelper revaluationHelper = new RevaluationHelper(fxDeal ,tenant);
		Revaluation revaluation = revaluationHelper.profitAndLoss();

		ObjectNode objectNode = createNode(revaluation);
		objectNode.put("status",true);

		return objectNode ;

	}

	public ObjectNode fxDifferentialRatesMethod(long fxDealId){

		FxDeal fxDeal = fxDealDAO.find(fxDealId);

		if(fxDeal ==null){
			return Helper.statusNodes(false);
		}

		FxDifferentialRatesMethod fxDifferentialRatesMethod = new FxDifferentialRatesMethod(fxDeal, tenant);
		return fxDifferentialRatesMethod.getProfitNode();

	}

	public ObjectNode findWhereMoneyAccount(long id){

		List<Revaluation> revaluationList = new ArrayList<>();
		MoneyAccountTransactionsDAO moneyAccountTransactionsDAO = new MoneyAccountTransactionsDAO(tenant);
		List<MoneyAccountTransactions> moneyAccountTransactionsList = moneyAccountTransactionsDAO.findWhere(id ,"money_account_id");

		if(moneyAccountTransactionsList.isEmpty()){
			return Helper.statusNodes(false);
		}

		for(MoneyAccountTransactions m : moneyAccountTransactionsList){

			long fxDealId = m.getFxDeal().getId() ;
			Revaluation revaluation = accrualDAO.find(fxDealId ,"fx_deal_id");
			
			if(revaluation!=null) {
			    revaluationList.add(revaluation);
			}	
		}

		if(revaluationList.isEmpty()){
			return Helper.statusNodes(false);
		}


		ArrayNode arrayNode = Helper.createArrayNode();

        double profitAmount = revaluationList.stream().map(Revaluation::getFcy).mapToDouble(Double::doubleValue).sum() ;
        
   
        for(Revaluation revaluation : revaluationList){
                
            ObjectNode objectNode1 = createNode(revaluation);
            
            if(objectNode1==null) {
                continue ;
            }
            
            arrayNode.add(objectNode1);

	}    
        
		ObjectNode objectNode = Helper.createObjectNode();
		objectNode.put("status",true);
		objectNode.putPOJO("pageItems" ,arrayNode);
		objectNode.put("profit" ,profitAmount);
		objectNode.put("baseCurrency",moneyAccountTransactionsList.get(0).getMoneyAccount().getTradingCurrency().getStandardCurrency().getName());
		objectNode.put("accountName" ,moneyAccountTransactionsList.get(0).getMoneyAccount().getName());
	
		return objectNode ;

	}

	public ObjectNode findWhereDates(long id ,long startDay ,long endDay ,ACCRUAL_CRITERIA accrualCriteria){
	    
	        if(startDay > endDay) {
	            return Helper.statusNodes(false).put("message", Constants.invalidDate);
	        }

		List<Revaluation> revaluationList = new ArrayList<>();
		MoneyAccountTransactionsDAO moneyAccountTransactionsDAO = new MoneyAccountTransactionsDAO(tenant);
		List<MoneyAccountTransactions> moneyAccountTransactionsList = moneyAccountTransactionsDAO.findWhere(id ,"money_account_id");

		if(moneyAccountTransactionsList.isEmpty()){
			return Helper.statusNodes(false).put("message", Constants.noAvailableData);
		}


		for(MoneyAccountTransactions m : moneyAccountTransactionsList){

			long fxDealId = m.getFxDeal().getId() ;

			switch (accrualCriteria){
				case FX_DEAL_DATE :
					FxDeal fxDeal = m.getFxDeal();
					if(fxDeal.getDate() >= startDay && fxDeal.getDate() <= endDay){

						RevaluationHelper revaluationHelper = new RevaluationHelper(fxDeal ,tenant);
						Revaluation revaluation = revaluationHelper.profitAndLoss();
						revaluationList.add(revaluation);
					}

					break;

				case REVALUATION_DATE:
				    
					Revaluation revaluation = accrualDAO.find(fxDealId ,"fx_deal_id");
					if(revaluation !=null){

						if(revaluation.getAccruedDate() >= startDay && revaluation.getAccruedDate() <= endDay){

							revaluationList.add(revaluation);

						}
					}

					break;
			}

		}

		if(revaluationList.isEmpty()){
			return Helper.statusNodes(false).put("message", Constants.noAvailableData);
		}
		
		
		try {
		          ArrayNode arrayNode = Helper.createArrayNode();

		                double profitAmount = revaluationList.stream().map(Revaluation::getFcy).mapToDouble(Double::doubleValue).sum() ;

		                for(Revaluation revaluation : revaluationList){

		                        ObjectNode objectNode1 = createNode(revaluation);

		                        if(objectNode1==null) {
		                                continue ;
		                        }

		                        arrayNode.add(objectNode1);

		                }

		                ObjectNode objectNode = Helper.createObjectNode();
		                objectNode.put("status",true);
		                objectNode.putPOJO("pageItems" ,arrayNode);
		                objectNode.put("profit" ,profitAmount);
		                objectNode.put("baseCurrency",moneyAccountTransactionsList.get(0).getMoneyAccount().getTradingCurrency().getStandardCurrency().getName());
		                objectNode.put("accountName" ,moneyAccountTransactionsList.get(0).getMoneyAccount().getName());

		                return objectNode ;
		    
		}
		
		catch (NullPointerException e) {
                    // TODO: handle exception
		    e.printStackTrace();
                }


		return null ;

	}

	public ObjectNode createNode(Revaluation revaluation){

		ObjectNode objectNode = null;
		
		try {

			objectNode =Helper.createObjectNode();
			objectNode.put("fxDealId" ,revaluation.getFxDeal().getId());

			String date = TimeHelper.timestampToStringWithTimeForSecond(revaluation.getFxDeal().getDate());
			String accruedDate = null ;

			objectNode.put("accruedDate",date);
			if(revaluation.getAccruedDate() != 0){

				accruedDate = TimeHelper.timestampToStringWithTimeForSecond(revaluation.getAccruedDate());
				objectNode.put("accruedDate",accruedDate);
			}


			objectNode.put("fxDealId" ,revaluation.getFxDeal().getId());
			objectNode.put("fxDealDate",date);
			objectNode.put("fcy",revaluation.getFcy());
			objectNode.put("revaluationProfit" ,revaluation.getRevaluationProfit());
			objectNode.put("positionRevaluation",revaluation.getPositionRevaluation());
			objectNode.put("revalRate",TradingAlgorithm.roundRate(revaluation.getRevalRate()));
			objectNode.put("rate", TradingAlgorithm.roundRate(revaluation.getTradingRates().getRate()));
			objectNode.put("quoteAmount", TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL, revaluation.getFxDeal().getBlotter().getQuoteAmount()));
			objectNode.put("baseAmount",TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL, revaluation.getFxDeal().getBlotter().getBaseAmount()));
			objectNode.put("currencyDeal",revaluation.getCurrencyPair().getTick());
			objectNode.put("baseCurrency",revaluation.getCurrencyPair().getBaseCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency().getName());
			objectNode.put("quoteCurrency",revaluation.getCurrencyPair().getQuoteCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency().getName());

		}

		catch(NullPointerException e){
			return null  ;
		}
		
		return objectNode ;

	}

}
