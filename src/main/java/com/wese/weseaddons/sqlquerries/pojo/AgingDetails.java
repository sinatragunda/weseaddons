package com.wese.weseaddons.sqlquerries.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class AgingDetails implements PojoInterface {

	private Long id ;
	private int daysInArrears ;
	private double interestOverdue ;
	private double principalOverdue ;

	@Override
	public String getSchema() {
		return null;
	}

	public AgingDetails(){}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDaysInArrears() {
		return daysInArrears;
	}

	public void setDaysInArrears(int daysInArrears) {
		this.daysInArrears = daysInArrears;
	}

	public double getInterestOverdue() {
		return interestOverdue;
	}

	public void setInterestOverdue(double interestOverdue) {
		this.interestOverdue = interestOverdue;
	}

	public double getPrincipalOverdue() {
		return principalOverdue;
	}

	public void setPrincipalOverdue(double principalOverdue) {
		this.principalOverdue = principalOverdue;
	}
}