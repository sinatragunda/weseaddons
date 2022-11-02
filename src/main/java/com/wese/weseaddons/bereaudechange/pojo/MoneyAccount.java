package com.wese.weseaddons.bereaudechange.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wese.weseaddons.bereaudechange.enumerations.MONEY_ACCOUNT_TYPE;
import java.util.List;

@JsonSerialize
public class MoneyAccount {

    private long id ;
    private String name ;
    private String accountNumber ;
    private double upperLimit ;
    private double lowerLimit ;
    private double balance ;
    private List<Charges> chargesList;
    private MONEY_ACCOUNT_TYPE moneyAccountType ;
    private TradingCurrency tradingCurrency ;
    private boolean active ;
    private double profitLossPosition = 500 ;
    private double buyPrice = 19.2 ;


    public MoneyAccount(){

    }


    public MoneyAccount(long id) {
        this.id = id;
    }

    public List<Charges> getChargesList() {
        return chargesList;
    }

    public void setChargesList(List<Charges> chargesList) {
        this.chargesList = chargesList;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public MONEY_ACCOUNT_TYPE getMoneyAccountType() {
        return moneyAccountType;
    }

    public void setMoneyAccountType(MONEY_ACCOUNT_TYPE moneyAccountType) {
        this.moneyAccountType = moneyAccountType;
    }

    public TradingCurrency getTradingCurrency() {
        return tradingCurrency;
    }

    public void setTradingCurrency(TradingCurrency tradingCurrency) {
        this.tradingCurrency = tradingCurrency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


	public double getProfitLossPosition() {
		
		return profitLossPosition;
	}


	public double getBuyPrice() {

		return buyPrice;
	}
	
	public void setProfitLossPosition(double profitLossPosition) {
		
		this.profitLossPosition = profitLossPosition;
	}


	public void setBuyPrice(double buyPrice) {

		this.buyPrice = buyPrice;
	}
	
	
}
