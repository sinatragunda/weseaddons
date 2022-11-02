package com.wese.weseaddons.crb.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wese.weseaddons.crb.enumerations.APPLICATION_STATUS;
import com.wese.weseaddons.crb.enumerations.CREDIT_AMORTIZATION_TYPE;
import com.wese.weseaddons.crb.enumerations.STATUS_UPDATE_REASON;
import com.wese.weseaddons.crb.enumerations.TYPE_OF_COMPANY;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.Timeline;

import java.util.Date ;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "alienId",
        "accountNumber",
        "applicationAmount",
        "applicationCurrencyType",
        "applicationDate",
        "applicationDuration",
        "applicationStatus",
        "approvalDate",
        "approvedAmount",
        "associatedCompanyName",
        "companyRegNo",
        "companyRegistrationDate",
        "companyStatus",
        "country",
        "creditAmortizationType",
        "dateOfBirth",
        "disbursedAmount",
        "district",
        "drivingLicenseNumber",
        "durationAtThisAddressMonths",
        "durationAtThisAddressYears",
        "emailAddress",
        "employerAddressLine1",
        "employerCountry",
        "employerName",
        "employerTown",
        "employmentDurationMonths",
        "employmentDurationYears",
        "facsimile",
        "foreName1",
        "foreName2",
        "foreName3",
        "gender",
        "groupName",
        "groupNumber",
        "healthInsuranceNumber",
        "homeTelephone",
        "income",
        "incomeFrequency",
        "industry",
        "institution",
        "interestCalculationMethod",
        "interestType",
        "location",
        "maritalStatus",
        "maturityDate",
        "mobileTelephone",
        "nationality",
        "noOfDependants",
        "nrcNumber",
        "occupation",
        "otherInformation",
        "passportNumber",
        "placeOfBirth",
        "plotNumber",
        "postalCode",
        "postalNumber",
        "province",
        "residenceType",
        "salutation",
        "socialSecurityNumber",
        "statusUpdateReason",
        "surName",
        "taxNumber",
        "telephone1",
        "town",
        "tradingName",
        "typeOfCompany",
        "workTelephone"
})
public class CreditApplicationRecord {

    @JsonProperty("alienId")
    private String alienId;
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("applicationAmount")
    private String applicationAmount;
    @JsonProperty("applicationCurrencyType")
    private String applicationCurrencyType;
    @JsonProperty("applicationDate")
    private String applicationDate;
    @JsonProperty("applicationDuration")
    private String applicationDuration;
    @JsonProperty("applicationStatus")
    private String applicationStatus;
    @JsonProperty("approvalDate")
    private String approvalDate;
    @JsonProperty("approvedAmount")
    private String approvedAmount;
    @JsonProperty("associatedCompanyName")
    private String associatedCompanyName;
    @JsonProperty("companyRegNo")
    private String companyRegNo;
    @JsonProperty("companyRegistrationDate")
    private String companyRegistrationDate;
    @JsonProperty("companyStatus")
    private String companyStatus;
    @JsonProperty("country")
    private String country;
    @JsonProperty("creditAmortizationType")
    private CREDIT_AMORTIZATION_TYPE creditAmortizationType;
    @JsonProperty("dateOfBirth")
    private String dateOfBirth;
    @JsonProperty("disbursedAmount")
    private String disbursedAmount;
    @JsonProperty("district")
    private String district;
    @JsonProperty("drivingLicenseNumber")
    private String drivingLicenseNumber;
    @JsonProperty("durationAtThisAddressMonths")
    private String durationAtThisAddressMonths;
    @JsonProperty("durationAtThisAddressYears")
    private String durationAtThisAddressYears;
    @JsonProperty("emailAddress")
    private String emailAddress;
    @JsonProperty("employerAddressLine1")
    private String employerAddressLine1;
    @JsonProperty("employerCountry")
    private String employerCountry;
    @JsonProperty("employerName")
    private String employerName;
    @JsonProperty("employerTown")
    private String employerTown;
    @JsonProperty("employmentDurationMonths")
    private String employmentDurationMonths;
    @JsonProperty("employmentDurationYears")
    private String employmentDurationYears;
    @JsonProperty("facsimile")
    private String facsimile;
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
    @JsonProperty("healthInsuranceNumber")
    private String healthInsuranceNumber;
    @JsonProperty("homeTelephone")
    private String homeTelephone;
    @JsonProperty("income")
    private String income;
    @JsonProperty("incomeFrequency")
    private String incomeFrequency;
    @JsonProperty("industry")
    private String industry;
    @JsonProperty("institution")
    private String institution;
    @JsonProperty("interestCalculationMethod")
    private String interestCalculationMethod;
    @JsonProperty("interestType")
    private String interestType;
    @JsonProperty("location")
    private String location;
    @JsonProperty("maritalStatus")
    private String maritalStatus;
    @JsonProperty("maturityDate")
    private String maturityDate;
    @JsonProperty("mobileTelephone")
    private String mobileTelephone;
    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("noOfDependants")
    private String noOfDependants;
    @JsonProperty("nrcNumber")
    private String nrcNumber;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("otherInformation")
    private String otherInformation;
    @JsonProperty("passportNumber")
    private String passportNumber;
    @JsonProperty("placeOfBirth")
    private String placeOfBirth;
    @JsonProperty("plotNumber")
    private String plotNumber;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("postalNumber")
    private String postalNumber;
    @JsonProperty("province")
    private String province;
    @JsonProperty("residenceType")
    private String residenceType;
    @JsonProperty("salutation")
    private String salutation;
    @JsonProperty("socialSecurityNumber")
    private String socialSecurityNumber;
    @JsonProperty("statusUpdateReason")
    private STATUS_UPDATE_REASON statusUpdateReason;
    @JsonProperty("surName")
    private String surName;
    @JsonProperty("taxNumber")
    private String taxNumber;
    @JsonProperty("telephone1")
    private String telephone1;
    @JsonProperty("town")
    private String town;
    @JsonProperty("tradingName")
    private String tradingName;
    @JsonProperty("typeOfCompany")
    private TYPE_OF_COMPANY typeOfCompany;
    @JsonProperty("workTelephone")
    private String workTelephone;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreditApplicationRecord() {
    }


    /**
     *
     * @param country
     * @param drivingLicenseNumber
     * @param occupation
     * @param surName
     * @param companyRegNo
     * @param postalCode
     * @param associatedCompanyName
     * @param telephone1
     * @param institution
     * @param residenceType
     * @param emailAddress
     * @param applicationStatus
     * @param foreName3
     * @param foreName2
     * @param province
     * @param foreName1
     * @param applicationAmount
     * @param maturityDate
     * @param typeOfCompany
     * @param companyRegistrationDate
     * @param passportNumber
     * @param facsimile
     * @param town
     * @param creditAmortizationType
     * @param companyStatus
     * @param taxNumber
     * @param otherInformation
     * @param employerAddressLine1
     * @param incomeFrequency
     * @param healthInsuranceNumber
     * @param groupName
     * @param mobileTelephone
     * @param nationality
     * @param durationAtThisAddressYears
     * @param applicationCurrencyType
     * @param district
     * @param plotNumber
     * @param salutation
     * @param noOfDependants
     * @param nrcNumber
     * @param alienId
     * @param maritalStatus
     * @param applicationDate
     * @param income
     * @param employmentDurationYears
     * @param approvalDate
     * @param gender
     * @param socialSecurityNumber
     * @param interestType
     * @param employerName
     * @param industry
     * @param employmentDurationMonths
     * @param applicationDuration
     * @param approvedAmount
     * @param durationAtThisAddressMonths
     * @param disbursedAmount
     * @param homeTelephone
     * @param employerTown
     * @param tradingName
     * @param employerCountry
     * @param placeOfBirth
     * @param statusUpdateReason
     * @param postalNumber
     * @param dateOfBirth
     * @param workTelephone
     * @param groupNumber
     * @param interestCalculationMethod
     * @param location
     */
    public CreditApplicationRecord(String alienId, String applicationAmount, String applicationCurrencyType, String applicationDate, String applicationDuration, APPLICATION_STATUS applicationStatus, String approvalDate, String approvedAmount, String associatedCompanyName, String companyRegNo, String companyRegistrationDate, String companyStatus, String country, CREDIT_AMORTIZATION_TYPE creditAmortizationType, String dateOfBirth, String disbursedAmount, String district, String drivingLicenseNumber, String durationAtThisAddressMonths, String durationAtThisAddressYears, String emailAddress, String employerAddressLine1, String employerCountry, String employerName, String employerTown, String employmentDurationMonths, String employmentDurationYears, String facsimile, String foreName1, String foreName2, String foreName3, String gender, String groupName, String groupNumber, String healthInsuranceNumber, String homeTelephone, String income, String incomeFrequency, String industry, String institution, String interestCalculationMethod, String interestType, String location, String maritalStatus, String maturityDate, String mobileTelephone, String nationality, String noOfDependants, String nrcNumber, String occupation, String otherInformation, String passportNumber, String placeOfBirth, String plotNumber, String postalCode, String postalNumber, String province, String residenceType, String salutation, String socialSecurityNumber, STATUS_UPDATE_REASON statusUpdateReason, String surName, String taxNumber, String telephone1, String town, String tradingName, TYPE_OF_COMPANY typeOfCompany, String workTelephone) {
        super();
        this.alienId = alienId;
        this.applicationAmount = applicationAmount;
        this.applicationCurrencyType = applicationCurrencyType;
        this.applicationDate = applicationDate;
        this.applicationDuration = applicationDuration;
        this.applicationStatus = applicationStatus.name();
        this.approvalDate = approvalDate;
        this.approvedAmount = approvedAmount;
        this.associatedCompanyName = associatedCompanyName;
        this.companyRegNo = companyRegNo;
        this.companyRegistrationDate = companyRegistrationDate;
        this.companyStatus = companyStatus;
        this.country = country;
        this.creditAmortizationType = creditAmortizationType;
        this.dateOfBirth = dateOfBirth;
        this.disbursedAmount = disbursedAmount;
        this.district = district;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.durationAtThisAddressMonths = durationAtThisAddressMonths;
        this.durationAtThisAddressYears = durationAtThisAddressYears;
        this.emailAddress = emailAddress;
        this.employerAddressLine1 = employerAddressLine1;
        this.employerCountry = employerCountry;
        this.employerName = employerName;
        this.employerTown = employerTown;
        this.employmentDurationMonths = employmentDurationMonths;
        this.employmentDurationYears = employmentDurationYears;
        this.facsimile = facsimile;
        this.foreName1 = foreName1;
        this.foreName2 = foreName2;
        this.foreName3 = foreName3;
        this.gender = gender;
        this.groupName = groupName;
        this.groupNumber = groupNumber;
        this.healthInsuranceNumber = healthInsuranceNumber;
        this.homeTelephone = homeTelephone;
        this.income = income;
        this.incomeFrequency = incomeFrequency;
        this.industry = industry;
        this.institution = institution;
        this.interestCalculationMethod = interestCalculationMethod;
        this.interestType = interestType;
        this.location = location;
        this.maritalStatus = maritalStatus;
        this.maturityDate = maturityDate;
        this.mobileTelephone = mobileTelephone;
        this.nationality = nationality;
        this.noOfDependants = noOfDependants;
        this.nrcNumber = nrcNumber;
        this.occupation = occupation;
        this.otherInformation = otherInformation;
        this.passportNumber = passportNumber;
        this.placeOfBirth = placeOfBirth;
        this.plotNumber = plotNumber;
        this.postalCode = postalCode;
        this.postalNumber = postalNumber;
        this.province = province;
        this.residenceType = residenceType;
        this.salutation = salutation;
        this.socialSecurityNumber = socialSecurityNumber;
        this.statusUpdateReason = statusUpdateReason;
        this.surName = surName;
        this.taxNumber = taxNumber;
        this.telephone1 = telephone1;
        this.town = town;
        this.tradingName = tradingName;
        this.typeOfCompany = typeOfCompany;
        this.workTelephone = workTelephone;
    }

    public CreditApplicationRecord individual(Loan loan ,Client client , APPLICATION_STATUS applicationStatus, STATUS_UPDATE_REASON statusUpdateReason) {

        Timeline timeline = loan.getTimeline();

        loan.setApplicationStatus(APPLICATION_STATUS.F);
        System.err.println(applicationStatus);
        //String submittedDate = TimeHelper.dateToStringWithFormat(timeline.getSubmittedDate(), Constants.crbDateFormat);
        //String expectedMaturityDate = TimeHelper.dateToStringWithFormat(timeline.getExpectedMaturityDate() ,Constants.crbDateFormat);

        String submittedDate = "01012021";
        String expectedMaturityDate = "01012021";

        this.accountNumber = loan.getAccountNo();
        this.applicationAmount = String.valueOf(loan.getPrincipal());
        this.applicationCurrencyType = loan.getCurrency().getCode();
        this.applicationDate = submittedDate;
        this.applicationDuration = "1" ;//Constants.dataNotCollected;
        this.applicationStatus = applicationStatus.name();
        this.approvalDate = submittedDate;
        this.approvedAmount = String.valueOf(loan.getPrincipal());
        this.country = "Zambia";
        this.dateOfBirth = "20011026";  //TimeHelper.dateToStringWithFormat(client.getDateOfBirth() ,Constants.crbDateFormat);
        this.disbursedAmount = String.valueOf(loan.getPrincipalDisbursed());
        this.town = client.getOfficeName();
        this.district = town;
        this.durationAtThisAddressMonths = durationAtThisAddressMonths==null ? "1": durationAtThisAddressMonths;
        this.durationAtThisAddressYears = durationAtThisAddressYears==null ? "1":durationAtThisAddressYears ;
        this.employerAddressLine1 = Constants.dataNotCollected;
        this.employerName = Constants.dataNotCollected;
        this.employerTown = Constants.dataNotCollected;
        this.employmentDurationYears = Constants.dataNotCollected ;
        this.foreName1 = client.getFirstname();
        this.gender = "M";//client.getGender();
        this.groupName = "individual";
        this.groupNumber = "1";
        this.income = "5000";///Constants.dataNotCollected;
        this.incomeFrequency = "M";
        this.location = town;
        this.maturityDate = expectedMaturityDate;
        this.mobileTelephone = client.getMobileNumber();
        this.nationality = "Zambian";
        this.nrcNumber = client.getNrcNumber();
        this.passportNumber = client.getNrcNumber();
        this.province = town;
        this.statusUpdateReason = statusUpdateReason;
        this.surName = client.getSurname();
        this.province = town ;
        this.salutation = gender.equalsIgnoreCase("m") ? "MR":"MRS";
        this.maritalStatus = "O";
        this.residenceType = "T";

        return this ;
    }

    public CreditApplicationRecord corporate(Loan loan , Client client,TYPE_OF_COMPANY typeOfCompany) {

        String submittedDate = TimeHelper.dateToStringWithFormat(loan.getSubmittedDate(), Constants.crbDateFormat);
        String expectedMaturityDate = TimeHelper.dateToStringWithFormat(loan.getExpectedMaturityDate() ,Constants.crbDateFormat);

        this.applicationAmount = String.valueOf(loan.getPrincipal());
        this.applicationCurrencyType = loan.getCurrency().getCode();
        this.applicationDate = TimeHelper.dateToStringWithFormat(loan.getSubmittedDate() ,Constants.crbDateFormat);
        this.applicationDuration = applicationDuration;
        this.applicationStatus = APPLICATION_STATUS.G.name() ;
        this.approvalDate = approvalDate;
        this.approvedAmount = approvedAmount;
        this.associatedCompanyName = client.getFullname();
        this.companyRegNo = client.getNrcNumber();
        this.companyRegistrationDate = Constants.dataNotCollected;
        this.companyStatus = companyStatus;
        this.country = "Zambia";
        this.creditAmortizationType = creditAmortizationType;
        this.disbursedAmount = String.valueOf(loan.getPrincipalDisbursed());
        this.town = client.getOfficeName();
        this.district = town;
        this.industry = industry;
        this.institution = institution;
        this.maturityDate = expectedMaturityDate ;
        this.mobileTelephone = client.getMobileNumber();
        this.nationality = "Zambian";
        this.plotNumber = "";
        this.postalCode = "";
        this.postalNumber = "";
        this.province = town;
        this.statusUpdateReason = STATUS_UPDATE_REASON.NEW_LOAN;
        this.telephone1 = client.getMobileNumber();
        this.tradingName = client.getFullname();
        this.typeOfCompany = typeOfCompany;
        this.workTelephone = client.getMobileNumber();
        return this ;
    }

    @JsonProperty("alienId")
    public String getAlienId() {
        return alienId;
    }

    @JsonProperty("alienId")
    public void setAlienId(String alienId) {
        this.alienId = alienId;
    }

    @JsonProperty("applicationAmount")
    public String getApplicationAmount() {
        return applicationAmount;
    }

    @JsonProperty("applicationAmount")
    public void setApplicationAmount(String applicationAmount) {
        this.applicationAmount = applicationAmount;
    }

    @JsonProperty("applicationCurrencyType")
    public String getApplicationCurrencyType() {

        if(applicationCurrencyType.equalsIgnoreCase("ZMK")){
            this.applicationCurrencyType = "ZMW";
        }
        if(applicationCurrencyType.equalsIgnoreCase("ZWD")){
            this.applicationCurrencyType = "ZWL";
        }
        return applicationCurrencyType;
    }

    @JsonProperty("applicationCurrencyType")
    public void setApplicationCurrencyType(String applicationCurrencyType) {
        this.applicationCurrencyType = applicationCurrencyType;
    }

    @JsonProperty("applicationDate")
    public String getApplicationDate() {
        return applicationDate;
    }

    @JsonProperty("applicationDate")
    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    @JsonProperty("applicationDuration")
    public String getApplicationDuration() {
        return applicationDuration;
    }

    @JsonProperty("applicationDuration")
    public void setApplicationDuration(String applicationDuration) {
        this.applicationDuration = applicationDuration;
    }

    @JsonProperty("applicationStatus")
    public String getApplicationStatus() {
        return applicationStatus;
    }

    @JsonProperty("applicationStatus")
    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @JsonProperty("approvalDate")
    public String getApprovalDate() {

        if(approvalDate==null){

            Date date = TimeHelper.dateNow();
            return TimeHelper.dateToStringWithFormat(date , Constants.crbDateFormat);

        }
        return approvalDate;
    }

    @JsonProperty("approvalDate")
    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    @JsonProperty("approvedAmount")
    public String getApprovedAmount() {


        if(approvalDate==null){

            return approvedAmount ;
        }
        return approvedAmount;
    }

    @JsonProperty("approvedAmount")
    public void setApprovedAmount(String approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    @JsonProperty("associatedCompanyName")
    public String getAssociatedCompanyName() {
        return associatedCompanyName;
    }

    @JsonProperty("associatedCompanyName")
    public void setAssociatedCompanyName(String associatedCompanyName) {
        this.associatedCompanyName = associatedCompanyName;
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

    @JsonProperty("companyStatus")
    public String getCompanyStatus() {
        return companyStatus;
    }

    @JsonProperty("companyStatus")
    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("creditAmortizationType")
    public CREDIT_AMORTIZATION_TYPE getCreditAmortizationType() {
        return creditAmortizationType;
    }

    @JsonProperty("creditAmortizationType")
    public void setCreditAmortizationType(CREDIT_AMORTIZATION_TYPE creditAmortizationType) {
        this.creditAmortizationType = creditAmortizationType;
    }

    @JsonProperty("dateOfBirth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    @JsonProperty("drivingLicenseNumber")
    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    @JsonProperty("drivingLicenseNumber")
    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    @JsonProperty("durationAtThisAddressMonths")
    public String getDurationAtThisAddressMonths() {
        return durationAtThisAddressMonths;
    }

    @JsonProperty("durationAtThisAddressMonths")
    public void setDurationAtThisAddressMonths(String durationAtThisAddressMonths) {
        this.durationAtThisAddressMonths = durationAtThisAddressMonths;
    }

    @JsonProperty("durationAtThisAddressYears")
    public String getDurationAtThisAddressYears() {
        return durationAtThisAddressYears;
    }

    @JsonProperty("durationAtThisAddressYears")
    public void setDurationAtThisAddressYears(String durationAtThisAddressYears) {
        this.durationAtThisAddressYears = durationAtThisAddressYears;
    }

    @JsonProperty("emailAddress")
    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty("emailAddress")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @JsonProperty("employerAddressLine1")
    public String getEmployerAddressLine1() {
        return employerAddressLine1;
    }

    @JsonProperty("employerAddressLine1")
    public void setEmployerAddressLine1(String employerAddressLine1) {
        this.employerAddressLine1 = employerAddressLine1;
    }

    @JsonProperty("employerCountry")
    public String getEmployerCountry() {

        if(employerCountry==null){
            return "Data not collected";
        }

        return employerCountry;
    }

    @JsonProperty("employerCountry")
    public void setEmployerCountry(String employerCountry) {
        this.employerCountry = employerCountry;
    }

    @JsonProperty("employerName")
    public String getEmployerName() {

        if(employerName==null){
            return "Data not collected";
        }
        return employerName;
    }

    @JsonProperty("employerName")
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    @JsonProperty("employerTown")
    public String getEmployerTown() {

        if(employerTown==null){
            return "Data not collected";
        }

        return employerTown;
    }

    @JsonProperty("employerTown")
    public void setEmployerTown(String employerTown) {
        this.employerTown = employerTown;
    }

    @JsonProperty("employmentDurationMonths")
    public String getEmploymentDurationMonths() {
        return employmentDurationMonths;
    }

    @JsonProperty("employmentDurationMonths")
    public void setEmploymentDurationMonths(String employmentDurationMonths) {
        this.employmentDurationMonths = employmentDurationMonths;
    }

    @JsonProperty("employmentDurationYears")
    public String getEmploymentDurationYears() {
        return employmentDurationYears;
    }

    @JsonProperty("employmentDurationYears")
    public void setEmploymentDurationYears(String employmentDurationYears) {
        this.employmentDurationYears = employmentDurationYears;
    }

    @JsonProperty("facsimile")
    public String getFacsimile() {
        return facsimile;
    }

    @JsonProperty("facsimile")
    public void setFacsimile(String facsimile) {
        this.facsimile = facsimile;
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

        if(groupName==null){
            return "individual group";
        }
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("groupNumber")
    public String getGroupNumber() {

        if(groupNumber==null){
            return "1";
        }
        return groupNumber;
    }

    @JsonProperty("groupNumber")
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @JsonProperty("healthInsuranceNumber")
    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    @JsonProperty("healthInsuranceNumber")
    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
    }

    @JsonProperty("homeTelephone")
    public String getHomeTelephone() {
        return homeTelephone;
    }

    @JsonProperty("homeTelephone")
    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    @JsonProperty("income")
    public String getIncome() {
        return income;
    }

    @JsonProperty("income")
    public void setIncome(String income) {
        this.income = income;
    }

    @JsonProperty("incomeFrequency")
    public String getIncomeFrequency() {
        return incomeFrequency;
    }

    @JsonProperty("incomeFrequency")
    public void setIncomeFrequency(String incomeFrequency) {
        this.incomeFrequency = incomeFrequency;
    }

    @JsonProperty("industry")
    public String getIndustry() {
        return industry;
    }

    @JsonProperty("industry")
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @JsonProperty("institution")
    public String getInstitution() {
        return institution;
    }

    @JsonProperty("institution")
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @JsonProperty("interestCalculationMethod")
    public String getInterestCalculationMethod() {
        return interestCalculationMethod;
    }

    @JsonProperty("interestCalculationMethod")
    public void setInterestCalculationMethod(String interestCalculationMethod) {
        this.interestCalculationMethod = interestCalculationMethod;
    }

    @JsonProperty("interestType")
    public String getInterestType() {
        return interestType;
    }

    @JsonProperty("interestType")
    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }

    @JsonProperty("maritalStatus")
    public String getMaritalStatus() {
        return maritalStatus;
    }

    @JsonProperty("maritalStatus")
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
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

    @JsonProperty("noOfDependants")
    public String getNoOfDependants() {
        return noOfDependants;
    }

    @JsonProperty("noOfDependants")
    public void setNoOfDependants(String noOfDependants) {
        this.noOfDependants = noOfDependants;
    }

    @JsonProperty("nrcNumber")
    public String getNrcNumber() {

        if(nrcNumber==null){
            nrcNumber = Constants.dataNotCollected ;
        }
        return nrcNumber;
    }

    @JsonProperty("nrcNumber")
    public void setNrcNumber(String nrcNumber) {
        this.nrcNumber = nrcNumber;
    }

    @JsonProperty("occupation")
    public String getOccupation() {

        if(occupation==null){
            return "Data not collected";
        }

        return occupation;
    }

    @JsonProperty("occupation")
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    @JsonProperty("otherInformation")
    public String getOtherInformation() {
        return otherInformation;
    }

    @JsonProperty("otherInformation")
    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
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

    @JsonProperty("plotNumber")
    public String getPlotNumber() {
        return plotNumber;
    }

    @JsonProperty("plotNumber")
    public void setPlotNumber(String plotNumber) {
        this.plotNumber = plotNumber;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("postalNumber")
    public String getPostalNumber() {
        return postalNumber;
    }

    @JsonProperty("postalNumber")
    public void setPostalNumber(String postalNumber) {
        this.postalNumber = postalNumber;
    }

    @JsonProperty("province")
    public String getProvince() {
        return province;
    }

    @JsonProperty("province")
    public void setProvince(String province) {
        this.province = province;
    }

    @JsonProperty("residenceType")
    public String getResidenceType() {
        return residenceType;
    }

    @JsonProperty("residenceType")
    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    @JsonProperty("salutation")
    public String getSalutation() {
        return salutation;
    }

    @JsonProperty("salutation")
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    @JsonProperty("socialSecurityNumber")
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    @JsonProperty("socialSecurityNumber")
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    @JsonProperty("statusUpdateReason")
    public STATUS_UPDATE_REASON getStatusUpdateReason() {
        return statusUpdateReason;
    }

    @JsonProperty("statusUpdateReason")
    public void setStatusUpdateReason(STATUS_UPDATE_REASON statusUpdateReason) {
        this.statusUpdateReason = statusUpdateReason;
    }

    @JsonProperty("surName")
    public String getSurName() {
        return surName;
    }

    @JsonProperty("surName")
    public void setSurName(String surName) {
        this.surName = surName;
    }

    @JsonProperty("taxNumber")
    public String getTaxNumber() {
        return taxNumber;
    }

    @JsonProperty("taxNumber")
    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    @JsonProperty("telephone1")
    public String getTelephone1() {
        return telephone1;
    }

    @JsonProperty("telephone1")
    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
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
    public TYPE_OF_COMPANY getTypeOfCompany() {
        return typeOfCompany;
    }

    @JsonProperty("typeOfCompany")
    public void setTypeOfCompany(TYPE_OF_COMPANY typeOfCompany) {
        this.typeOfCompany = typeOfCompany;
    }

    @JsonProperty("workTelephone")
    public String getWorkTelephone() {
        return workTelephone;
    }

    @JsonProperty("workTelephone")
    public void setWorkTelephone(String workTelephone) {
        this.workTelephone = workTelephone;
    }


     @JsonProperty("accountNumber")
    public String getAccountNumber() {
        return accountNumber;
    }


}
