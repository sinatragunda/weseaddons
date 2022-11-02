
package com.wese.weseaddons.bereaudechange.pojo;


import com.wese.weseaddons.pojo.AppUser ;

import com.wese.weseaddons.enumerations.TRANSACTION_TYPE;

public class FxTellerTransactions{

	private long id ;
	private double amount ;
	private TRANSACTION_TYPE transactionType ;
	private AppUser authoriser ;
	private long date ; 
	private StandardCurrency standardCurrency;

	public FxTellerTransactions(){}

	public FxTellerTransactions(long id){
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

	public TRANSACTION_TYPE getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TRANSACTION_TYPE transactionType) {
		this.transactionType = transactionType;
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

	public void setStandardCurrency(StandardCurrency standardCurrency) {
		this.standardCurrency = standardCurrency;
	}
}