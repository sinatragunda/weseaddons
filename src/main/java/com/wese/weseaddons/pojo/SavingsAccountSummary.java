/*

    Created by Sinatra Gunda
    At 2:25 PM on 6/30/2021

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavingsAccountSummary {

    public Currency currency;
    public BigDecimal totalDeposits;
    public BigDecimal totalWithdrawals;
    public int totalInterestPosted;
    public BigDecimal accountBalance;
    public int totalOverdraftInterestDerived;
    public int interestNotPosted;
    public BigDecimal availableBalance;

    public SavingsAccountSummary(){}

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public int getTotalOverdraftInterestDerived() {
        return totalOverdraftInterestDerived;
    }

    public void setTotalOverdraftInterestDerived(int totalOverdraftInterestDerived) {
        this.totalOverdraftInterestDerived = totalOverdraftInterestDerived;
    }

    public int getInterestNotPosted() {
        return interestNotPosted;
    }

    public void setInterestNotPosted(int interestNotPosted) {
        this.interestNotPosted = interestNotPosted;
    }

    public BigDecimal getTotalDeposits() {
        return totalDeposits;
    }

    public void setTotalDeposits(BigDecimal totalDeposits) {
        this.totalDeposits = totalDeposits;
    }

    public BigDecimal getTotalWithdrawals() {
        return totalWithdrawals;
    }

    public void setTotalWithdrawals(BigDecimal totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }

    public int getTotalInterestPosted() {
        return totalInterestPosted;
    }

    public void setTotalInterestPosted(int totalInterestPosted) {
        this.totalInterestPosted = totalInterestPosted;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}
