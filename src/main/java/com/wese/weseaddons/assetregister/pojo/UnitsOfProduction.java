package com.wese.weseaddons.assetregister.pojo;

import com.wese.weseaddons.assetregister.interfaces.DepreciationObjects;

public class UnitsOfProduction implements DepreciationObjects{

    private int unitsOfProduction ;
    private int depreciationCostPerUnit ;
    private double depreciationExpense ;
    private double accumulatedDepreciation ;
    private double bookValue ;

    public UnitsOfProduction() {
    }

    public UnitsOfProduction(int unitsOfProduction, int depreciationCostPerUnit, double depreciationExpense, double accumulatedDepreciation, double bookValue) {
        this.unitsOfProduction = unitsOfProduction;
        this.depreciationCostPerUnit = depreciationCostPerUnit;
        this.depreciationExpense = depreciationExpense;
        this.accumulatedDepreciation = accumulatedDepreciation;
        this.bookValue = bookValue;
    }



    public int getUnitsOfProduction() {
        return unitsOfProduction;
    }

    public void setUnitsOfProduction(int unitsOfProduction) {
        this.unitsOfProduction = unitsOfProduction;
    }

    public int getDepreciationCostPerUnit() {
        return depreciationCostPerUnit;
    }

    public void setDepreciationCostPerUnit(int depreciationCostPerUnit) {
        this.depreciationCostPerUnit = depreciationCostPerUnit;
    }

    public double getDepreciationExpense() {
        return depreciationExpense;
    }

    public void setDepreciationExpense(double depreciationExpense) {
        this.depreciationExpense = depreciationExpense;
    }

    public double getAccumulatedDepreciation() {
        return accumulatedDepreciation;
    }

    public void setAccumulatedDepreciation(double accumulatedDepreciation) {
        this.accumulatedDepreciation = accumulatedDepreciation;
    }

    public double getBookValue() {
        return bookValue;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }
}
