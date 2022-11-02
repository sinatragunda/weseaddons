/*Created by Sinatra Gunda
  At 08:12 on 10/5/2020 */

package com.wese.weseaddons.config.pojo;

import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;

import java.util.List;

public class AndroidAppSettings {
    private String loanProducts ;
    private String address ;
    private String activityNotification ;
    private String tenant ;
    private Double appVersion ;
    private List<CompanyOfficials> emailNotificationList ;


    public AndroidAppSettings() {
    }

    public List<CompanyOfficials> getEmailNotificationList() {
        return emailNotificationList;
    }

    public void setEmailNotificationList(List<CompanyOfficials> emailNotificationList) {
        this.emailNotificationList = emailNotificationList;
    }

    public String getLoanProducts() {
        return loanProducts;
    }

    public void setLoanProducts(String loanProducts) {
        this.loanProducts = loanProducts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivityNotification() {
        return activityNotification;
    }

    public void setActivityNotification(String activityNotification) {
        this.activityNotification = activityNotification;
    }

    public Double getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(Double appVersion) {
        this.appVersion = appVersion;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    @Override
    public String toString() {
        return "AndroidAppSettings{" +
                "loanProducts='" + loanProducts + '\'' +
                ", address='" + address + '\'' +
                ", activityNotification='" + activityNotification + '\'' +
                '}';
    }
}
