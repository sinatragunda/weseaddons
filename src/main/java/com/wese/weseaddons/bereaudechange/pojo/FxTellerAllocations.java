
package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.FINANCIAL_INSTRUMENT_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;

import com.wese.weseaddons.pojo.AppUser ;

public class FxTellerAllocations {

	private long id ;
	private double amount ;
	private FINANCIAL_INSTRUMENT_TYPE financialInstrumentType ;
	private long date ;
	private T_ACCOUNT tAccount ;
	private AppUser authoriser ;
	private FxCashier fxCashier ;
	private StandardCurrency standardCurrency;

	public FxTellerAllocations(){}

	public FxTellerAllocations(long id){
		this.id = id ;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public FINANCIAL_INSTRUMENT_TYPE getFinancialInstrumentType() {
		return financialInstrumentType;
	}

	public void setFinancialInstrumentType(FINANCIAL_INSTRUMENT_TYPE financialInstrumentType) {
		this.financialInstrumentType = financialInstrumentType;
	}

	public T_ACCOUNT gettAccount() {
		return tAccount;
	}

	public void settAccount(T_ACCOUNT tAccount) {
		this.tAccount = tAccount;
	}

	public AppUser getAuthoriser() {
		return authoriser;
	}

	public void setAuthoriser(AppUser authoriser) {
		this.authoriser = authoriser;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public StandardCurrency getStandardCurrency() {
		return standardCurrency;
	}

	public FxCashier getFxCashier() {
		return fxCashier;
	}

	public void setFxCashier(FxCashier fxCashier) {
		this.fxCashier = fxCashier;
	}

	public void setStandardCurrency(StandardCurrency standardCurrency) {
		this.standardCurrency = standardCurrency;
	}
}