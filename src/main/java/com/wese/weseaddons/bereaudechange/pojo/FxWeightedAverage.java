package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;

public class FxWeightedAverage {

    private long id ;
    private String currencySymbol ;
    private double weightedAverageBuyPrice ;
    private double totalCurrencyBought ;
    private double buyPrice;
    private double quantityCurrencyBought ;

    public FxWeightedAverage() {
    }

    public FxWeightedAverage(long id) {
        this.id = id;
    }
    
    public FxWeightedAverage(FxDeal fxDeal) {
    	
    	this.currencySymbol = fxDeal.getTradingRates().getCurrencyPair().getQuoteCurrencyMoneyAccount().getTradingCurrency().getSymbol();
    	this.buyPrice = TradingAlgorithm.getRateWithMarkUp(fxDeal);
    	this.quantityCurrencyBought = fxDeal.getBlotter().getNetQuoteAmount();
    	
    	if(fxDeal.getTradeType() == TRADE_TYPE.SELL) {
    		this.currencySymbol = fxDeal.getTradingRates().getCurrencyPair().getBaseCurrencyMoneyAccount().getTradingCurrency().getSymbol();
    	}
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public double getQuantityCurrencyBought() {
		return quantityCurrencyBought;
	}

	public void setQuantityCurrencyBought(double quantityCurrencyBought) {
		this.quantityCurrencyBought = quantityCurrencyBought;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public double getWeightedAverageBuyPrice() {
		return weightedAverageBuyPrice;
	}

	public void setWeightedAverageBuyPrice(double weightedAverageBuyPrice) {
		this.weightedAverageBuyPrice = weightedAverageBuyPrice;
	}
	
	public double getTotalCurrencyBought() {
		return totalCurrencyBought;
	}

	public void setTotalCurrencyBought(double totalCurrencyBought) {
		this.totalCurrencyBought = totalCurrencyBought;
	}

}

