package com.wese.weseaddons.sqlquerries.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class LoansDistrubution implements PojoInterface{


    private Long id ;
    private String productName ;
    private long loanProductId ;
    private double principalAmount ;


    @Override
    public String getSchema() {
        return null;
    }


    public LoansDistrubution() {
    }


    @Override
    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(long loanProductId) {
        this.loanProductId = loanProductId;
    }

    public double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(double principalAmount) {
        this.principalAmount = principalAmount;
    }
}
