
package com.wese.weseaddons.dashboard.pojo ;

import com.wese.weseaddons.dashboard.enumerations.AGING_DETAILS_ENUM;

public class AgingDetailsNormalizedData{

	private double average = 0;
	private double sum = 0;
	private int index = 0 ;
	private AGING_DETAILS_ENUM agingDetailsEnum ;

	public AgingDetailsNormalizedData(double value ,AGING_DETAILS_ENUM agingDetailsEnum) {
		this.agingDetailsEnum = agingDetailsEnum ;
		addToSum(value);

	}

	public void addToSum(double value) {
		this.sum += value ;
		addToAverage();
	}

	public void addToAverage(){
		++this.index ;
		this.average = sum / index ;
	}


	public AGING_DETAILS_ENUM getAgingDetailsEnum() {
		return agingDetailsEnum;
	}

	public double getAverage() {
		return average;
	}

	public double getSum() {
		return sum;
	}
}