package com.wese.weseaddons.assetregister.pojo ;

import com.wese.weseaddons.assetregister.interfaces.DepreciationObjects;

public class StraightLine implements DepreciationObjects {

	private double depreciationExpense  ;
	private double accumulatedDepreciationAtYearEnd ;
	private double bookValueAtEndYear ;


	public StraightLine() {
	}

	public StraightLine(double depreciationExpense, double accumulatedDepreciationAtYearEnd, double bookValueAtEndYear) {
		this.depreciationExpense = depreciationExpense;
		this.accumulatedDepreciationAtYearEnd = accumulatedDepreciationAtYearEnd;
		this.bookValueAtEndYear = bookValueAtEndYear;
	}

	public double getDepreciationExpense() {
		return depreciationExpense;
	}

	public void setDepreciationExpense(double depreciationExpense) {
		this.depreciationExpense = depreciationExpense;
	}

	public double getAccumulatedDepreciationAtYearEnd() {
		return accumulatedDepreciationAtYearEnd;
	}

	public void setAccumulatedDepreciationAtYearEnd(double accumulatedDepreciationAtYearEnd) {
		this.accumulatedDepreciationAtYearEnd = accumulatedDepreciationAtYearEnd;
	}

	public double getBookValueAtEndYear() {
		return bookValueAtEndYear;
	}

	public void setBookValueAtEndYear(double bookValueAtEndYear) {
		this.bookValueAtEndYear = bookValueAtEndYear;
	}
}