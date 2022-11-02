package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "accountType",
        "alienId",
        "chequeAmount",
        "chequeDate",
        "chequeNumber",
        "chequeReturnedDate",
        "companyRegNo",
        "companyRegistrationDate",
        "country",
        "currency",
        "dateOfBirth",
        "foreName1",
        "foreName2",
        "foreName3",
        "institutionName",
        "nationality",
        "nrcNumber",
        "passportNumber",
        "placeofBirth",
        "postalAddressNumber",
        "postalCode",
        "returnedChequeReason",
        "surName",
        "town",
        "tradingName"
})
public class BouncedChequeInformationRecord {

    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("accountType")
    private String accountType;
    @JsonProperty("alienId")
    private String alienId;
    @JsonProperty("chequeAmount")
    private String chequeAmount;
    @JsonProperty("chequeDate")
    private String chequeDate;
    @JsonProperty("chequeNumber")
    private String chequeNumber;
    @JsonProperty("chequeReturnedDate")
    private String chequeReturnedDate;
    @JsonProperty("companyRegNo")
    private String companyRegNo;
    @JsonProperty("companyRegistrationDate")
    private String companyRegistrationDate;
    @JsonProperty("country")
    private String country;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("foreName1")
    private String foreName1;
    @JsonProperty("foreName2")
    private String foreName2;
    @JsonProperty("foreName3")
    private String foreName3;
    @JsonProperty("institutionName")
    private String institutionName;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("nrcNumber")
    private String nrcNumber;
    @JsonProperty("passportNumber")
    private String passportNumber;
    @JsonProperty("placeofBirth")
    private String placeofBirth;
    @JsonProperty("postalAddressNumber")
    private String postalAddressNumber;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("returnedChequeReason")
    private String returnedChequeReason;
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("town")
    private String town;
    @JsonProperty("tradingName")
    private String tradingName;

    /**
     * No args constructor for use in serialization
     *
     */
    public BouncedChequeInformationRecord() {
    }

    /**
     *
     * @param country
     * @param surName
     * @param returnedChequeReason
     * @param companyRegNo
     * @param postalCode
     * @param placeofBirth
     * @param chequeNumber
     * @param foreName3
     * @param foreName2
     * @param foreName1
     * @param currency
     * @param companyRegistrationDate
     * @param passportNumber
     * @param tradingName
     * @param chequeReturnedDate
     * @param town
     * @param accountType
     * @param dateOfBirth
     * @param accountNumber
     * @param chequeDate
     * @param chequeAmount
     * @param nationality
     * @param institutionName
     * @param nrcNumber
     * @param alienId
     * @param postalAddressNumber
     */
    public BouncedChequeInformationRecord(String accountNumber, String accountType, String alienId, String chequeAmount, String chequeDate, String chequeNumber, String chequeReturnedDate, String companyRegNo, String companyRegistrationDate, String country, String currency, String dateOfBirth, String foreName1, String foreName2, String foreName3, String institutionName, String nationality, String nrcNumber, String passportNumber, String placeofBirth, String postalAddressNumber, String postalCode, String returnedChequeReason, String surName, String town, String tradingName) {
        super();
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.alienId = alienId;
        this.chequeAmount = chequeAmount;
        this.chequeDate = chequeDate;
        this.chequeNumber = chequeNumber;
        this.chequeReturnedDate = chequeReturnedDate;
        this.companyRegNo = companyRegNo;
        this.companyRegistrationDate = companyRegistrationDate;
        this.country = country;
        this.currency = currency;
        this.dateOfBirth = dateOfBirth;
        this.foreName1 = foreName1;
        this.foreName2 = foreName2;
        this.foreName3 = foreName3;
        this.institutionName = institutionName;
        this.nationality = nationality;
        this.nrcNumber = nrcNumber;
        this.passportNumber = passportNumber;
        this.placeofBirth = placeofBirth;
        this.postalAddressNumber = postalAddressNumber;
        this.postalCode = postalCode;
        this.returnedChequeReason = returnedChequeReason;
        this.surName = surName;
        this.town = town;
        this.tradingName = tradingName;
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

    @JsonProperty("alienId")
    public String getAlienId() {
        return alienId;
    }

    @JsonProperty("alienId")
    public void setAlienId(String alienId) {
        this.alienId = alienId;
    }

    @JsonProperty("chequeAmount")
    public String getChequeAmount() {
        return chequeAmount;
    }

    @JsonProperty("chequeAmount")
    public void setChequeAmount(String chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    @JsonProperty("chequeDate")
    public String getChequeDate() {
        return chequeDate;
    }

    @JsonProperty("chequeDate")
    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    @JsonProperty("chequeNumber")
    public String getChequeNumber() {
        return chequeNumber;
    }

    @JsonProperty("chequeNumber")
    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    @JsonProperty("chequeReturnedDate")
    public String getChequeReturnedDate() {
        return chequeReturnedDate;
    }

    @JsonProperty("chequeReturnedDate")
    public void setChequeReturnedDate(String chequeReturnedDate) {
        this.chequeReturnedDate = chequeReturnedDate;
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

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("dateOfBirth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    @JsonProperty("institutionName")
    public String getInstitutionName() {
        return institutionName;
    }

    @JsonProperty("institutionName")
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
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

    @JsonProperty("passportNumber")
    public String getPassportNumber() {
        return passportNumber;
    }

    @JsonProperty("passportNumber")
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @JsonProperty("placeofBirth")
    public String getPlaceofBirth() {
        return placeofBirth;
    }

    @JsonProperty("placeofBirth")
    public void setPlaceofBirth(String placeofBirth) {
        this.placeofBirth = placeofBirth;
    }

    @JsonProperty("postalAddressNumber")
    public String getPostalAddressNumber() {
        return postalAddressNumber;
    }

    @JsonProperty("postalAddressNumber")
    public void setPostalAddressNumber(String postalAddressNumber) {
        this.postalAddressNumber = postalAddressNumber;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("returnedChequeReason")
    public String getReturnedChequeReason() {
        return returnedChequeReason;
    }

    @JsonProperty("returnedChequeReason")
    public void setReturnedChequeReason(String returnedChequeReason) {
        this.returnedChequeReason = returnedChequeReason;
    }

    @JsonProperty("surName")
    public String getSurName() {
        return surName;
    }

    @JsonProperty("surName")
    public void setSurName(String surName) {
        this.surName = surName;
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

}