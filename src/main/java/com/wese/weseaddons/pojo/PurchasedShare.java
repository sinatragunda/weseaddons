/*

    Created by Sinatra Gunda
    At 9:23 AM on 6/22/2021

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.helper.JsonHelper;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchasedShare {

    public int id;
    public int accountId;
    public List<Integer> purchasedDate;
    public int numberOfShares;
    public double purchasedPrice;
    public Status status;
    public double amount;
    public double chargeAmount;
    public double amountPaid;

    public PurchasedShare(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public List<Integer> getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(List<Integer> purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public double getPurchasedPrice() {
        return purchasedPrice;
    }

    public void setPurchasedPrice(double purchasedPrice) {
        this.purchasedPrice = purchasedPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(double chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public static PurchasedShare fromHttpResponse(String arg){
        return JsonHelper.serializeFromHttpResponse(new PurchasedShare() ,arg);
    }
}
