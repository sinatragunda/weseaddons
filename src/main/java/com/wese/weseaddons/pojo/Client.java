package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.client.ClientLoanAccount;
import com.wese.weseaddons.crb.enumerations.GENDER;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.helper.ObjectNodeHelper;
import com.wese.weseaddons.interfaces.IdIndexedClass;
import com.wese.weseaddons.weselicense.helper.ObjectNodeBuilder;
import org.json.JSONObject;

import java.util.Date;

public class Client implements IdIndexedClass{

    private long id ;
    private String accountNumber ;
    private boolean isActive ;
    private String officeId ;
    private String displayName ;
    private String externalId ;
    private Date activationDate ;
    private GENDER gender ;
    private String firstname ;
    private String surname ;
    private Date dateOfBirth ;
    private String mobileNumber ;
    private String nrcNumber ;
    private String officeName ;
    private String passportNumber ;
    private String fullname ;
    private String tenantIdentifier ;
    private ClientLoanAccount clientLoanAccount ;



    public Client(){}

    public Client(long id , String accountNumber , String externalId , String displayName , String officeId , boolean isActive, Date activationDate , GENDER gender){
        this.id = id ;
        this.isActive = isActive ;
        this.officeId = officeId ;
        this.displayName = displayName ;
        this.accountNumber = accountNumber ;
        this.externalId = externalId ;
        this.activationDate = activationDate ;
        this.gender = gender ;
    }

    public Client(long id ,String accountNumber ,String externalId ,String fullname , String firstname ,String surname ,String displayName ,String officeId ,String officeName ,boolean isActive,Date activationDate ,GENDER gender,Date dateOfBirth ,String mobileNumber ,String nrcNumber){
        this.id = id ;
        this.isActive = isActive ;
        this.officeId = officeId ;
        this.displayName = displayName ;
        this.accountNumber = accountNumber ;
        this.externalId = externalId ;
        this.activationDate = activationDate ;
        this.gender = gender ;
        this.dateOfBirth = dateOfBirth ;
        this.firstname = firstname ;
        this.surname = surname ;
        this.officeName = officeName ;
        this.mobileNumber = mobileNumber ;
        this.nrcNumber = nrcNumber ;
        this.fullname = fullname ;
    }

    public Client(long id ,String accountNumber ,String externalId ,String displayName ,String officeId ,boolean isActive){
        this.id = id ;
        this.isActive = isActive ;
        this.officeId = officeId ;
        this.displayName = displayName ;
        this.accountNumber = accountNumber ;
        this.externalId = externalId ;
    }

    public Client(long id ,String accountNumber ,String externalId ,String firstname ,String lastName ,String displayName ,String officeName ,boolean isActive){
        this.id = id ;
        this.isActive = isActive ;
        this.officeName = officeName;
        this.firstname = firstname ;
        this.surname = lastName ;
        this.displayName = displayName ;
        this.accountNumber = accountNumber ;
        this.externalId = externalId ;
    }

    public ClientLoanAccount getClientLoanAccount() {
        return clientLoanAccount;
    }

    public void setClientLoanAccount(ClientLoanAccount clientLoanAccount) {
        this.clientLoanAccount = clientLoanAccount;
    }

    public Client(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getExternalId(){

        return externalId ;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setTenantIdentifier(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
    }

    public String getTenantIdentifier(){
        return tenantIdentifier ;
    }

    public String getPassportNumber() {
        if(passportNumber==null){
            return "Data Not Collected";
        }
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setNrcNumber(String nrcNumber) {
        this.nrcNumber = nrcNumber;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    //    public ClientLoanAccount getClientLoanAccount() {
//        return clientLoanAccount;
//    }
//
//    public void setClientLoanAccount(ClientLoanAccount clientLoanAccount) {
//        this.clientLoanAccount = clientLoanAccount;
//    }


    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setExternalId(String externalId){
        this.externalId = externalId ;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getNrcNumber(){
        return this.externalId ;
    }

    public String getGender() {
        return gender.name();
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public ObjectNode objectNode(){

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.put("displayName" ,displayName);
        objectNode.put("firstname" ,firstname);
        objectNode.put("lastname" ,surname);
        objectNode.put("id" ,accountNumber);

        return objectNode ;
    }
}
