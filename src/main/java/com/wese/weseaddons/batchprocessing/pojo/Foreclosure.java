/*Created by Sinatra Gunda
  At 15:16 on 9/3/2020 */

package com.wese.weseaddons.batchprocessing.pojo;

public class Foreclosure {

    private String customerName ;
    private int count ;
    private String loanAccountNumber ;
    private String note ;
    private String transactionDate ;


    public Foreclosure(){}

    public Foreclosure(String note, String transactionDate) {
        this.note = note;
        this.transactionDate = transactionDate;
    }

    public Foreclosure(String loanAccountNumber , String customerName, int count) {
        this.customerName = customerName;
        this.count = count;
        this.loanAccountNumber = loanAccountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
