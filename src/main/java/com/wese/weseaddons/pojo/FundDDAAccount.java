/*

    Created by Sinatra Gunda
    At 1:31 PM on 2/2/2022

*/
package com.wese.weseaddons.pojo;

import com.wese.weseaddons.batchprocessing.enumerations.STAGING;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;

import java.math.BigDecimal;

public class FundDDAAccount extends SavingsAccount {


    private String name ;
    private BigDecimal amount ;
    private STAGING staging ;
    private int count ;
    private Long resourceId ;
    private String description;

    public FundDDAAccount(){}

    public FundDDAAccount(Long id){
        super(id);
    }


    public FundDDAAccount(Long id, String name, BigDecimal amount, STAGING staging, int count) {
        super(id);
        this.name = name;
        this.amount = amount;
        this.staging = staging;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public STAGING getStaging() {
        return staging;
    }

    public int getCount() {
        return count;
    }

    public void setResourceId(Long id){
        this.resourceId = id ;
    }

    public Long getResourceId(){
        return this.resourceId;
    }

    public void updateFromSavingsAccount(SavingsAccount savingsAccount){
        setClientId(savingsAccount.getClientId());
    }

    public void setStatusDescription(String arg){
        this.description = arg;
    }

    public String getStatusDescription(){
        return this.description;
    }

    /**
     * Added 14/10/2022 at 0240
     */ 
    public ExcelClientData toClientData(){
        return new ExcelClientData(this);
    }
}
