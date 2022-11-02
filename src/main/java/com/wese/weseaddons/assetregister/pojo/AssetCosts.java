package com.wese.weseaddons.assetregister.pojo;

import com.wese.weseaddons.assetregister.enumeration.USEFULL_LIFE;

public class AssetCosts {

    private double annualDepreciation ;
    private double costOfAsset ;
    private double residualValue ;
    private double estimatedTotalProduction ;
    private double actualProduction ;
    private int duration ;
    private USEFULL_LIFE usefullLife ;



    public void setResidualValue(double residualValue) {
        this.residualValue = residualValue;
    }

    public double getEstimatedTotalProduction() {
        return estimatedTotalProduction;
    }

    public void setEstimatedTotalProduction(double estimatedTotalProduction) {
        this.estimatedTotalProduction = estimatedTotalProduction;
    }

    public double getActualProduction() {
        return actualProduction;
    }

    public void setActualProduction(double actualProduction) {
        this.actualProduction = actualProduction;
    }

    public double getAnnualDepreciation() {
        return annualDepreciation;
    }

    public void setAnnualDepreciation(double annualDepreciation) {
        this.annualDepreciation = annualDepreciation;
    }

    public double getCostOfAsset() {
        return costOfAsset;
    }

    public void setCostOfAsset(double costOfAsset) {
        this.costOfAsset = costOfAsset;
    }

    public double getResidualValue() {
        return residualValue;
    }

    public void setResidual(double residualValue) {
        this.residualValue = residualValue;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public USEFULL_LIFE getUsefullLife() {
        return usefullLife;
    }

    public void setUsefullLife(USEFULL_LIFE usefullLife) {
        this.usefullLife = usefullLife;
    }
}
