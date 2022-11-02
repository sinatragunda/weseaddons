package com.wese.weseaddons.pojo;

public class ClientLoanAccounts {

    private long loanId ;
    private String accountNumber ;



    public ClientLoanAccounts(long loanId , String accountNumber){
        this.accountNumber = accountNumber;
        this.loanId = loanId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }



    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }
}
