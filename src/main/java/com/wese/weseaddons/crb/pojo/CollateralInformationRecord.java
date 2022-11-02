
package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"accountNumber",
"accountType",
"collateralCurrencyType",
"collateralDateofDischarge",
"collateralExpiryDate",
"collateralForcedSaleDate",
"collateralForcedSaleValue",
"collateralLastValuationAmount",
"collateralLastValuationDate",
"collateralReferenceNumber",
"collateralType"
})
public class CollateralInformationRecord {

@JsonProperty("accountNumber")
private String accountNumber;
@JsonProperty("accountType")
private String accountType;
@JsonProperty("collateralCurrencyType")
private String collateralCurrencyType;
@JsonProperty("collateralDateofDischarge")
private String collateralDateofDischarge;
@JsonProperty("collateralExpiryDate")
private String collateralExpiryDate;
@JsonProperty("collateralForcedSaleDate")
private String collateralForcedSaleDate;
@JsonProperty("collateralForcedSaleValue")
private String collateralForcedSaleValue;
@JsonProperty("collateralLastValuationAmount")
private String collateralLastValuationAmount;
@JsonProperty("collateralLastValuationDate")
private String collateralLastValuationDate;
@JsonProperty("collateralReferenceNumber")
private String collateralReferenceNumber;
@JsonProperty("collateralType")
private String collateralType;

/**
* No args constructor for use in serialization
*
*/
public CollateralInformationRecord() {
}

/**
*
* @param collateralForcedSaleValue
* @param collateralLastValuationAmount
* @param collateralDateofDischarge
* @param collateralType
* @param accountType
* @param collateralLastValuationDate
* @param collateralForcedSaleDate
* @param collateralCurrencyType
* @param collateralReferenceNumber
* @param accountNumber
* @param collateralExpiryDate
*/
public CollateralInformationRecord(String accountNumber, String accountType, String collateralCurrencyType, String collateralDateofDischarge, String collateralExpiryDate, String collateralForcedSaleDate, String collateralForcedSaleValue, String collateralLastValuationAmount, String collateralLastValuationDate, String collateralReferenceNumber, String collateralType) {
super();
this.accountNumber = accountNumber;
this.accountType = accountType;
this.collateralCurrencyType = collateralCurrencyType;
this.collateralDateofDischarge = collateralDateofDischarge;
this.collateralExpiryDate = collateralExpiryDate;
this.collateralForcedSaleDate = collateralForcedSaleDate;
this.collateralForcedSaleValue = collateralForcedSaleValue;
this.collateralLastValuationAmount = collateralLastValuationAmount;
this.collateralLastValuationDate = collateralLastValuationDate;
this.collateralReferenceNumber = collateralReferenceNumber;
this.collateralType = collateralType;
}

@JsonProperty("accountNumber")
public String getAccountNumber() {
return accountNumber;
}

@JsonProperty("accountNumber")
public void setAccountNumber(String accountNumber) {
this.accountNumber = accountNumber;
}

@JsonProperty("accountType")
public String getAccountType() {
return accountType;
}

@JsonProperty("accountType")
public void setAccountType(String accountType) {
this.accountType = accountType;
}

@JsonProperty("collateralCurrencyType")
public String getCollateralCurrencyType() {
return collateralCurrencyType;
}

@JsonProperty("collateralCurrencyType")
public void setCollateralCurrencyType(String collateralCurrencyType) {
this.collateralCurrencyType = collateralCurrencyType;
}

@JsonProperty("collateralDateofDischarge")
public String getCollateralDateofDischarge() {
return collateralDateofDischarge;
}

@JsonProperty("collateralDateofDischarge")
public void setCollateralDateofDischarge(String collateralDateofDischarge) {
this.collateralDateofDischarge = collateralDateofDischarge;
}

@JsonProperty("collateralExpiryDate")
public String getCollateralExpiryDate() {
return collateralExpiryDate;
}

@JsonProperty("collateralExpiryDate")
public void setCollateralExpiryDate(String collateralExpiryDate) {
this.collateralExpiryDate = collateralExpiryDate;
}

@JsonProperty("collateralForcedSaleDate")
public String getCollateralForcedSaleDate() {
return collateralForcedSaleDate;
}

@JsonProperty("collateralForcedSaleDate")
public void setCollateralForcedSaleDate(String collateralForcedSaleDate) {
this.collateralForcedSaleDate = collateralForcedSaleDate;
}

@JsonProperty("collateralForcedSaleValue")
public String getCollateralForcedSaleValue() {
return collateralForcedSaleValue;
}

@JsonProperty("collateralForcedSaleValue")
public void setCollateralForcedSaleValue(String collateralForcedSaleValue) {
this.collateralForcedSaleValue = collateralForcedSaleValue;
}

@JsonProperty("collateralLastValuationAmount")
public String getCollateralLastValuationAmount() {
return collateralLastValuationAmount;
}

@JsonProperty("collateralLastValuationAmount")
public void setCollateralLastValuationAmount(String collateralLastValuationAmount) {
this.collateralLastValuationAmount = collateralLastValuationAmount;
}

@JsonProperty("collateralLastValuationDate")
public String getCollateralLastValuationDate() {
return collateralLastValuationDate;
}

@JsonProperty("collateralLastValuationDate")
public void setCollateralLastValuationDate(String collateralLastValuationDate) {
this.collateralLastValuationDate = collateralLastValuationDate;
}

@JsonProperty("collateralReferenceNumber")
public String getCollateralReferenceNumber() {
return collateralReferenceNumber;
}

@JsonProperty("collateralReferenceNumber")
public void setCollateralReferenceNumber(String collateralReferenceNumber) {
this.collateralReferenceNumber = collateralReferenceNumber;
}

@JsonProperty("collateralType")
public String getCollateralType() {
return collateralType;
}

@JsonProperty("collateralType")
public void setCollateralType(String collateralType) {
this.collateralType = collateralType;
}

}