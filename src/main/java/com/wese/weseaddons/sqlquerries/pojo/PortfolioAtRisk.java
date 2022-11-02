package com.wese.weseaddons.sqlquerries.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class PortfolioAtRisk implements PojoInterface{

    private Long id ;
    private double principalOutstanding ;
    private double risk ;


    public PortfolioAtRisk(){}


    @Override
    public Long getId() {
        return id;
    }

    public double getPrincipalOutstanding() {
        return principalOutstanding;
    }

    public void setPrincipalOutstanding(double principalOutstanding) {
        this.principalOutstanding = principalOutstanding;
    }


    public double getRisk() {
        return risk;
    }

    public void setRisk(double risk) {
        this.risk = risk;
    }

    @Override
    public String getSchema() {
        return null;
    }
}
