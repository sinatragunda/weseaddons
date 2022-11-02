package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.CHARGE_CRITERIA;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;

public class Charges {

    private long id ;
    private String name ;
    private String description ;
    private double amount ;
    private T_ACCOUNT tAccount;
    private CHARGE_CRITERIA chargeCriteria ;


    public Charges() {
    }

    public Charges(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public T_ACCOUNT gettAccount() {
        return tAccount;
    }

    public void settAccount(int tAccountInt) {

        this.tAccount = T_ACCOUNT.fromInt(tAccountInt);
    }

    public CHARGE_CRITERIA getChargeCriteria() {
        return chargeCriteria;
    }

    public void setChargeCriteria(int chargeCriteria) {
        this.chargeCriteria = CHARGE_CRITERIA.fromInt(chargeCriteria);
    }

}

