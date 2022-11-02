
package com.wese.weseaddons.remittance.pojo ;


import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.bereaudechange.pojo.StandardCurrency;
import com.wese.weseaddons.remittance.enumerations.REMITTANCE_TRANSACTION_STATE;

public class RemittanceTransaction{

	private long id ;
	private double amount ;
	private double commission ;
	private Remitter remitterReciever ;
	private Remitter remitterSender ;
	private FxCashier fxCashierSender ;
	private FxCashier fxCashierReciever ;
	private StandardCurrency standardCurrency ;
	private REMITTANCE_TRANSACTION_STATE remittanceTransactionState ;
	private String fundsKey ;
	private long transactionDate ;
	private long disbursedDate ;
	
	
	public RemittanceTransaction() {}


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

	public double getCommission() {
		return commission;
	}


	public void setCommission(double commission) {
		this.commission = commission;
	}

	public long getDisbursedDate() {
		return disbursedDate;
	}

	public void setDisbursedDate(long disbursedDate) {
		this.disbursedDate = disbursedDate;
	}

	public Remitter getRemitterReciever() {
		return remitterReciever;
	}

	public void setRemitterReciever(Remitter remitterReciever) {
		this.remitterReciever = remitterReciever;
	}

	public Remitter getRemitterSender() {
		return remitterSender;
	}

	public void setRemitterSender(Remitter remitterSender) {
		this.remitterSender = remitterSender;
	}

	public StandardCurrency getStandardCurrency() {
		return standardCurrency;
	}

	public void setStandardCurrency(StandardCurrency standardCurrency) {
		this.standardCurrency = standardCurrency;
	}

	public REMITTANCE_TRANSACTION_STATE getRemittanceTransactionState() {
		return remittanceTransactionState;
	}

	public void setRemittanceTransactionState(int remittanceTransactionState) {
		this.remittanceTransactionState = REMITTANCE_TRANSACTION_STATE.fromInt(remittanceTransactionState);
	}

	public String getFundsKey() {
		return fundsKey;
	}

	public void setFundsKey(String fundsKey) {
		this.fundsKey = fundsKey;
	}

	public long getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(long transactionDate) {
		this.transactionDate = transactionDate;
	}

	public FxCashier getFxCashierSender() {
		return fxCashierSender;
	}

	public void setFxCashierSender(FxCashier fxCashierSender) {
		this.fxCashierSender = fxCashierSender;
	}

	public FxCashier getFxCashierReciever() {
		return fxCashierReciever;
	}

	public void setFxCashierReciever(FxCashier fxCashierReciever) {
		this.fxCashierReciever = fxCashierReciever;
	}
}