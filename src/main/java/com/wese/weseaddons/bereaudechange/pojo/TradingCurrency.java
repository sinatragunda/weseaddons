package com.wese.weseaddons.bereaudechange.pojo;

import java.util.List;

public class TradingCurrency {

    private long id ;
    private String name ;
    private String country ;
    private String region ;
    private String symbol ;
    private long addedDate ;
    private String description ;
    private FinancialInstrument financialInstrument ;
    private StandardCurrency standardCurrency;
    private List<PaymentMethods> paymentMethodList;



    public TradingCurrency() {
    }

    public TradingCurrency(long id) {
        this.id = id;
    }
    
    public List<PaymentMethods> getPaymentMethodsList() {
        return paymentMethodList;
    }
    
    public void setPaymentMethodsList(List<PaymentMethods> paymentMethodList) {
        this.paymentMethodList = paymentMethodList;
    }

    public TradingCurrency(String name, String country, String region, String symbol, long addedDate, FinancialInstrument financialInstrument) {
        this.name = name;
        this.country = country;
        this.region = region;
        this.symbol = symbol;
        this.addedDate = addedDate;
        this.financialInstrument = financialInstrument;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

    public FinancialInstrument getFinancialInstrument() {
        return financialInstrument;
    }

    public void setFinancialInstrument(FinancialInstrument financialInstrument) {
        this.financialInstrument = financialInstrument;
    }


    public StandardCurrency getStandardCurrency(){
        return this.standardCurrency ;
    }

    public void setStandardCurrency(StandardCurrency s){
        this.standardCurrency = s ;
    }
}
