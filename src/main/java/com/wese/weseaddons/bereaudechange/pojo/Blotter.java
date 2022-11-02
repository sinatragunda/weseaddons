package com.wese.weseaddons.bereaudechange.pojo;

public class Blotter{

    private long id  ;
    private double baseAmount ;
    private double baseCharges ;
    private double netBaseAmount ;
    private double quoteAmount ;
    private double quoteCharges ;
    private double netQuoteAmount ;

    private double roundOfProfit ;
    private double roundOfLoss ;


    public Blotter() {
    }

    public Blotter(long id) {
        this.id = id;
    }

    public Blotter(double baseAmount, double baseCharges,double netBaseAmount , double quoteAmount, double quoteCharges,double netQuoteAmount) {
        this.baseAmount = baseAmount;
        this.baseCharges = baseCharges;
        this.quoteAmount = quoteAmount;
        this.quoteCharges = quoteCharges;
        this.netBaseAmount = netBaseAmount ;
        this.netQuoteAmount = netQuoteAmount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getQuoteAmount() {
        return quoteAmount;
    }

    public void setQuoteAmount(double quoteAmount) {
        this.quoteAmount = quoteAmount;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public double getBaseCharges() {
        return baseCharges;
    }

    public void setBaseCharges(double baseCharges) {
        this.baseCharges = baseCharges;
    }

    public double getQuoteCharges() {
        return quoteCharges;
    }

    public void setQuoteCharges(double quoteCharges) {
        this.quoteCharges = quoteCharges;
    }

    public void setRoundOfProfit(double roundOfProfit) {
        this.roundOfProfit = roundOfProfit;
    }

    public double getRoundOfProfit() {
        return roundOfProfit;
    }


    public double getRoundOfLoss() {
        return roundOfLoss;
    }

    public void setRoundOfLoss(double roundOfLoss) {
        this.roundOfLoss = roundOfLoss;
    }

    public double getNetQuoteAmount() {
        return netQuoteAmount;
    }

    public void setNetQuoteAmount(double netQuoteAmount) {
        this.netQuoteAmount = netQuoteAmount;
    }

    public double getNetBaseAmount() {
        return netBaseAmount;
    }

    public void setNetBaseAmount(double netBaseAmount) {
        this.netBaseAmount = netBaseAmount;
    }
}
