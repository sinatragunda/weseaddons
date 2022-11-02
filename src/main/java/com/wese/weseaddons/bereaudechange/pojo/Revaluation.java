package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.TradingRates;

public class Revaluation{

	private  long id ;
	private FxDeal fxDeal ;
	private TradingRates tradingRates ;
	private CurrencyPair currencyPair;
	private double positionRevaluation ;
	private double revaluationProfit ;
	private double revalRate ;
	private double fcy ;
	private long accruedDate ;

	public Revaluation() {
	}

	public Revaluation(long id) {
		this.id = id;
	}

	public Revaluation(FxDeal fxDeal , double revalRate , double positionRevaluation , double revaluationProfit , double fcy){

		this.fxDeal = fxDeal ;
		this.tradingRates = fxDeal.getTradingRates();
		this.currencyPair = tradingRates.getCurrencyPair();
		this.revalRate = revalRate ;
		this.positionRevaluation = positionRevaluation ;
		this.revaluationProfit = revaluationProfit ;
		this.fcy = fcy ;
	}

	public long getAccruedDate() {
		return accruedDate;
	}

	public void setAccruedDate(long accruedDate) {
		this.accruedDate = accruedDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FxDeal getFxDeal() {
		return fxDeal;
	}

	public void setFxDeal(FxDeal fxDeal) {
		this.fxDeal = fxDeal;
	}

	public TradingRates getTradingRates() {
		return tradingRates;
	}

	public void setTradingRates(TradingRates tradingRates) {
		this.tradingRates = tradingRates;
	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(CurrencyPair currencyPair) {
		this.currencyPair = currencyPair;
	}

	public double getPositionRevaluation() {
		return positionRevaluation;
	}

	public void setPositionRevaluation(double positionRevaluation) {
		this.positionRevaluation = positionRevaluation;
	}

	public double getRevaluationProfit() {
		return revaluationProfit;
	}

	public void setRevaluationProfit(double revaluationProfit) {
		this.revaluationProfit = revaluationProfit;
	}

	public double getRevalRate() {
		return revalRate;
	}

	public void setRevalRate(double revalRate) {
		this.revalRate = revalRate;
	}

	public double getFcy() {
		return fcy;
	}

	public void setFcy(double fcy) {
		this.fcy = fcy;
	}
}