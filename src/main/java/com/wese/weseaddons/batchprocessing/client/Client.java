package com.wese.weseaddons.batchprocessing.client;

public class Client {

    private long id ;
    private String accountNumber ;
    private boolean isActive ;
    private String officeId ;
    private String displayName ;
    private String externalId ;
    private ClientLoanAccount clientLoanAccount ;

    public Client(long id ,String accountNumber ,String externalId ,String displayName ,String officeId ,boolean isActive){
        this.id = id ;
        this.isActive = isActive ;
        this.officeId = officeId ;
        this.displayName = displayName ;
        this.accountNumber = accountNumber ;
        this.externalId = externalId ;
    }

    public long getId() {
        return id;
    }

    public String getExternalId(){

        return externalId ;
    }

    public ClientLoanAccount getClientLoanAccount() {
        return clientLoanAccount;
    }

    public void setClientLoanAccount(ClientLoanAccount clientLoanAccount) {
        this.clientLoanAccount = clientLoanAccount;
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
}
