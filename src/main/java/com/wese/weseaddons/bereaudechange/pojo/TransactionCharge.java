package com.wese.weseaddons.bereaudechange.pojo;

public class TransactionCharge {

    private long id ;
    private MoneyAccountTransactions moneyAccountTransactions ;
    private Charges charges ;

    public TransactionCharge() {
    }

    public TransactionCharge(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MoneyAccountTransactions getMoneyAccountTransactions() {
        return moneyAccountTransactions;
    }

    public void setMoneyAccountTransactions(MoneyAccountTransactions moneyAccountTransactions) {
        this.moneyAccountTransactions = moneyAccountTransactions;
    }

    public Charges getCharges() {
        return charges;
    }

    public void setCharges(Charges charges) {
        this.charges = charges;
    }
}
