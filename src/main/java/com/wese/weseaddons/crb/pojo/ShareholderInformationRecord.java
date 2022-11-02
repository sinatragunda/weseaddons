/*Created by Sinatra Gunda
  At 3:32 PM on 2/23/2020 */

package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "accountNumber",
        "alienId",
        "companyRegNo",
        "companyRegistrationDate",
        "country",
        "dateOfBirth",
        "foreName1",
        "foreName2",
        "foreName3",
        "institutionName",
        "location",
        "nationality",
        "nrcNumber",
        "numberOfShares",
        "passportNumber",
        "percentageOfShares",
        "placeOfBirth",
        "plotNumber",
        "postalAddressNumber",
        "postalCode",
        "shareHolderType",
        "surName",
        "town",
        "tradingName"
})
public class ShareholderInformationRecord {

    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("alienId")
    private String alienId;
    @JsonProperty("companyRegNo")
    private String companyRegNo;
    @JsonProperty("companyRegistrationDate")
    private String companyRegistrationDate;
    @JsonProperty("country")
    private String country;
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
    @JsonProperty("location")
    private String location;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("nrcNumber")
    private String nrcNumber;
    @JsonProperty("numberOfShares")
    private String numberOfShares;
    @JsonProperty("passportNumber")
    private String passportNumber;
    @JsonProperty("percentageOfShares")
    private String percentageOfShares;
    @JsonProperty("placeOfBirth")
    private String placeOfBirth;
    @JsonProperty("plotNumber")
    private String plotNumber;
    @JsonProperty("postalAddressNumber")
    private String postalAddressNumber;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("shareHolderType")
    private String shareHolderType;
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
    public ShareholderInformationRecord() {
    }

    /**
     *
     * @param companyRegistrationDate
     * @param passportNumber
     * @param tradingName
     * @param country
     * @param placeOfBirth
     * @param surName
     * @param town
     * @param companyRegNo
     * @param numberOfShares
     * @param postalCode
     * @param dateOfBirth
     * @param accountNumber
     * @param percentageOfShares
     * @param foreName3
     * @param foreName2
     * @param nationality
     * @param foreName1
     * @param shareHolderType
     * @param institutionName
     * @param plotNumber
     * @param location
     * @param nrcNumber
     * @param alienId
     * @param postalAddressNumber
     */
    public ShareholderInformationRecord(String accountNumber, String alienId, String companyRegNo, String companyRegistrationDate, String country, String dateOfBirth, String foreName1, String foreName2, String foreName3, String institutionName, String location, String nationality, String nrcNumber, String numberOfShares, String passportNumber, String percentageOfShares, String placeOfBirth, String plotNumber, String postalAddressNumber, String postalCode, String shareHolderType, String surName, String town, String tradingName) {
        super();
        this.accountNumber = accountNumber;
        this.alienId = alienId;
        this.companyRegNo = companyRegNo;
        this.companyRegistrationDate = companyRegistrationDate;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.foreName1 = foreName1;
        this.foreName2 = foreName2;
        this.foreName3 = foreName3;
        this.institutionName = institutionName;
        this.location = location;
        this.nationality = nationality;
        this.nrcNumber = nrcNumber;
        this.numberOfShares = numberOfShares;
        this.passportNumber = passportNumber;
        this.percentageOfShares = percentageOfShares;
        this.placeOfBirth = placeOfBirth;
        this.plotNumber = plotNumber;
        this.postalAddressNumber = postalAddressNumber;
        this.postalCode = postalCode;
        this.shareHolderType = shareHolderType;
        this.surName = surName;
        this.town = town;
        this.tradingName = tradingName;
    }

    public ShareholderInformationRecord individual(String accountNumber, String country, String foreName1, String institutionName, String location, String nationality, String nrcNumber, String passportNumber, String percentageOfShares, String placeOfBirth, String shareHolderType, String surName, String town) {

        this.accountNumber = accountNumber;
        this.country = country;
        this.foreName1 = foreName1;
        this.institutionName = institutionName;
        this.location = location;
        this.nationality = nationality;
        this.nrcNumber = nrcNumber;
        this.passportNumber = passportNumber;
        this.percentageOfShares = percentageOfShares;
        this.placeOfBirth = placeOfBirth;
        this.shareHolderType = shareHolderType;
        this.surName = surName;
        this.town = town;

        return this ;

    }

    public ShareholderInformationRecord corporate(String accountNumber, String companyRegNo, String companyRegistrationDate, String country, String institutionName, String location, String numberOfShares, String passportNumber, String percentageOfShares, String plotNumber, String shareHolderType, String town, String tradingName) {
        this.accountNumber = accountNumber;
        this.companyRegNo = companyRegNo;
        this.companyRegistrationDate = companyRegistrationDate;
        this.country = country;
        this.institutionName = institutionName;
        this.location = location;
        this.numberOfShares = numberOfShares;
        this.passportNumber = passportNumber;
        this.percentageOfShares = percentageOfShares;
        this.plotNumber = plotNumber;
        this.shareHolderType = shareHolderType;
        this.town = town;
        this.tradingName = tradingName;
        return this ;
    }



    @JsonProperty("accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("accountNumber")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("alienId")
    public String getAlienId() {
        return alienId;
    }

    @JsonProperty("alienId")
    public void setAlienId(String alienId) {
        this.alienId = alienId;
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

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
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

    @JsonProperty("numberOfShares")
    public String getNumberOfShares() {
        return numberOfShares;
    }

    @JsonProperty("numberOfShares")
    public void setNumberOfShares(String numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    @JsonProperty("passportNumber")
    public String getPassportNumber() {
        return passportNumber;
    }

    @JsonProperty("passportNumber")
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @JsonProperty("percentageOfShares")
    public String getPercentageOfShares() {
        return percentageOfShares;
    }

    @JsonProperty("percentageOfShares")
    public void setPercentageOfShares(String percentageOfShares) {
        this.percentageOfShares = percentageOfShares;
    }

    @JsonProperty("placeOfBirth")
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    @JsonProperty("placeOfBirth")
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @JsonProperty("plotNumber")
    public String getPlotNumber() {
        return plotNumber;
    }

    @JsonProperty("plotNumber")
    public void setPlotNumber(String plotNumber) {
        this.plotNumber = plotNumber;
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

    @JsonProperty("shareHolderType")
    public String getShareHolderType() {
        return shareHolderType;
    }

    @JsonProperty("shareHolderType")
    public void setShareHolderType(String shareHolderType) {
        this.shareHolderType = shareHolderType;
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

