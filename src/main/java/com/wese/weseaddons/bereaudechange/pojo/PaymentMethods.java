package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.PAYMENT_METHODS;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;

public class PaymentMethods {

    private long id ;
    private String name ;
    private String description ;
    private T_ACCOUNT tAccount ;
    private PAYMENT_METHODS paymentMethod ;


    public PaymentMethods() {
    }

    public PaymentMethods(long id) {
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

    public T_ACCOUNT gettAccount() {
        return tAccount;
    }

    public void settAccount(int tAccountInt) {

        this.tAccount = T_ACCOUNT.fromInt(tAccountInt);
    }

    public PAYMENT_METHODS getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = PAYMENT_METHODS.fromInt(paymentMethod);
    }

}

