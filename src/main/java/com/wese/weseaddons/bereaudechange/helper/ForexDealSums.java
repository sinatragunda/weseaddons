package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;

public class ForexDealSums {

    private double baseAmount  =0;
    private double baseCharges = 0;
    private double netBaseAmount = 0;
    private double quoteAmount = 0;
    private double quoteCharges = 0;
    private double netQuoteAmount = 0;
    private CurrencyPair currencyPair ;
    private ROUNDING_RULE roundingRule ;

    public ForexDealSums(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair ;
        this.roundingRule = currencyPair.getRoundingRule();
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = TradingAlgorithm.roundValue(roundingRule , baseAmount);
        //this.baseAmount = baseAmount ;
    }

    public double getNetBaseAmount() {
        return netBaseAmount;
    }

    public void setNetBaseAmount(double netBaseAmount) {

       this.netBaseAmount = TradingAlgorithm.roundValue(roundingRule, netBaseAmount);
        //this.netBaseAmount = netBaseAmount ;
    }

    public double getQuoteAmount() {
        return quoteAmount;
    }

    public void setQuoteAmount(double quoteAmount) {

        this.quoteAmount = TradingAlgorithm.roundValue(roundingRule , quoteAmount);
    }

    public double getNetQuoteAmount() {
        return netQuoteAmount;
    }

    public void setNetQuoteAmount(double netQuoteAmount) {

        this.netQuoteAmount = TradingAlgorithm.roundValue(roundingRule ,netQuoteAmount);
      //  this.netQuoteAmount = netQuoteAmount;

    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public ROUNDING_RULE getRoundingRule() {
        return roundingRule;
    }

    public void setRoundingRule(ROUNDING_RULE roundingRule) {
        this.roundingRule = roundingRule;
    }

    public double getBaseCharges() {
        return baseCharges;
    }

    public void setBaseCharges(double baseCharges) {

        this.baseCharges = TradingAlgorithm.roundValue(roundingRule, baseCharges);
        //this.baseCharges = baseCharges ;
    }

    public double getQuoteCharges() {
        return quoteCharges;
    }

    public void setQuoteCharges(double quoteCharges) {
        this.quoteCharges = TradingAlgorithm.roundValue(roundingRule, quoteCharges);
       // this.quoteCharges = quoteCharges ;
    }

}
