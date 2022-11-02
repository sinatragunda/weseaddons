package com.wese.weseaddons.bereaudechange.pojo;

public class LiveRates{

	private long id ;
	private boolean hasGained ;
	private double percentageChange ;
	private TradingRates tradingRates ;

	public LiveRates() {
	}

	public LiveRates(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isHasGained() {
		return hasGained;
	}

	public double getPercentageChange() {
		return percentageChange;
	}

	public void setPercentageChange(double percentageChange) {
		this.percentageChange = percentageChange;
	}

	public void setHasGained(boolean hasGained) {
		this.hasGained = hasGained;
	}

	public TradingRates getTradingRates() {
		return tradingRates;
	}

	public void setTradingRates(TradingRates tradingRates) {
		this.tradingRates = tradingRates;
	}
}

