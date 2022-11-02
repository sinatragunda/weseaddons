package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;

public class MoneyAccountTransactions {

    private long id ;
    private double amount ;
    private double charges ;
    private double runningBalance ;
    private FxDeal fxDeal;
    private MoneyAccount moneyAccount ;
    private T_ACCOUNT tAccount ;


    public MoneyAccountTransactions() {
    }

    public MoneyAccountTransactions(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FxDeal getFxDeal() {
        return fxDeal;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }

    public void setFxDeal(FxDeal fxDeal) {
        this.fxDeal = fxDeal;
    }

    public MoneyAccount getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(MoneyAccount moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    public T_ACCOUNT gettAccount() {
        return tAccount;
    }

    public void settAccount(T_ACCOUNT tAccount) {
        this.tAccount = tAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(double runningBalance) {
        this.runningBalance = runningBalance;
    }
}
