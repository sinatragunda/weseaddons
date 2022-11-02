package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.EXCHANGE_VARIATION;

public class CurrencyPairVariation {

    private long id ;
    private double value ;
    private CurrencyPair currencyPair ;
    private EXCHANGE_VARIATION exchangeVariation ;

    public CurrencyPairVariation() {
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public EXCHANGE_VARIATION getExchangeVariation() {
        return exchangeVariation;
    }

    public void setExchangeVariation(int exchangeVariation) {
        this.exchangeVariation = EXCHANGE_VARIATION.fromInt(exchangeVariation);
    }

    @Override
    public String toString(){
        return String.format("%d %.2f %s",id ,value ,exchangeVariation.getCode());
    }
}
