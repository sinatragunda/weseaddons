package com.wese.weseaddons.sqlquerries.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class LoansSector implements PojoInterface{

    private Long id ;

    private String loanPurpose ;
    private double principalAmount ;


    @Override
    public String getSchema() {
        return null;
    }


    public LoansSector() {
    }


    @Override
    public Long getId() {
        return id;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }


    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }
}
