/*Created by Sinatra Gunda
  At 1:03 PM on 3/2/2020 */

package com.wese.weseaddons.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wese.weseaddons.helper.TimeHelper;

import java.util.Date ;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanRepaymentSchedule {

    private int dueDate[] ;
    private int fromDate[] ;
    private int obligationsMetOn[] ;

    private boolean complete = true;
    private double principalPaid ;
    private double principalDue ;
    private double interestDue ;
    private double interestPaid ;
    private double totalDueForPeriod = 0.0;
    private double totalPaidForPeriod ;
    private double totalInstallmentAmountForPeriod = 0.0;
    private double feeChargesDue ;
    private double feeChargesWaived ;
    private double feeChargesPaid ;
    private double principalLoanBalanceOutstanding;
    private double interestWaived ;
    private double penaltyChargesPaid;
    private double feeChargesOutstanding;
    private double totalWrittenOffForPeriod ;
    
    public LoanRepaymentSchedule() {
    }

    public LoanRepaymentSchedule(int dueDate[], int fromDate[], int obligationsMetOn[], boolean complete, double principalPaid, double principalDue, double interestDue, double interestPaid, double totalDueForPeriod, double totalPaidForPeriod) {
        this.dueDate = dueDate;
        this.fromDate = fromDate;
        this.obligationsMetOn = obligationsMetOn;
        this.complete = complete;
        this.principalPaid = principalPaid;
        this.principalDue = principalDue;
        this.interestDue = interestDue;
        this.interestPaid = interestPaid;
        this.totalDueForPeriod = totalDueForPeriod;
        this.totalPaidForPeriod = totalPaidForPeriod;
    }

    public double getTotalWrittenOffForPeriod() {
        return totalWrittenOffForPeriod;
    }

    public void setTotalWrittenOffForPeriod(double totalWrittenOffForPeriod) {
        this.totalWrittenOffForPeriod = totalWrittenOffForPeriod;
    }

    public double getFeeChargesOutstanding() {
        return feeChargesOutstanding;
    }

    public void setFeeChargesOutstanding(double feeChargesOutstanding) {
        this.feeChargesOutstanding = feeChargesOutstanding;
    }

    public double getPenaltyChargesPaid() {
        return penaltyChargesPaid;
    }

    public void setPenaltyChargesPaid(double penaltyChargesPaid) {
        this.penaltyChargesPaid = penaltyChargesPaid;
    }

    public double getPrincipalLoanBalanceOutstanding() {
        return principalLoanBalanceOutstanding;
    }

    public void setPrincipalLoanBalanceOutstanding(double principalLoanBalanceOutstanding) {
        this.principalLoanBalanceOutstanding = principalLoanBalanceOutstanding;
    }

    public double getInterestWaived() {
        return interestWaived;
    }

    public void setInterestWaived(double interestWaived) {
        this.interestWaived = interestWaived;
    }

    public double getFeeChargesPaid() {
        return feeChargesPaid;
    }

    public void setFeeChargesPaid(double feeChargesPaid) {
        this.feeChargesPaid = feeChargesPaid;
    }

    public double getFeeChargesWaived() {
        return feeChargesWaived;
    }

    public void setFeeChargesWaived(double feeChargesWaived) {
        this.feeChargesWaived = feeChargesWaived;
    }

    public double getTotalInstallmentAmountForPeriod() {
        return totalInstallmentAmountForPeriod;
    }

    public void setTotalInstallmentAmountForPeriod(double totalInstallmentAmountForPeriod) {
        this.totalInstallmentAmountForPeriod = totalInstallmentAmountForPeriod;
    }

    public double getFeeChargesDue() {
        return feeChargesDue;
    }

    public void setFeeChargesDue(double feeChargesDue) {
        this.feeChargesDue = feeChargesDue;
    }

    public Date getDueDate() {
        return TimeHelper.dateFromArray(this.dueDate);
    }

    public void setDueDate(int dueDate[]) {
        this.dueDate = dueDate;
    }

    public Date getFromDate() {
        return null;
    }

    public void setFromDate(int fromDate[]) {
        this.fromDate = fromDate;
    }

    public Date getObligationsMetOn() {
        return null;
    }

    public void setObligationsMetOn(int obligationsMetOn[]) {
        this.obligationsMetOn = obligationsMetOn;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public double getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public double getPrincipalDue() {
        return principalDue;
    }

    public void setPrincipalDue(double principalDue) {
        this.principalDue = principalDue;
    }

    public double getInterestDue() {
        return interestDue;
    }

    public void setInterestDue(double interestDue) {
        this.interestDue = interestDue;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public double getTotalDueForPeriod() {
        return totalDueForPeriod;
    }

    public void setTotalDueForPeriod(double totalDueForPeriod) {
        this.totalDueForPeriod = totalDueForPeriod;
    }

    public double getTotalPaidForPeriod() {
        return totalPaidForPeriod;
    }

    public void setTotalPaidForPeriod(double totalPaidForPeriod) {
        this.totalPaidForPeriod = totalPaidForPeriod;
    }


    public static LoanRepaymentSchedule fromJson(String arg){

        ObjectMapper objectMapper = new ObjectMapper();
        LoanRepaymentSchedule loanRepaymentSchedule = null ;
        try{
            objectMapper.configure(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            loanRepaymentSchedule = objectMapper.readValue(arg ,LoanRepaymentSchedule.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return loanRepaymentSchedule;

    }
}
