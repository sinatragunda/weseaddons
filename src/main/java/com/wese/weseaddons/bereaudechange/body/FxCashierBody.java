package com.wese.weseaddons.bereaudechange.body;

import com.wese.weseaddons.pojo.AppUser;

public class FxCashierBody {

    private String activeFromDate ;
    private String activeToDate ;
    private AppUser appUser ;
    private String tellerName ;
    private String description ;
    private String firstName ;
    private String lastName ;
    private String mobileNumber ;

    public FxCashierBody() {
    }

    public String getActiveFromDate() {
        return activeFromDate;
    }

    public void setActiveFromDate(String activeFromDate) {
        this.activeFromDate = activeFromDate;
    }

    public String getActiveToDate() {
        return activeToDate;
    }

    public void setActiveToDate(String activeToDate) {
        this.activeToDate = activeToDate;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {

        this.appUser = appUser;

    }

    public String getTellerName() {
        return tellerName;
    }

    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
