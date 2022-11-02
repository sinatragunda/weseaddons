package com.wese.weseaddons.bereaudechange.pojo;

public class TradingCurrencyPaymentMethodsRM {

    private long id ;
    private TradingCurrency tradingCurrency ;
    private PaymentMethods paymentmethod ;
    private boolean active ;


    public TradingCurrencyPaymentMethodsRM() {
    }

    public TradingCurrencyPaymentMethodsRM(TradingCurrency tradingCurrency, PaymentMethods paymentmethod) {
        this.tradingCurrency = tradingCurrency;
        this.paymentmethod = paymentmethod;
    }

    public TradingCurrencyPaymentMethodsRM(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TradingCurrency getTradingCurrency() {
        return tradingCurrency;
    }

    public void setTradingCurrency(TradingCurrency tradingCurrency) {
        this.tradingCurrency = tradingCurrency;
    }

    public PaymentMethods getPaymentMethod() {
        return paymentmethod;
    }

    public void setPaymentMethod(PaymentMethods paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
