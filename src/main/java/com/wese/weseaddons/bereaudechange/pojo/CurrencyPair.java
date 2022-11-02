package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.MARKET_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class CurrencyPair {

    private long id ;
    private String tick ;
    private String inverseTick ;
    private MoneyAccount baseCurrencyMoneyAccount ;
    private MoneyAccount quoteCurrencyMoneyAccount ;
    private double rate ;
    private double sellRate ;
    private double markUp ;
    private ROUNDING_RULE roundingRule ;
    private MARKET_TYPE marketType ;
    private List<CurrencyPairVariation> currencyPairVariationList = new ArrayList<>();


    public CurrencyPair() {
    }

    public CurrencyPair(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public ROUNDING_RULE getRoundingRule() {
        return roundingRule;
    }

    public void setRoundingRule(ROUNDING_RULE roundingRule) {
        this.roundingRule = roundingRule;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }

    public MoneyAccount getBaseCurrencyMoneyAccount() {
        return baseCurrencyMoneyAccount;
    }

    public void setBaseCurrencyMoneyAccount(MoneyAccount baseCurrencyMoneyAccount) {
        this.baseCurrencyMoneyAccount = baseCurrencyMoneyAccount;
    }

    public MoneyAccount getQuoteCurrencyMoneyAccount() {
        return quoteCurrencyMoneyAccount;
    }

    public void setQuoteCurrencyMoneyAccount(MoneyAccount quoteCurrencyMoneyAccount) {
        this.quoteCurrencyMoneyAccount = quoteCurrencyMoneyAccount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public double getMarkUp() {
        return markUp;
    }

    public void setMarkUp(double markUp) {
        this.markUp = markUp;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public MARKET_TYPE getMarketType() {
        return marketType;
    }

    public void setMarketType(int marketType) {
        this.marketType = MARKET_TYPE.fromInt(marketType);
    }

    public List<CurrencyPairVariation> getCurrencyPairVariationList() {
        return currencyPairVariationList;
    }

    public void setCurrencyPairVariationList(List<CurrencyPairVariation> currencyPairVariationList) {
        this.currencyPairVariationList = currencyPairVariationList;
    }

    public String getInverseTick(){

        return StringHelper.getInverseTick(tick);
    }
}
