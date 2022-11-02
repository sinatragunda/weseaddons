/*

    Created by Sinatra Gunda
    At 8:05 AM on 3/19/2022

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.batchprocessing.init.SSBPaymentsInit;

import javax.crypto.spec.OAEPParameterSpec;
import java.math.BigDecimal;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UndoTransaction {

    private String locale = "en";
    private String dateFormat = "dd MMMM yyyy";
    private BigDecimal transactionAmount = BigDecimal.ZERO ;
    private String transactionDate ;

    public UndoTransaction(){}

    public UndoTransaction(String transactionDate){
        boolean isPresent = Optional.ofNullable(transactionDate).isPresent();
        if(!isPresent){
            this.transactionDate = SSBPaymentsInit.getInstance().getTransactionDate();
            return;
        }
        this.transactionDate = transactionDate;
    }


    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDate(){
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
