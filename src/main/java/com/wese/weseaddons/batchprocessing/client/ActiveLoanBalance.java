package com.wese.weseaddons.batchprocessing.client;

public class ActiveLoanBalance {

    public double balance ;
    public double principal ;


    public ActiveLoanBalance(double principal ,double balance){
        this.balance = balance ;
        this.principal = principal ;
    }


    public double getBalance() {
        return balance;
    }

    public double getPrincipal() {
        return principal;
    }
}
