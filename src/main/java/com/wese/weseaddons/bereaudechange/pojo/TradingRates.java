package com.wese.weseaddons.bereaudechange.pojo;

public class TradingRates {

    private long id ;
    private double rate ;
    private double sellRate ;
    private long openTime ;
    private long closeTime ;
    private boolean active ;
    private boolean hasGained ;
    private double percentageChange ;
    private CurrencyPair currencyPair ;

    public TradingRates() {
    }

    public boolean isHasGained() {
        return hasGained;
    }

    public void setHasGained(boolean hasGained) {
        this.hasGained = hasGained;
    }

    public TradingRates(long id) {
        this.id = id;
    }

    public TradingRates(double rate ,double sellRate) {
        this.rate = rate;
        this.sellRate = sellRate ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(long closeTime) {
        this.closeTime = closeTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }
}
