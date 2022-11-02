/*Created by Sinatra Gunda
  At 10:46 AM on 2/24/2020 */

package com.wese.weseaddons.crb.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.*;
import com.wese.weseaddons.crb.helper.LoanArrearsHelper;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.LoanRepaymentSchedule;


import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "accountOwner",
        "accountRepaymentTerm",
        "accountStatus",
        "accountStatusChangeDate",
        "accountType",
        "actualPaymentAmount",
        "amountPaidToDate",
        "amountPastDue",
        "approvalDate",
        "approvedAmount",
        "availableCredit",
        "bozClassification",
        "country",
        "currentBalance",
        "dateAccountUpdated",
        "dateClosed",
        "dateLoanAccountOpened",
        "dateOfBirth",
        "daysInArrears",
        "delinquencyDate",
        "disbursedAmount",
        "district",
        "firstPaymentDate",
        "foreName1",
        "gender",
        "groupName",
        "groupNumber",
        "installmentsInArrears",
        "lastPaymentAmount",
        "lastPaymentDate",
        "maturityDate",
        "mobileTelephone",
        "nationality",
        "nrcNumber",
        "numberOfJointLoanParticipants",
        "openingBalance",
        "passportNumber",
        "province",
        "reportingCurrency",
        "scheduledPaymentAmount",
        "surName",
        "termsDuration",
        "town"
})
public class ConsumerCreditInformationRecord{

    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("accountOwner")
    private ACCOUNT_OWNER accountOwner;
    @JsonProperty("accountRepaymentTerm")
    private ACCOUNT_REPAYMENT_TERM accountRepaymentTerm;
    @JsonProperty("accountStatus")
    private ACCOUNT_STATUS accountStatus;
    @JsonProperty("accountStatusChangeDate")
    private String accountStatusChangeDate;
    @JsonProperty("accountType")
    private CREDIT_ACCOUNT_TYPE accountType;
    @JsonProperty("actualPaymentAmount")
    private String actualPaymentAmount;
    @JsonProperty("amountPaidToDate")
    private String amountPaidToDate;
    @JsonProperty("amountPastDue")
    private String amountPastDue;
    @JsonProperty("approvalDate")
    private String approvalDate;
    @JsonProperty("approvedAmount")
    private String approvedAmount;
    @JsonProperty("availableCredit")
    private String availableCredit;
    @JsonProperty("bozClassification")
    private BOZ_CLASSIFICATION bozClassification;
    @JsonProperty("country")
    private String country;
    @JsonProperty("currentBalance")
    private String currentBalance;
    @JsonProperty("dateAccountUpdated")
    private String dateAccountUpdated;
    @JsonProperty("dateClosed")
    private String dateClosed;
    @JsonProperty("dateLoanAccountOpened")
    private String dateLoanAccountOpened;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("daysInArrears")
    private String daysInArrears;
    @JsonProperty("delinquencyDate")
    private String delinquencyDate;
    @JsonProperty("disbursedAmount")
    private String disbursedAmount;
    @JsonProperty("district")
    private String district;
    @JsonProperty("firstPaymentDate")
    private String firstPaymentDate;
    @JsonProperty("foreName1")
    private String foreName1;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("groupNumber")
    private String groupNumber;
    @JsonProperty("installmentsInArrears")
    private String installmentsInArrears;
    @JsonProperty("lastPaymentAmount")
    private String lastPaymentAmount;
    @JsonProperty("lastPaymentDate")
    private String lastPaymentDate;
    @JsonProperty("maturityDate")
    private String maturityDate;
    @JsonProperty("mobileTelephone")
    private String mobileTelephone;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("nrcNumber")
    private String nrcNumber;
    @JsonProperty("numberOfJointLoanParticipants")
    private String numberOfJointLoanParticipants;
    @JsonProperty("openingBalance")
    private String openingBalance;
    @JsonProperty("passportNumber")
    private String passportNumber;
    @JsonProperty("province")
    private String province;
    @JsonProperty("reportingCurrency")
    private String reportingCurrency;
    @JsonProperty("scheduledPaymentAmount")
    private String scheduledPaymentAmount;
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("termsDuration")
    private String termsDuration;
    @JsonProperty("town")
    private String town;

    /**
     * No args constructor for use in serialization
     *
     */
    public ConsumerCreditInformationRecord() {
    }

    /**
     *
     * @param dateAccountUpdated
     * @param dateClosed
     * @param delinquencyDate
     * @param daysInArrears
     * @param accountStatus
     * @param termsDuration
     * @param lastPaymentDate
     * @param lastPaymentAmount
     * @param openingBalance
     * @param availableCredit
     * @param accountRepaymentTerm
     * @param town
     * @param currentBalance
     * @param dateLoanAccountOpened
     * @param installmentsInArrears
     * @param scheduledPaymentAmount
     * @param actualPaymentAmount
     * @param bozClassification

     */
    public ConsumerCreditInformationRecord(Loan loan, Client client) {
        super();

        int index = loan.getLoanRepaymentScheduleList().size();
        
        System.err.print("Size of list is "+index);
        
        if(index==0) {
            return ;
        }

        LoanRepaymentSchedule loanRepaymentSchedule = loan.getLoanRepaymentScheduleList().get(index-1);

        this.accountNumber = loan.getAccountNo() ;
        this.accountOwner = ACCOUNT_OWNER.O;
        this.accountRepaymentTerm = loan.getAccountRepaymentTerm();
        this.accountStatus = loan.getAccountStatus();

        Date accountStatusChangeDate = TimeHelper.dateNow();
        String accountStatusChangeDateString = TimeHelper.dateToStringWithFormat(accountStatusChangeDate ,Constants.crbDateFormat);

        this.accountStatusChangeDate = accountStatusChangeDateString;
        this.accountType = CREDIT_ACCOUNT_TYPE.C ;//// This is credit line

        this.town = client.getOfficeName();
        this.actualPaymentAmount = String.valueOf(loan.getTransactionPayment());
        this.amountPaidToDate = String.valueOf(loan.getTotalRepayment());
        this.amountPastDue = String.valueOf(loan.getTotalOverdue());
        this.approvalDate = TimeHelper.dateToStringWithFormat(loan.getApprovedOnDate() , Constants.crbDateFormat);
        this.approvedAmount = String.valueOf(loan.getPrincipal());
        this.availableCredit = "0";
        this.bozClassification = BOZ_CLASSIFICATION.P;
        this.country = "Zambia";
        this.currentBalance = String.valueOf(loan.getSummary().getTotalOutstanding());
        this.dateAccountUpdated = accountStatusChangeDateString;
        this.dateClosed = "Active";
        
        if(loan.isClosed()) {
            
            this.dateClosed = TimeHelper.dateToStringWithFormat(loan.getClosedOnDate(),Constants.crbDateFormat);
            
        }
        this.dateLoanAccountOpened = approvalDate.toString() ;
        this.dateOfBirth = TimeHelper.dateToStringWithFormat(client.getDateOfBirth() ,Constants.crbDateFormat);

        this.daysInArrears = String.valueOf(LoanArrearsHelper.daysInArreas(loan.getId() ,client.getTenantIdentifier()));
        
        this.delinquencyDate = delinquencyDate;
        this.disbursedAmount = String.valueOf(loan.getPrincipalDisbursed());
        this.district = town;
        this.firstPaymentDate = TimeHelper.dateToStringWithFormat(loan.getLoanRepaymentScheduleList().get(index-1).getObligationsMetOn() ,Constants.crbDateFormat);
        this.foreName1 = client.getFirstname();
        this.gender = client.getGender();
        this.groupName = "individual";
        this.groupNumber = "1";
        this.installmentsInArrears = "0";
        this.lastPaymentAmount = String.valueOf(loan.getLoanRepaymentScheduleList().get(1).getTotalPaidForPeriod());
        this.lastPaymentDate = TimeHelper.dateToStringWithFormat(loan.getLoanRepaymentScheduleList().get(1).getObligationsMetOn() ,Constants.crbDateFormat);
        this.maturityDate = TimeHelper.dateToStringWithFormat(loan.getExpectedMaturityDate() , Constants.crbDateFormat);
        this.mobileTelephone = client.getMobileNumber();
        this.nationality = "Zambian";
        this.nrcNumber = client.getNrcNumber();
        this.numberOfJointLoanParticipants = "1";
        this.openingBalance = "0";
        this.passportNumber = nrcNumber;
        this.province = town;
        this.reportingCurrency = loan.getCurrency().getCode();
        this.scheduledPaymentAmount = String.valueOf(loanRepaymentSchedule.getTotalDueForPeriod());
        this.surName = client.getSurname();
        this.termsDuration = "0";
    }

    @JsonProperty("accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("accountNumber")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("accountOwner")
    public String getAccountOwner() {
        return accountOwner.name();
    }

    @JsonProperty("accountOwner")
    public void setAccountOwner(ACCOUNT_OWNER accountOwner) {
        this.accountOwner = accountOwner;
    }

    @JsonProperty("accountRepaymentTerm")
    public ACCOUNT_REPAYMENT_TERM getAccountRepaymentTerm() {
        return accountRepaymentTerm;
    }

    @JsonProperty("accountRepaymentTerm")
    public void setAccountRepaymentTerm(ACCOUNT_REPAYMENT_TERM accountRepaymentTerm) {
        this.accountRepaymentTerm = accountRepaymentTerm;
    }

    @JsonProperty("accountStatus")
    public ACCOUNT_STATUS getAccountStatus() {
        return accountStatus;
    }

    @JsonProperty("accountStatus")
    public void setAccountStatus(ACCOUNT_STATUS accountStatus) {
        this.accountStatus = accountStatus;
    }

    @JsonProperty("accountStatusChangeDate")
    public String getAccountStatusChangeDate() {
        return accountStatusChangeDate;
    }

    @JsonProperty("accountStatusChangeDate")
    public void setAccountStatusChangeDate(String accountStatusChangeDate) {
        this.accountStatusChangeDate = accountStatusChangeDate;
    }

    @JsonProperty("accountType")
    public CREDIT_ACCOUNT_TYPE getAccountType() {
        return accountType;
    }

    @JsonProperty("accountType")
    public void setAccountType(CREDIT_ACCOUNT_TYPE accountType) {
        this.accountType = accountType;
    }

    @JsonProperty("actualPaymentAmount")
    public String getActualPaymentAmount() {
        return actualPaymentAmount;
    }

    @JsonProperty("actualPaymentAmount")
    public void setActualPaymentAmount(String actualPaymentAmount) {
        this.actualPaymentAmount = actualPaymentAmount;
    }

    @JsonProperty("amountPaidToDate")
    public String getAmountPaidToDate() {
        return amountPaidToDate;
    }

    @JsonProperty("amountPaidToDate")
    public void setAmountPaidToDate(String amountPaidToDate) {
        this.amountPaidToDate = amountPaidToDate;
    }

    @JsonProperty("amountPastDue")
    public String getAmountPastDue() {
        return amountPastDue;
    }

    @JsonProperty("amountPastDue")
    public void setAmountPastDue(String amountPastDue) {
        this.amountPastDue = amountPastDue;
    }

    @JsonProperty("approvalDate")
    public String getApprovalDate() {
        return approvalDate;
    }

    @JsonProperty("approvalDate")
    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    @JsonProperty("approvedAmount")
    public String getApprovedAmount() {
        return approvedAmount;
    }

    @JsonProperty("approvedAmount")
    public void setApprovedAmount(String approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    @JsonProperty("availableCredit")
    public String getAvailableCredit() {
        return availableCredit;
    }

    @JsonProperty("availableCredit")
    public void setAvailableCredit(String availableCredit) {
        this.availableCredit = availableCredit;
    }

    @JsonProperty("bozClassification")
    public String getBozClassification() {
        return bozClassification.name();
    }

    @JsonProperty("bozClassification")
    public void setBozClassification(BOZ_CLASSIFICATION bozClassification) {
        this.bozClassification = bozClassification;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("currentBalance")
    public String getCurrentBalance() {
        return currentBalance;
    }

    @JsonProperty("currentBalance")
    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    @JsonProperty("dateAccountUpdated")
    public String getDateAccountUpdated() {
        return dateAccountUpdated;
    }

    @JsonProperty("dateAccountUpdated")
    public void setDateAccountUpdated(String dateAccountUpdated) {
        this.dateAccountUpdated = dateAccountUpdated;
    }

    @JsonProperty("dateClosed")
    public String getDateClosed() {
        return dateClosed;
    }

    @JsonProperty("dateClosed")
    public void setDateClosed(String dateClosed) {
        this.dateClosed = dateClosed;
    }

    @JsonProperty("dateLoanAccountOpened")
    public String getDateLoanAccountOpened() {
        return dateLoanAccountOpened;
    }

    @JsonProperty("dateLoanAccountOpened")
    public void setDateLoanAccountOpened(String dateLoanAccountOpened) {
        this.dateLoanAccountOpened = dateLoanAccountOpened;
    }

    @JsonProperty("dateOfBirth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("daysInArrears")
    public String getDaysInArrears() {
        return daysInArrears;
    }

    @JsonProperty("daysInArrears")
    public void setDaysInArrears(String daysInArrears) {
        this.daysInArrears = daysInArrears;
    }

    @JsonProperty("delinquencyDate")
    public String getDelinquencyDate() {
        return delinquencyDate;
    }

    @JsonProperty("delinquencyDate")
    public void setDelinquencyDate(String delinquencyDate) {
        this.delinquencyDate = delinquencyDate;
    }

    @JsonProperty("disbursedAmount")
    public String getDisbursedAmount() {
        return disbursedAmount;
    }

    @JsonProperty("disbursedAmount")
    public void setDisbursedAmount(String disbursedAmount) {
        this.disbursedAmount = disbursedAmount;
    }

    @JsonProperty("district")
    public String getDistrict() {
        return district;
    }

    @JsonProperty("district")
    public void setDistrict(String district) {
        this.district = district;
    }

    @JsonProperty("firstPaymentDate")
    public String getFirstPaymentDate() {
        return firstPaymentDate;
    }

    @JsonProperty("firstPaymentDate")
    public void setFirstPaymentDate(String firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    @JsonProperty("foreName1")
    public String getForeName1() {
        return foreName1;
    }

    @JsonProperty("foreName1")
    public void setForeName1(String foreName1) {
        this.foreName1 = foreName1;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("groupName")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("groupNumber")
    public String getGroupNumber() {
        return groupNumber;
    }

    @JsonProperty("groupNumber")
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @JsonProperty("installmentsInArrears")
    public String getInstallmentsInArrears() {
        return installmentsInArrears;
    }

    @JsonProperty("installmentsInArrears")
    public void setInstallmentsInArrears(String installmentsInArrears) {
        this.installmentsInArrears = installmentsInArrears;
    }

    @JsonProperty("lastPaymentAmount")
    public String getLastPaymentAmount() {
        return lastPaymentAmount;
    }

    @JsonProperty("lastPaymentAmount")
    public void setLastPaymentAmount(String lastPaymentAmount) {
        this.lastPaymentAmount = lastPaymentAmount;
    }

    @JsonProperty("lastPaymentDate")
    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    @JsonProperty("lastPaymentDate")
    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    @JsonProperty("maturityDate")
    public String getMaturityDate() {
        return maturityDate;
    }

    @JsonProperty("maturityDate")
    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    @JsonProperty("mobileTelephone")
    public String getMobileTelephone() {
        return mobileTelephone;
    }

    @JsonProperty("mobileTelephone")
    public void setMobileTelephone(String mobileTelephone) {
        this.mobileTelephone = mobileTelephone;
    }

    @JsonProperty("nationality")
    public String getNationality() {
        return nationality;
    }

    @JsonProperty("nationality")
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @JsonProperty("nrcNumber")
    public String getNrcNumber() {
        return nrcNumber;
    }

    @JsonProperty("nrcNumber")
    public void setNrcNumber(String nrcNumber) {
        this.nrcNumber = nrcNumber;
    }

    @JsonProperty("numberOfJointLoanParticipants")
    public String getNumberOfJointLoanParticipants() {
        return numberOfJointLoanParticipants;
    }

    @JsonProperty("numberOfJointLoanParticipants")
    public void setNumberOfJointLoanParticipants(String numberOfJointLoanParticipants) {
        this.numberOfJointLoanParticipants = numberOfJointLoanParticipants;
    }

    @JsonProperty("openingBalance")
    public String getOpeningBalance() {
        return openingBalance;
    }

    @JsonProperty("openingBalance")
    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    @JsonProperty("passportNumber")
    public String getPassportNumber() {
        return passportNumber;
    }

    @JsonProperty("passportNumber")
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @JsonProperty("province")
    public String getProvince() {
        return province;
    }

    @JsonProperty("province")
    public void setProvince(String province) {
        this.province = province;
    }

    @JsonProperty("reportingCurrency")
    public String getReportingCurrency() {
        return reportingCurrency;
    }

    @JsonProperty("reportingCurrency")
    public void setReportingCurrency(String reportingCurrency) {
        this.reportingCurrency = reportingCurrency;
    }

    @JsonProperty("scheduledPaymentAmount")
    public String getScheduledPaymentAmount() {
        return scheduledPaymentAmount;
    }

    @JsonProperty("scheduledPaymentAmount")
    public void setScheduledPaymentAmount(String scheduledPaymentAmount) {
        this.scheduledPaymentAmount = scheduledPaymentAmount;
    }

    @JsonProperty("surName")
    public String getSurName() {
        return surName;
    }

    @JsonProperty("surName")
    public void setSurName(String surName) {
        this.surName = surName;
    }

    @JsonProperty("termsDuration")
    public String getTermsDuration() {
        return termsDuration;
    }

    @JsonProperty("termsDuration")
    public void setTermsDuration(String termsDuration) {
        this.termsDuration = termsDuration;
    }

    @JsonProperty("town")
    public String getTown() {
        return town;
    }

    @JsonProperty("town")
    public void setTown(String town) {
        this.town = town;
    }

}


