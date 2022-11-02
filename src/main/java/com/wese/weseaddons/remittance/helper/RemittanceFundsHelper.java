package com.wese.weseaddons.remittance.helper;

public class RemittanceFundsHelper{


	private double amount ;
	private double commission ;


	public RemittanceFundsHelper(double amount ,double commission){
		this.amount = amount ;
		this.commission = commission ;

		transactionAmount(amount ,commission);

	}

	
	public void transactionAmount(double initialAmount ,double commisionPercentage){

		this.commission = (commisionPercentage / 100 ) * initialAmount ;
		this.amount = initialAmount - commission ;

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
}