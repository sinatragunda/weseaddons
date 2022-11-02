package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.pojo.AppUser ;
import com.wese.weseaddons.bereaudechange.enumerations.FUNDS_ACTION_TYPE;

public class FundsAction {

    private long id ;
    private FUNDS_ACTION_TYPE fundsActionType ;
    private MoneyAccount sourceMoneyAccount ;
    private MoneyAccount destinationMoneyAccount ;
    private double amount ;
    private AppUser authoriser ;
    private double charges ;

    public FundsAction() {
    }

    public FundsAction(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public FUNDS_ACTION_TYPE getFundsActionType() {
        return fundsActionType;
    }

    public void setFundsActionType(int fundsActionType) {

        this.fundsActionType = FUNDS_ACTION_TYPE.fromInt(fundsActionType);
    }

    public MoneyAccount getSourceMoneyAccount() {
        return sourceMoneyAccount;
    }

    public void setSourceMoneyAccount(MoneyAccount sourceMoneyAccount) {
        this.sourceMoneyAccount = sourceMoneyAccount;
    }

    public MoneyAccount getDestinationMoneyAccount() {
        return destinationMoneyAccount;
    }

    public void setDestinationMoneyAccount(MoneyAccount destinationMoneyAccount) {
        this.destinationMoneyAccount = destinationMoneyAccount;
    }

    public AppUser getAuthoriser() {
        return authoriser;
    }

    public void setAuthoriser(AppUser authoriser) {
        this.authoriser = authoriser;
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }
}

