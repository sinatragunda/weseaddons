package com.wese.weseaddons.bereaudechange.pojo;


import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser ;
import com.wese.weseaddons.bereaudechange.body.FxCashierBody;

public class FxCashier {

    private long id ;
    private long activeFromDate ;
    private long activeToDate ;
    private AppUser appUser ;
    private String tellerName ;
    private String description ;
    private long systemTellerId ;
    private long systemStaffId ;
    private long systemCashierId ;


    public FxCashier() {
    }

    public FxCashier(FxCashierBody fxCashierBody){

        setActiveFromDate(TimeHelper.angularStringToDate(fxCashierBody.getActiveFromDate()));
        setActiveToDate(TimeHelper.angularStringToDate(fxCashierBody.getActiveToDate()));
        setTellerName(fxCashierBody.getTellerName());
        setDescription(fxCashierBody.getDescription());
        setAppUser(fxCashierBody.getAppUser());
    }

    public FxCashier(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getActiveFromDate() {
        return activeFromDate;
    }

    public void setActiveFromDate(long activeFromDate) {
        this.activeFromDate = activeFromDate;
    }

    public long getActiveToDate() {
        return activeToDate;
    }

    public void setActiveToDate(long activeToDate) {
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

    public long getSystemTellerId() {
        return systemTellerId;
    }

    public void setSystemTellerId(long systemTellerId) {
        this.systemTellerId = systemTellerId;
    }

    public long getSystemStaffId() {
        return systemStaffId;
    }

    public void setSystemStaffId(long systemStaffId) {
        this.systemStaffId = systemStaffId;
    }

    public long getSystemCashierId() {
        return systemCashierId;
    }

    public void setSystemCashierId(long systemCashierId) {
        this.systemCashierId = systemCashierId;
    }
}
