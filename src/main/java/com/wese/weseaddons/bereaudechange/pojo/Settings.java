package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.AUTO_REVAL_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.PROFIT_CALC_METHOD;

public class Settings{

	private long id ;
	private int openingTime ;
	private int closingTime ;
	private double dailyLimit ;
	private String companyName ;
	private String companyAddress ;
	private String companyCity ;
	private StandardCurrency localCurrency ;
	private CurrencyPair currencyPair ;
	private StandardCurrency accountingCurrency ;
	private String district ;
	private int autoRevalInt ;
	private int profitCalculationMethodInt;
	private AUTO_REVAL_TYPE autoRevalType ;
	private PROFIT_CALC_METHOD profitCalculationMethod ;

	public Settings() {
	}

	public Settings(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAutoRevalInt() {
		return autoRevalInt;
	}

	public void setAutoRevalInt(int autoRevalInt) {
		this.autoRevalInt = autoRevalInt;
	}

	public int getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(int openingTime) {
		this.openingTime = openingTime;
	}

	public int getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(int closingTime) {
		this.closingTime = closingTime;
	}

	public double getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}


	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public AUTO_REVAL_TYPE getAutoRevalType() {

		return AUTO_REVAL_TYPE.fromInt(autoRevalInt);
	}

	public void setAutoRevalType(int autoRevalType) {
		this.autoRevalType = AUTO_REVAL_TYPE.fromInt(autoRevalType);
	}

	public StandardCurrency getAccountingCurrency() {
		return accountingCurrency;
	}

	public void setAccountingCurrency(StandardCurrency accountingCurrency) {
		this.accountingCurrency = accountingCurrency;
	}

	public CurrencyPair getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(CurrencyPair currencyPair) {
		this.currencyPair = currencyPair;
	}


	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyCity() {
		return companyCity;
	}

	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}

	public StandardCurrency getLocalCurrency() {
		return localCurrency;
	}

	public void setLocalCurrency(StandardCurrency localCurrency) {
		this.localCurrency = localCurrency;
	}
	
	
	public void setProfitCalculationMethod(int arg) {
		this.profitCalculationMethod = PROFIT_CALC_METHOD.fromInt(arg);
	}
	
	public PROFIT_CALC_METHOD getProfitCalculationMethod() {
		return profitCalculationMethod ;
	}


	@Override
	public String toString() {
		return "Settings{" +
				"id=" + id +
				", openingTime=" + openingTime +
				", closingTime=" + closingTime +
				", dailyLimit=" + dailyLimit +
				", companyName='" + companyName + '\'' +
				", companyAddress='" + companyAddress + '\'' +
				", companyCity='" + companyCity + '\'' +
				", localCurrency=" + localCurrency +
				", currencyPair=" + currencyPair +
				", baseCurrency=" + accountingCurrency +
				", district='" + district + '\'' +
				", autoRevalInt=" + autoRevalInt +
				", profitCalculationMethodInt=" + profitCalculationMethodInt +
				", autoRevalType=" + autoRevalType +
				", profitCalculationMethod=" + profitCalculationMethod +
				'}';
	}
}