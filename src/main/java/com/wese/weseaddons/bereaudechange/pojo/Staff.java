package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.body.FxCashierBody;

public class Staff {

    private long id ;
    private long officeId ;
    private String firstName ;
    private String lastName ;
    private String mobileNumber ;
    private String joiningDate ;

    public Staff() {
    }

    public Staff(FxCashierBody fxCashierBody){

        setFirstName(fxCashierBody.getFirstName());
        setLastName(fxCashierBody.getLastName());
        setOfficeId(fxCashierBody.getAppUser().getOfficeId());
        setMobileNumber(fxCashierBody.getMobileNumber());
        setJoiningDate(fxCashierBody.getActiveFromDate());
    }

    public Staff(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }
}
