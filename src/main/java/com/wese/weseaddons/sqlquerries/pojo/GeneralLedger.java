/*Created by Sinatra Gunda
  At 08:16 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.pojo;
import com.wese.weseaddons.interfaces.PojoInterface;

import java.util.Date ;

public class GeneralLedger implements PojoInterface{


    private Double debitAmount =0.0 ,creditAmount =0.0, afterTxn =0.0,openingBalance =0.0;
    private Long  acid1 ;
    private String reportName ,accountName ,description ,branchName ,reportId ,transactionId,reportHeader;
    private Date entryDate ,fromDate ,toDate;

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getAfterTxn() {
        return afterTxn;
    }

    public void setAfterTxn(Double afterTxn) {
        this.afterTxn = afterTxn;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Long getAcid1() {
        return acid1;
    }

    public void setAcid1(Long acid1) {
        this.acid1 = acid1;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReportHeader() {
        return reportHeader;
    }

    public void setReportHeader(String reportHeader) {
        this.reportHeader = reportHeader;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public String getSchema() {
        return null;
    }

    @Override
    public Long getId() {
        return null;
    }
}
