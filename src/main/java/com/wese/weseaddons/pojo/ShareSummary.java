/*

    Created by Sinatra Gunda
    At 9:23 AM on 6/22/2021

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShareSummary {
    public int id;
    public String accountNo;
    public int totalApprovedShares;
    public int totalPendingForApprovalShares;
    public int productId;
    public String productName;
    public Status status;
    public Currency currency;
    public Timeline timeline;


    public ShareSummary(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public int getTotalApprovedShares() {
        return totalApprovedShares;
    }

    public void setTotalApprovedShares(int totalApprovedShares) {
        this.totalApprovedShares = totalApprovedShares;
    }

    public int getTotalPendingForApprovalShares() {
        return totalPendingForApprovalShares;
    }

    public void setTotalPendingForApprovalShares(int totalPendingForApprovalShares) {
        this.totalPendingForApprovalShares = totalPendingForApprovalShares;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
