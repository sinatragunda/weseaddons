package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"accountProductType",
"accountStatus",
"classification",
"currency",
"customerRef",
"dateClosed",
"daysInArrears",
"disbursementDate",
"dob",
"documentNumber",
"documentType",
"foreName1",
"foreName2",
"foreName3",
"gender",
"lastPaymentDate",
"loanAccount",
"loanBalance",
"maturityDate",
"mobileNumber",
"principalAmount",
"surName"
})
public class MobileLoansInformation {

@JsonProperty("accountProductType")
private String accountProductType;
@JsonProperty("accountStatus")
private String accountStatus;
@JsonProperty("classification")
private String classification;
@JsonProperty("currency")
private String currency;
@JsonProperty("customerRef")
private String customerRef;
@JsonProperty("dateClosed")
private String dateClosed;
@JsonProperty("daysInArrears")
private Integer daysInArrears;
@JsonProperty("disbursementDate")
private String disbursementDate;
@JsonProperty("dob")
private String dob;
@JsonProperty("documentNumber")
private String documentNumber;
@JsonProperty("documentType")
private String documentType;
@JsonProperty("foreName1")
private String foreName1;
@JsonProperty("foreName2")
private String foreName2;
@JsonProperty("foreName3")
private String foreName3;
@JsonProperty("gender")
private String gender;
@JsonProperty("lastPaymentDate")
private String lastPaymentDate;
@JsonProperty("loanAccount")
private String loanAccount;
@JsonProperty("loanBalance")
private Integer loanBalance;
@JsonProperty("maturityDate")
private String maturityDate;
@JsonProperty("mobileNumber")
private String mobileNumber;
@JsonProperty("principalAmount")
private Integer principalAmount;
@JsonProperty("surName")
private String surName;

/**
* No args constructor for use in serialization
*
*/
public MobileLoansInformation() {
}

/**
*
* @param gender
* @param surName
* @param documentType
* @param documentNumber
* @param mobileNumber
* @param classification
* @param dateClosed
* @param daysInArrears
* @param principalAmount
* @param loanBalance
* @param accountStatus
* @param accountProductType
* @param foreName3
* @param loanAccount
* @param foreName2
* @param customerRef
* @param foreName1
* @param dob
* @param maturityDate
* @param lastPaymentDate
* @param disbursementDate
* @param currency
*/
public MobileLoansInformation(String accountProductType, String accountStatus, String classification, String currency, String customerRef, String dateClosed, Integer daysInArrears, String disbursementDate, String dob, String documentNumber, String documentType, String foreName1, String foreName2, String foreName3, String gender, String lastPaymentDate, String loanAccount, Integer loanBalance, String maturityDate, String mobileNumber, Integer principalAmount, String surName) {
super();
this.accountProductType = accountProductType;
this.accountStatus = accountStatus;
this.classification = classification;
this.currency = currency;
this.customerRef = customerRef;
this.dateClosed = dateClosed;
this.daysInArrears = daysInArrears;
this.disbursementDate = disbursementDate;
this.dob = dob;
this.documentNumber = documentNumber;
this.documentType = documentType;
this.foreName1 = foreName1;
this.foreName2 = foreName2;
this.foreName3 = foreName3;
this.gender = gender;
this.lastPaymentDate = lastPaymentDate;
this.loanAccount = loanAccount;
this.loanBalance = loanBalance;
this.maturityDate = maturityDate;
this.mobileNumber = mobileNumber;
this.principalAmount = principalAmount;
this.surName = surName;
}

@JsonProperty("accountProductType")
public String getAccountProductType() {
return accountProductType;
}

@JsonProperty("accountProductType")
public void setAccountProductType(String accountProductType) {
this.accountProductType = accountProductType;
}

@JsonProperty("accountStatus")
public String getAccountStatus() {
return accountStatus;
}

@JsonProperty("accountStatus")
public void setAccountStatus(String accountStatus) {
this.accountStatus = accountStatus;
}

@JsonProperty("classification")
public String getClassification() {
return classification;
}

@JsonProperty("classification")
public void setClassification(String classification) {
this.classification = classification;
}

@JsonProperty("currency")
public String getCurrency() {
return currency;
}

@JsonProperty("currency")
public void setCurrency(String currency) {
this.currency = currency;
}

@JsonProperty("customerRef")
public String getCustomerRef() {
return customerRef;
}

@JsonProperty("customerRef")
public void setCustomerRef(String customerRef) {
this.customerRef = customerRef;
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
public Integer getDaysInArrears() {
return daysInArrears;
}

@JsonProperty("daysInArrears")
public void setDaysInArrears(Integer daysInArrears) {
this.daysInArrears = daysInArrears;
}

@JsonProperty("disbursementDate")
public String getDisbursementDate() {
return disbursementDate;
}

@JsonProperty("disbursementDate")
public void setDisbursementDate(String disbursementDate) {
this.disbursementDate = disbursementDate;
}

@JsonProperty("dob")
public String getDob() {
return dob;
}

@JsonProperty("dob")
public void setDob(String dob) {
this.dob = dob;
}

@JsonProperty("documentNumber")
public String getDocumentNumber() {
return documentNumber;
}

@JsonProperty("documentNumber")
public void setDocumentNumber(String documentNumber) {
this.documentNumber = documentNumber;
}

@JsonProperty("documentType")
public String getDocumentType() {
return documentType;
}

@JsonProperty("documentType")
public void setDocumentType(String documentType) {
this.documentType = documentType;
}

@JsonProperty("foreName1")
public String getForeName1() {
return foreName1;
}

@JsonProperty("foreName1")
public void setForeName1(String foreName1) {
this.foreName1 = foreName1;
}

@JsonProperty("foreName2")
public String getForeName2() {
return foreName2;
}

@JsonProperty("foreName2")
public void setForeName2(String foreName2) {
this.foreName2 = foreName2;
}

@JsonProperty("foreName3")
public String getForeName3() {
return foreName3;
}

@JsonProperty("foreName3")
public void setForeName3(String foreName3) {
this.foreName3 = foreName3;
}

@JsonProperty("gender")
public String getGender() {
return gender;
}

@JsonProperty("gender")
public void setGender(String gender) {
this.gender = gender;
}

@JsonProperty("lastPaymentDate")
public String getLastPaymentDate() {
return lastPaymentDate;
}

@JsonProperty("lastPaymentDate")
public void setLastPaymentDate(String lastPaymentDate) {
this.lastPaymentDate = lastPaymentDate;
}

@JsonProperty("loanAccount")
public String getLoanAccount() {
return loanAccount;
}

@JsonProperty("loanAccount")
public void setLoanAccount(String loanAccount) {
this.loanAccount = loanAccount;
}

@JsonProperty("loanBalance")
public Integer getLoanBalance() {
return loanBalance;
}

@JsonProperty("loanBalance")
public void setLoanBalance(Integer loanBalance) {
this.loanBalance = loanBalance;
}

@JsonProperty("maturityDate")
public String getMaturityDate() {
return maturityDate;
}

@JsonProperty("maturityDate")
public void setMaturityDate(String maturityDate) {
this.maturityDate = maturityDate;
}

@JsonProperty("mobileNumber")
public String getMobileNumber() {
return mobileNumber;
}

@JsonProperty("mobileNumber")
public void setMobileNumber(String mobileNumber) {
this.mobileNumber = mobileNumber;
}

@JsonProperty("principalAmount")
public Integer getPrincipalAmount() {
return principalAmount;
}

@JsonProperty("principalAmount")
public void setPrincipalAmount(Integer principalAmount) {
this.principalAmount = principalAmount;
}

@JsonProperty("surName")
public String getSurName() {
return surName;
}

@JsonProperty("surName")
public void setSurName(String surName) {
this.surName = surName;
}

}