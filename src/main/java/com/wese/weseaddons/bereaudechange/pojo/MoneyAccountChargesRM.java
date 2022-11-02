package com.wese.weseaddons.bereaudechange.pojo;

public class MoneyAccountChargesRM {

    private long id ;
    private MoneyAccount moneyAccount ;
    private Charges charges ;
    private boolean active ;


    public MoneyAccountChargesRM() {
    }

    public MoneyAccountChargesRM(MoneyAccount moneyAccount, Charges charges) {
        this.moneyAccount = moneyAccount;
        this.charges = charges;
    }

    public MoneyAccountChargesRM(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MoneyAccount getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(MoneyAccount moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    public Charges getCharges() {
        return charges;
    }

    public void setCharges(Charges charges) {
        this.charges = charges;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
