/*Created by Sinatra Gunda
  At 12:14 PM on 2/24/2020 */

package com.wese.weseaddons.crb.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.ACCOUNT_OWNER;
import com.wese.weseaddons.crb.enumerations.ACCOUNT_REPAYMENT_TERM;
import com.wese.weseaddons.crb.enumerations.ACCOUNT_STATUS;
import com.wese.weseaddons.crb.enumerations.CREDIT_ACCOUNT_TYPE;

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
        "companyRegNo",
        "companyRegistrationDate",
        "country",
        "currencyType",
        "currentBalance",
        "dateAccountOpened",
        "dateAccountUpdated",
        "dateClosed",
        "daysInArrears",
        "delinquencyDate",
        "disbursedAmount",
        "district",
        "firstPaymentDate",
        "gracePeriod",
        "industry",
        "installmentsInArrears",
        "institution",
        "interestType",
        "lastPaymentAmount",
        "lastPaymentDate",
        "location",
        "maturityDate",
        "numberOfJointLoanParticipants",
        "openingBalance",
        "overdraftType",
        "province",
        "telephone1",
        "termsDuration",
        "town",
        "tradingName",
        "typeOfCompany"
})
public class CorporateCreditInformationRecord{

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
    private String bozClassification;
    @JsonProperty("companyRegNo")
    private String companyRegNo;
    @JsonProperty("companyRegistrationDate")
    private String companyRegistrationDate;
    @JsonProperty("country")
    private String country;
    @JsonProperty("currencyType")
    private String currencyType;
    @JsonProperty("currentBalance")
    private String currentBalance;
    @JsonProperty("dateAccountOpened")
    private String dateAccountOpened;
    @JsonProperty("dateAccountUpdated")
    private String dateAccountUpdated;
    @JsonProperty("dateClosed")
    private String dateClosed;
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
    @JsonProperty("gracePeriod")
    private String gracePeriod;
    @JsonProperty("industry")
    private String industry;
    @JsonProperty("installmentsInArrears")
    private String installmentsInArrears;
    @JsonProperty("institution")
    private String institution;
    @JsonProperty("interestType")
    private String interestType;
    @JsonProperty("lastPaymentAmount")
    private String lastPaymentAmount;
    @JsonProperty("lastPaymentDate")
    private String lastPaymentDate;
    @JsonProperty("location")
    private String location;
    @JsonProperty("maturityDate")
    private String maturityDate;
    @JsonProperty("numberOfJointLoanParticipants")
    private String numberOfJointLoanParticipants;
    @JsonProperty("openingBalance")
    private String openingBalance;
    @JsonProperty("overdraftType")
    private String overdraftType;
    @JsonProperty("province")
    private String province;
    @JsonProperty("telephone1")
    private String telephone1;
    @JsonProperty("termsDuration")
    private String termsDuration;
    @JsonProperty("town")
    private String town;
    @JsonProperty("tradingName")
    private String tradingName;
    @JsonProperty("typeOfCompany")
    private String typeOfCompany;

    /**
     * No args constructor for use in serialization
     *
     */
    public CorporateCreditInformationRecord() {
    }

    /**
     *
     * @param currencyType
     * @param country
     * @param gracePeriod
     * @param accountStatusChangeDate
     * @param approvalDate
     * @param companyRegNo
     * @param dateAccountUpdated
     * @param interestType
     * @param firstPaymentDate
     * @param industry
     * @param telephone1
     * @param dateClosed
     * @param delinquencyDate
     * @param daysInArrears
     * @param accountStatus
     * @param overdraftType
     * @param institution
     * @param numberOfJointLoanParticipants
     * @param province
     * @param termsDuration
     * @param maturityDate
     * @param lastPaymentDate
     * @param lastPaymentAmount
     * @param approvedAmount
     * @param openingBalance
     * @param amountPaidToDate
     * @param disbursedAmount
     * @param typeOfCompany
     * @param companyRegistrationDate
     * @param tradingName
     * @param availableCredit
     * @param accountRepaymentTerm
     * @param town
     * @param accountType
     * @param currentBalance
     * @param accountOwner
     * @param accountNumber
     * @param installmentsInArrears
     * @param actualPaymentAmount
     * @param bozClassification
     * @param district
     * @param dateAccountOpened
     * @param location
     * @param amountPastDue
     */
    public CorporateCreditInformationRecord(String accountNumber, ACCOUNT_OWNER accountOwner, ACCOUNT_REPAYMENT_TERM accountRepaymentTerm, ACCOUNT_STATUS accountStatus, String accountStatusChangeDate, CREDIT_ACCOUNT_TYPE accountType, String actualPaymentAmount, String amountPaidToDate, String amountPastDue, String approvalDate, String approvedAmount, String availableCredit, String bozClassification, String companyRegNo, String companyRegistrationDate, String country, String currencyType, String currentBalance, String dateAccountOpened, String dateAccountUpdated, String dateClosed, String daysInArrears, String delinquencyDate, String disbursedAmount, String district, String firstPaymentDate, String gracePeriod, String industry, String installmentsInArrears, String institution, String interestType, String lastPaymentAmount, String lastPaymentDate, String location, String maturityDate, String numberOfJointLoanParticipants, String openingBalance, String overdraftType, String province, String telephone1, String termsDuration, String town, String tradingName, String typeOfCompany) {
        super();
        this.accountNumber = accountNumber;
        this.accountOwner = accountOwner;
        this.accountRepaymentTerm = accountRepaymentTerm;
        this.accountStatus = accountStatus;
        this.accountStatusChangeDate = accountStatusChangeDate;
        this.accountType = accountType;
        this.actualPaymentAmount = actualPaymentAmount;
        this.amountPaidToDate = amountPaidToDate;
        this.amountPastDue = amountPastDue;
        this.approvalDate = approvalDate;
        this.approvedAmount = approvedAmount;
        this.availableCredit = availableCredit;
        this.bozClassification = bozClassification;
        this.companyRegNo = companyRegNo;
        this.companyRegistrationDate = companyRegistrationDate;
        this.country = country;
        this.currencyType = currencyType;
        this.currentBalance = currentBalance;
        this.dateAccountOpened = dateAccountOpened;
        this.dateAccountUpdated = dateAccountUpdated;
        this.dateClosed = dateClosed;
        this.daysInArrears = daysInArrears;
        this.delinquencyDate = delinquencyDate;
        this.disbursedAmount = disbursedAmount;
        this.district = district;
        this.firstPaymentDate = firstPaymentDate;
        this.gracePeriod = gracePeriod;
        this.industry = industry;
        this.installmentsInArrears = installmentsInArrears;
        this.institution = institution;
        this.interestType = interestType;
        this.lastPaymentAmount = lastPaymentAmount;
        this.lastPaymentDate = lastPaymentDate;
        this.location = location;
        this.maturityDate = maturityDate;
        this.numberOfJointLoanParticipants = numberOfJointLoanParticipants;
        this.openingBalance = openingBalance;
        this.overdraftType = overdraftType;
        this.province = province;
        this.telephone1 = telephone1;
        this.termsDuration = termsDuration;
        this.town = town;
        this.tradingName = tradingName;
        this.typeOfCompany = typeOfCompany;
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
    public String getAccountRepaymentTerm() {
        return accountRepaymentTerm.name();
    }

    @JsonProperty("accountRepaymentTerm")
    public void setAccountRepaymentTerm(ACCOUNT_REPAYMENT_TERM accountRepaymentTerm) {
        this.accountRepaymentTerm = accountRepaymentTerm;
    }

    @JsonProperty("accountStatus")
    public String getAccountStatus() {
        return accountStatus.name();
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
    public String getAccountType() {
        return accountType.name();
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
        return bozClassification;
    }

    @JsonProperty("bozClassification")
    public void setBozClassification(String bozClassification) {
        this.bozClassification = bozClassification;
    }

    @JsonProperty("companyRegNo")
    public String getCompanyRegNo() {
        return companyRegNo;
    }

    @JsonProperty("companyRegNo")
    public void setCompanyRegNo(String companyRegNo) {
        this.companyRegNo = companyRegNo;
    }

    @JsonProperty("companyRegistrationDate")
    public String getCompanyRegistrationDate() {
        return companyRegistrationDate;
    }

    @JsonProperty("companyRegistrationDate")
    public void setCompanyRegistrationDate(String companyRegistrationDate) {
        this.companyRegistrationDate = companyRegistrationDate;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("currencyType")
    public String getCurrencyType() {
        return currencyType;
    }

    @JsonProperty("currencyType")
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @JsonProperty("currentBalance")
    public String getCurrentBalance() {
        return currentBalance;
    }

    @JsonProperty("currentBalance")
    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    @JsonProperty("dateAccountOpened")
    public String getDateAccountOpened() {
        return dateAccountOpened;
    }

    @JsonProperty("dateAccountOpened")
    public void setDateAccountOpened(String dateAccountOpened) {
        this.dateAccountOpened = dateAccountOpened;
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

    @JsonProperty("gracePeriod")
    public String getGracePeriod() {
        return gracePeriod;
    }

    @JsonProperty("gracePeriod")
    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    @JsonProperty("industry")
    public String getIndustry() {
        return industry;
    }

    @JsonProperty("industry")
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @JsonProperty("installmentsInArrears")
    public String getInstallmentsInArrears() {
        return installmentsInArrears;
    }

    @JsonProperty("installmentsInArrears")
    public void setInstallmentsInArrears(String installmentsInArrears) {
        this.installmentsInArrears = installmentsInArrears;
    }

    @JsonProperty("institution")
    public String getInstitution() {
        return institution;
    }

    @JsonProperty("institution")
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @JsonProperty("interestType")
    public String getInterestType() {
        return interestType;
    }

    @JsonProperty("interestType")
    public void setInterestType(String interestType) {
        this.interestType = interestType;
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

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("maturityDate")
    public String getMaturityDate() {
        return maturityDate;
    }

    @JsonProperty("maturityDate")
    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
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

    @JsonProperty("overdraftType")
    public String getOverdraftType() {
        return overdraftType;
    }

    @JsonProperty("overdraftType")
    public void setOverdraftType(String overdraftType) {
        this.overdraftType = overdraftType;
    }

    @JsonProperty("province")
    public String getProvince() {
        return province;
    }

    @JsonProperty("province")
    public void setProvince(String province) {
        this.province = province;
    }

    @JsonProperty("telephone1")
    public String getTelephone1() {
        return telephone1;
    }

    @JsonProperty("telephone1")
    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
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

    @JsonProperty("tradingName")
    public String getTradingName() {
        return tradingName;
    }

    @JsonProperty("tradingName")
    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    @JsonProperty("typeOfCompany")
    public String getTypeOfCompany() {
        return typeOfCompany;
    }

    @JsonProperty("typeOfCompany")
    public void setTypeOfCompany(String typeOfCompany) {
        this.typeOfCompany = typeOfCompany;
    }

}
