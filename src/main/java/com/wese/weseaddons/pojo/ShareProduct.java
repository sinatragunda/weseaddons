/*

    Created by Sinatra Gunda
    At 8:42 AM on 6/23/2021

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.helper.JsonHelper;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShareProduct {

    public int id;
    public String name;
    public String shortName;
    public String description;
    public Currency currency;
    public Long totalShares;
    public Long totalSharesIssued;
    public double unitPrice;
    public double shareCapital;
    public Long minimumShares;
    public Long nominalShares;
    public Long maximumShares;

    public ShareProduct(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(Long totalShares) {
        this.totalShares = totalShares;
    }

    public Long getTotalSharesIssued() {
        return totalSharesIssued;
    }

    public void setTotalSharesIssued(Long totalSharesIssued) {
        this.totalSharesIssued = totalSharesIssued;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getShareCapital() {
        return shareCapital;
    }

    public void setShareCapital(double shareCapital) {
        this.shareCapital = shareCapital;
    }

    public Long getMinimumShares() {
        return minimumShares;
    }

    public void setMinimumShares(Long minimumShares) {
        this.minimumShares = minimumShares;
    }

    public Long getNominalShares() {
        return nominalShares;
    }

    public void setNominalShares(Long nominalShares) {
        this.nominalShares = nominalShares;
    }

    public Long getMaximumShares() {
        return maximumShares;
    }

    public void setMaximumShares(Long maximumShares) {
        this.maximumShares = maximumShares;
    }

    public static ShareProduct fromHttpResponse(String arg){
        return JsonHelper.serializeFromHttpResponse(new ShareProduct() ,arg);
    }
}
