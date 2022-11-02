package com.wese.weseaddons.bereaudechange.pojo;

public class FxEquivalent {

    private long id ;
    private FxDeal fxDeal ;
    private double localAmount ;
    private double equivalantAmount ;
    private TradingRates conversionTradingRates ;
    private TradingRates dealTradingRates ;

    public FxEquivalent(){}

    public FxEquivalent(FxDeal fxDeal, double localAmount, double equivalantAmount, TradingRates conversionTradingRates, TradingRates dealTradingRates) {

        this.fxDeal = fxDeal;
        this.localAmount = localAmount;
        this.equivalantAmount = equivalantAmount;
        this.conversionTradingRates = conversionTradingRates;
        this.dealTradingRates = dealTradingRates;
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

    public double getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(double localAmount) {
        this.localAmount = localAmount;
    }

    public double getEquivalantAmount() {
        return equivalantAmount;
    }

    public void setEquivalantAmount(double equivalantAmount) {
        this.equivalantAmount = equivalantAmount;
    }

    public TradingRates getConversionTradingRates() {
        return conversionTradingRates;
    }

    public void setConversionTradingRates(TradingRates conversionTradingRates) {
        this.conversionTradingRates = conversionTradingRates;
    }

    public TradingRates getDealTradingRates() {
        return dealTradingRates;
    }

    public void setDealTradingRates(TradingRates dealTradingRates) {
        this.dealTradingRates = dealTradingRates;
    }
}
