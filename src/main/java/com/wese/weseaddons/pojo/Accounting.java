package com.wese.weseaddons.pojo;

public class Accounting {

    private long disbursedTodayCb ;
    private double amountDisbursedToday ;
    private double portfolioAtRiskToday ;


    public Accounting(){}

    public Accounting(long disbursedTodayCb ,double amountDisbursedToday ,double portfolioAtRiskToday){
        this.disbursedTodayCb = disbursedTodayCb ;
        this.amountDisbursedToday = amountDisbursedToday ;
        this.portfolioAtRiskToday = portfolioAtRiskToday ;
    }

    public double getPortfolioAtRiskToday() {
        return portfolioAtRiskToday;
    }

    public double getAmountDisbursedToday() {
        return amountDisbursedToday;
    }

    public long getDisbursedTodayCb() {
        return disbursedTodayCb;
    }


}
