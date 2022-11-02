
package com.wese.weseaddons.bereaudechange.pojo;

public class FxTellerTill{

	private long id ;
	private long date ;
	private MoneyAccountTransactions moneyAccountTransactions ;

	public FxTellerTill(){}

	public FxTellerTill(long id){
		this.id = id ;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public MoneyAccountTransactions getMoneyAccountTransactions() {
		return moneyAccountTransactions;
	}

	public void setMoneyAccountTransactions(MoneyAccountTransactions moneyAccountTransactions) {
		this.moneyAccountTransactions = moneyAccountTransactions;
	}
}