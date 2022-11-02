package com.wese.weseaddons.batchprocessing.client;

public class ClientLoanAccount {

    private long loanId ;
    private long productId ;
    private String accountNumber ;
    private String productName ;
    private boolean active ;
   

    public ClientLoanAccount(long loanId , String accountNumber,long productId ,String productName,boolean active){
        this.accountNumber = accountNumber;
        this.loanId = loanId;
        this.productId = productId ;
        this.productName = productName ;
        this.active = active ;
    }

    public long getProductId() {
        return productId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean isActive() {
        return active;
    }

    public String getProductName() {
        return productName;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }
}
