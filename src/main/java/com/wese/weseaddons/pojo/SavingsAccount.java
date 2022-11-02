package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.helper.JsonHelper;

import java.math.BigDecimal;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavingsAccount {

    private long id ;
    private long clientId ;
    private String accountNumber ;
    private long savingsProductId ;
    private String savingsProductName ;
    private BigDecimal accountBalance ;
    private String currencyCode ;
    private boolean active ;
    private Status status ;
    private Currency currency ;
    private SavingsAccountSummary summary ;

    public SavingsAccount(){
    }

    public SavingsAccount(Long id){
        this.id = id ;
    }

    public SavingsAccount(long id , String accountNumber ,long clientId ,long savingsProductId ,String savingsProductName,BigDecimal accountBalance ,String currencyCode ,boolean active){

        this.id = id ;
        this.clientId = clientId ;
        this.accountNumber = accountNumber ;
        this.accountBalance = accountBalance ;
        this.savingsProductId = savingsProductId ;
        this.savingsProductName = savingsProductName ;
        this.currencyCode = currencyCode ;
        this.active = active;
    }

    public SavingsAccountSummary getSummary() {
        return summary;
    }

    public void setSummary(SavingsAccountSummary summary) {
        this.summary = summary;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setSavingsProductId(long savingsProductId) {
        this.savingsProductId = savingsProductId;
    }

    public void setSavingsProductName(String savingsProductName) {
        this.savingsProductName = savingsProductName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return active;
    }

    public BigDecimal getAccountBalance() {

        Optional.ofNullable(summary).ifPresent(e->{
            this.accountBalance = e.getAccountBalance();
        });

        return this.accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getSavingsProductName(){
        return savingsProductName ;
    }

    public long getSavingsProductId(){
        return savingsProductId ;
    }


    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public String getAccountNumber(){
        return accountNumber ;
    }

    public long getClientId(){
        return clientId ;
    }

    public static SavingsAccount fromHttpResponse(String arg){
        return JsonHelper.serializeFromHttpResponse(new SavingsAccount() ,arg);
    }

    /**
     * When was this added ? ,its proving vital now too many balance mismatch errors
     * Modified 28/09/2022 at 1555
     */
    public SavingsAccount update() {
        SavingsAccount savingsAccount = SavingsRequest.savingsAccount(id);
        this.accountBalance = savingsAccount.getSummary().getAvailableBalance();
        return savingsAccount;
    }
}
