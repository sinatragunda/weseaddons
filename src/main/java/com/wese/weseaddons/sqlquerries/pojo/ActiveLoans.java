/*Created by Sinatra Gunda
  At 08:17 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;
import java.util.Date ;
public class ActiveLoans implements PojoInterface{

    String name ,currency ,grzLoan ,agingBrack,gender,product, employerName ,loanAccountNumber,officeName ,loanOfficer ,description ;
    Double interestRate,interestOutstanding, principalAmount, accruedInterest ,interestRecievable ,interestPaid ,principalPaid ,principalOutstanding ,princpalOverdue ,interestOverdue ,feesRepaid ,feesOutstanding ,feesOverdue ,penaltiesRepaid, penaltiesOutstanding , penaltiesDue,totalDue;
    Long loanId ;
    Date disbursedOn ,maturedOn ;


    @Override
    public String getSchema() {
        return null;
    }

    @Override
    public Long getId() {
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLoanOfficer() {
        return loanOfficer;
    }

    public void setLoanOfficer(String loanOfficer) {
        this.loanOfficer = loanOfficer;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        employerName = employerName;
    }

    public String getLoanAccountNumber() {
        return loanAccountNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }

    public Double getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(Double accruedInterest) {
        this.accruedInterest = accruedInterest;
    }

    public Double getInterestRecievable() {
        return interestRecievable;
    }

    public void setInterestRecievable(Double interestRecievable) {
        this.interestRecievable = interestRecievable;
    }

    public Double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(Double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getGrzLoan() {
        return grzLoan;
    }

    public void setGrzLoan(String grzLoan) {
        this.grzLoan = grzLoan;
    }

    public String getAgingBrack() {
        return agingBrack;
    }

    public void setAgingBrack(String agingBrack) {
        this.agingBrack = agingBrack;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Double getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(Double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public Double getPrincipalOutstanding() {
        return principalOutstanding;
    }

    public void setPrincipalOutstanding(Double principalOutstanding) {
        this.principalOutstanding = principalOutstanding;
    }

    public Double getPrincpalOverdue() {
        return princpalOverdue;
    }

    public void setPrincpalOverdue(Double princpalOverdue) {
        this.princpalOverdue = princpalOverdue;
    }

    public Double getInterestOverdue() {
        return interestOverdue;
    }

    public void setInterestOverdue(Double interestOverdue) {
        this.interestOverdue = interestOverdue;
    }

    public Double getFeesRepaid() {
        return feesRepaid;
    }

    public void setFeesRepaid(Double feesRepaid) {
        this.feesRepaid = feesRepaid;
    }

    public Double getFeesOutstanding() {
        return feesOutstanding;
    }

    public void setFeesOutstanding(Double feesOutstanding) {
        this.feesOutstanding = feesOutstanding;
    }

    public Double getFeesOverdue() {
        return feesOverdue;
    }

    public void setFeesOverdue(Double feesOverdue) {
        this.feesOverdue = feesOverdue;
    }

    public Double getPenaltiesRepaid() {
        return penaltiesRepaid;
    }

    public void setPenaltiesRepaid(Double penaltiesRepaid) {
        this.penaltiesRepaid = penaltiesRepaid;
    }

    public Double getPenaltiesOutstanding() {
        return penaltiesOutstanding;
    }

    public void setPenaltiesOutstanding(Double penaltiesOutstanding) {
        this.penaltiesOutstanding = penaltiesOutstanding;
    }

    public Double getPenaltiesDue() {
        return penaltiesDue;
    }

    public void setPenaltiesDue(Double penaltiesDue) {
        this.penaltiesDue = penaltiesDue;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public Date getDisbursedOn() {
        return disbursedOn;
    }

    public void setDisbursedOn(Date disbursedOn) {
        this.disbursedOn = disbursedOn;
    }

    public Date getMaturedOn() {
        return maturedOn;
    }

    public void setMaturedOn(Date maturedOn) {
        this.maturedOn = maturedOn;
    }

    public Double getInterestOutstanding() {
        return interestOutstanding;
    }

    public void setInterestOutstanding(Double interestOutstanding) {
        this.interestOutstanding = interestOutstanding;
    }
}
