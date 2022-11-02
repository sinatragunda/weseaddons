/*

    Created by Sinatra Gunda
    At 9:10 AM on 6/22/2021

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wese.weseaddons.helper.JsonHelper;

import java.io.IOException;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShareAccount {

    public Long id;
    public String accountNo;
    public String savingsAccountNumber;
    public int clientId;
    public String clientName;
    public int productId;
    public String productName;
    public Status status;
    public Timeline timeline;
    public Currency currency;
    public ShareSummary summary;
    public List<PurchasedShare> purchasedShares;
    public double currentMarketPrice;
    public int lockinPeriod;
    public int minimumActivePeriod;
    public boolean allowDividendCalculationForInactiveClients;
    public List<Object> dividends;
    private Long totalApprovedShares = 0L ;


    public ShareAccount() {
    }

    public Long getTotalApprovedShares() {
        return totalApprovedShares;
    }

    public void setTotalApprovedShares(Long totalApprovedShares) {
        this.totalApprovedShares = totalApprovedShares;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getSavingsAccountNumber() {
        return savingsAccountNumber;
    }

    public void setSavingsAccountNumber(String savingsAccountNumber) {
        this.savingsAccountNumber = savingsAccountNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public ShareSummary getSummary() {
        return summary;
    }

    public void setSummary(ShareSummary summary) {
        this.summary = summary;
    }

    public List<PurchasedShare> getPurchasedShares() {
        return purchasedShares;
    }

    public void setPurchasedShares(List<PurchasedShare> purchasedShares) {
        this.purchasedShares = purchasedShares;
    }

    public double getCurrentMarketPrice() {
        return currentMarketPrice;
    }

    public void setCurrentMarketPrice(double currentMarketPrice) {
        this.currentMarketPrice = currentMarketPrice;
    }

    public int getLockinPeriod() {
        return lockinPeriod;
    }

    public void setLockinPeriod(int lockinPeriod) {
        this.lockinPeriod = lockinPeriod;
    }

    public int getMinimumActivePeriod() {
        return minimumActivePeriod;
    }

    public void setMinimumActivePeriod(int minimumActivePeriod) {
        this.minimumActivePeriod = minimumActivePeriod;
    }

    public boolean isAllowDividendCalculationForInactiveClients() {
        return allowDividendCalculationForInactiveClients;
    }

    public void setAllowDividendCalculationForInactiveClients(boolean allowDividendCalculationForInactiveClients) {
        this.allowDividendCalculationForInactiveClients = allowDividendCalculationForInactiveClients;
    }

    public List<Object> getDividends() {
        return dividends;
    }

    public void setDividends(List<Object> dividends) {
        this.dividends = dividends;
    }

    public static ShareAccount fromHttpResponse(String arg){

        return JsonHelper.serializeFromHttpResponse(new ShareAccount() ,arg);
    }
}
