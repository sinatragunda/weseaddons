package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.ussd.interfaces.Pojo; ;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanProducts implements Pojo{

	private String name ;
	private long id ;
	private double minPrincipal ;
	private double maxPrincipal ;
	private int repaymentsEvery ;
	private int numberOfRepayments ;
	private int amortizationType ;
	private int interestType ;
	private double interestRatePerPeriod ;
	private int interestCalculationPeriodType ;
	private int transactionProcessingStrategyId ;
	private int repaymentFrequencyType ;

	public LoanProducts(long id ,String name ,double minPrincipal ,double maxPrincipal){
		this.id = id ;
		this.name = name ;
		this.minPrincipal = minPrincipal ;
		this.maxPrincipal = maxPrincipal;
	}


	public LoanProducts(long id , String name , double minPrincipal , double maxPrincipal ,double interestRatePerPeriod , int repaymentsEvery ,int numberOfRepayments ,int amortizationType ,int interestType ,int interestCalculationPeriodType ,int transactionProcessingStrategyId ,int repaymentFrequencyType){
		this.id = id ;
		this.name = name ;
		this.minPrincipal = minPrincipal ;
		this.maxPrincipal = maxPrincipal;
		this.repaymentsEvery = repaymentsEvery ;
		this.numberOfRepayments = numberOfRepayments ;
		this.interestType = interestType ;
		this.amortizationType = amortizationType ;
		this.interestRatePerPeriod = interestRatePerPeriod ;
		this.interestCalculationPeriodType = interestCalculationPeriodType ;
		this.transactionProcessingStrategyId = transactionProcessingStrategyId ;
		this.repaymentFrequencyType = repaymentFrequencyType ;
	}


	public int getRepaymentFrequencyType() {
		return repaymentFrequencyType;
	}

	public int getInterestCalculationPeriodType() {
		return interestCalculationPeriodType;
	}

	public int getTransactionProcessingStrategyId() {
		return transactionProcessingStrategyId;
	}

	public double getInterestRatePerPeriod() {
		return interestRatePerPeriod;
	}

	public long getId(){
		return id ;
	}

	public String getName(){
		return name ;
	}

	public double getMinPrincipal(){
		return minPrincipal;
	}

	public double getMaxPrincipal(){
		return maxPrincipal ;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRepaymentsEvery() {
		return repaymentsEvery;
	}

	public int getAmortizationType() {
		return amortizationType;
	}

	public int getNumberOfRepayments() {
		return numberOfRepayments;
	}

	public int getInterestType() {
		return interestType;
	}
}