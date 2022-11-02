/*Created by Sinatra Gunda
  At 4:37 PM on 2/23/2020 */

package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.GURANTOR_TYPE;

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
        "gender",
        "groupName",
        "groupNumber",
        "guaranteeLimit",
        "guarantorType",
        "homeTelephone",
        "institutionName",
        "mobileTelephone",
        "nationality",
        "nrcNumber",
        "passportNumber",
        "placeOfBirth",
        "postalAddressNumber",
        "postalCode",
        "surName",
        "telephone1",
        "telephone2",
        "telephone3",
        "town",
        "tradingName",
        "workTelephone"
})
public class GuarantorInformationRecord {

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
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("groupNumber")
    private String groupNumber;
    @JsonProperty("guaranteeLimit")
    private String guaranteeLimit;
    @JsonProperty("guarantorType")
    private GURANTOR_TYPE guarantorType;
    @JsonProperty("homeTelephone")
    private String homeTelephone;
    @JsonProperty("institutionName")
    private String institutionName;
    @JsonProperty("mobileTelephone")
    private String mobileTelephone;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("nrcNumber")
    private String nrcNumber;
    @JsonProperty("passportNumber")
    private String passportNumber;
    @JsonProperty("placeOfBirth")
    private String placeOfBirth;
    @JsonProperty("postalAddressNumber")
    private String postalAddressNumber;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("telephone1")
    private String telephone1;
    @JsonProperty("telephone2")
    private String telephone2;
    @JsonProperty("telephone3")
    private String telephone3;
    @JsonProperty("town")
    private String town;
    @JsonProperty("tradingName")
    private String tradingName;
    @JsonProperty("workTelephone")
    private String workTelephone;

    /**
     * No args constructor for use in serialization
     *
     */
    public GuarantorInformationRecord() {
    }

    /**
     *
     * @param country
     * @param gender
     * @param surName
     * @param companyRegNo
     * @param postalCode
     * @param telephone3
     * @param telephone2
     * @param telephone1
     * @param guaranteeLimit
     * @param foreName3
     * @param foreName2
     * @param foreName1
     * @param homeTelephone
     * @param companyRegistrationDate
     * @param passportNumber
     * @param tradingName
     * @param placeOfBirth
     * @param town
     * @param dateOfBirth
     * @param workTelephone
     * @param accountNumber
     * @param groupNumber
     * @param groupName
     * @param mobileTelephone
     * @param nationality
     * @param institutionName
     * @param guarantorType
     * @param nrcNumber
     * @param alienId
     * @param postalAddressNumber
     */
    public GuarantorInformationRecord(String accountNumber, String alienId, String companyRegNo, String companyRegistrationDate, String country, String dateOfBirth, String foreName1, String foreName2, String foreName3, String gender, String groupName, String groupNumber, String guaranteeLimit, GURANTOR_TYPE guarantorType, String homeTelephone, String institutionName, String mobileTelephone, String nationality, String nrcNumber, String passportNumber, String placeOfBirth, String postalAddressNumber, String postalCode, String surName, String telephone1, String telephone2, String telephone3, String town, String tradingName, String workTelephone) {
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
        this.gender = gender;
        this.groupName = groupName;
        this.groupNumber = groupNumber;
        this.guaranteeLimit = guaranteeLimit;
        this.guarantorType = guarantorType;
        this.homeTelephone = homeTelephone;
        this.institutionName = institutionName;
        this.mobileTelephone = mobileTelephone;
        this.nationality = nationality;
        this.nrcNumber = nrcNumber;
        this.passportNumber = passportNumber;
        this.placeOfBirth = placeOfBirth;
        this.postalAddressNumber = postalAddressNumber;
        this.postalCode = postalCode;
        this.surName = surName;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        this.telephone3 = telephone3;
        this.town = town;
        this.tradingName = tradingName;
        this.workTelephone = workTelephone;
    }

    public GuarantorInformationRecord corporate(String accountNumber, String companyRegNo, String companyRegistrationDate, String country, String guaranteeLimit, GURANTOR_TYPE guarantorType,String institutionName, String telephone1, String town, String tradingName, String workTelephone) {

        this.accountNumber = accountNumber;
        this.companyRegNo = companyRegNo;
        this.companyRegistrationDate = companyRegistrationDate;
        this.country = country;
        this.guaranteeLimit = guaranteeLimit;
        this.guarantorType = guarantorType;
        this.institutionName = institutionName;
        this.telephone1 = telephone1;
        this.town = town;
        this.tradingName = tradingName;
        this.workTelephone = workTelephone;

        return this ;

    }

    public GuarantorInformationRecord individual(String accountNumber, String alienId, String country, String dateOfBirth, String foreName1, String gender, String guaranteeLimit, GURANTOR_TYPE guarantorType, String mobileTelephone, String nationality, String nrcNumber, String passportNumber, String surName, String telephone1, String town) {
        this.accountNumber = accountNumber;
        this.alienId = alienId;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.foreName1 = foreName1;
        this.gender = gender;
        this.guaranteeLimit = guaranteeLimit;
        this.guarantorType = guarantorType;
        this.mobileTelephone = mobileTelephone;
        this.nationality = nationality;
        this.nrcNumber = nrcNumber;
        this.passportNumber = passportNumber;
        this.surName = surName;
        this.telephone1 = telephone1;
        this.town = town;
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

    @JsonProperty("guaranteeLimit")
    public String getGuaranteeLimit() {
        return guaranteeLimit;
    }

    @JsonProperty("guaranteeLimit")
    public void setGuaranteeLimit(String guaranteeLimit) {
        this.guaranteeLimit = guaranteeLimit;
    }

    @JsonProperty("guarantorType")
    public String getGuarantorType() {
        return guarantorType.name();
    }

    @JsonProperty("guarantorType")
    public void setGuarantorType(GURANTOR_TYPE guarantorType) {
        this.guarantorType = guarantorType;
    }

    @JsonProperty("homeTelephone")
    public String getHomeTelephone() {
        return homeTelephone;
    }

    @JsonProperty("homeTelephone")
    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    @JsonProperty("institutionName")
    public String getInstitutionName() {
        return institutionName;
    }

    @JsonProperty("institutionName")
    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
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

    @JsonProperty("passportNumber")
    public String getPassportNumber() {
        return passportNumber;
    }

    @JsonProperty("passportNumber")
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @JsonProperty("placeOfBirth")
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    @JsonProperty("placeOfBirth")
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
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

    @JsonProperty("surName")
    public String getSurName() {
        return surName;
    }

    @JsonProperty("surName")
    public void setSurName(String surName) {
        this.surName = surName;
    }

    @JsonProperty("telephone1")
    public String getTelephone1() {
        return telephone1;
    }

    @JsonProperty("telephone1")
    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    @JsonProperty("telephone2")
    public String getTelephone2() {
        return telephone2;
    }

    @JsonProperty("telephone2")
    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    @JsonProperty("telephone3")
    public String getTelephone3() {
        return telephone3;
    }

    @JsonProperty("telephone3")
    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
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

    @JsonProperty("workTelephone")
    public String getWorkTelephone() {
        return workTelephone;
    }

    @JsonProperty("workTelephone")
    public void setWorkTelephone(String workTelephone) {
        this.workTelephone = workTelephone;
    }

}

