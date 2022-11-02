package com.wese.weseaddons.bereaudechange.body;

import com.wese.weseaddons.bereaudechange.pojo.TradingRates;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.pojo.Client;

public class FxDealBody {


    private double rate ;
    private String tenant ;
    private double quoteAmount ;
    private long currencyPairId ;
    private TradingRates tradingRates ;
    private Client client ;
    private String date ;
    private AppUser appUser;
    private int fxDealType ;
    private int tradeType ;
    private int purpose ;
  
    public FxDealBody(){
        
    }

    public int getFxDealType() {
        return fxDealType;
    }

    public void setFxDealType(int fxDealType) {
        this.fxDealType = fxDealType;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setOfficer(AppUser appUser) {
        this.appUser = appUser;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }


    public double getQuoteAmount() {
        return quoteAmount;
    }

    public void setQuoteAmount(double quoteAmount) {
        this.quoteAmount = quoteAmount;
    }

    public long getCurrencyPairId() {
        return currencyPairId;
    }

    public void setCurrencyPairId(long currencyPairId) {
        this.currencyPairId = currencyPairId;
    }

    public TradingRates getTradingRates() {
        return tradingRates;
    }

    public void setTradingRates(TradingRates tradingRates) {
        this.tradingRates = tradingRates;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPurpose() {
        return purpose;
    }

    public void setPurpose(int purpose) {
        this.purpose = purpose;
    }

	public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }
}
