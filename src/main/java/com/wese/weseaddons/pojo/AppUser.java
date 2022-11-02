package com.wese.weseaddons.pojo;

import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.interfaces.IdIndexedClass;

import java.util.List;

public class AppUser implements IdIndexedClass {

    private long id ;
    private String officeName ;
    private String lastName ;
    private String firstName ;
    private String userName ;
    private long officeId ;
    private String email ;
    private String displayName ;
    private FxCashier fxCashier ;
    private List<UserRole> selectedRolesList ;
    

    public AppUser() {
    }

    public AppUser(long id) {
        this.id = id;
    }

    public AppUser(long id ,long officeId , String username ,String firstname ,String lastname ,String officeName){
        this.id = id ;
        this.officeId = officeId ;
        this.userName = username ;
        this.firstName = firstname ;
        this.lastName = lastname ;
        this.officeName = officeName ;
    }


    public AppUser(long id ,long officeId , String username ,String firstname ,String lastname ,String officeName ,List selectedRolesList ,String email){
        this.id = id ;
        this.officeId = officeId ;
        this.userName = username ;
        this.firstName = firstname ;
        this.lastName = lastname ;
        this.officeName = officeName ;
        this.email = email;
        this.selectedRolesList = selectedRolesList ;
    }

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName(){
        return String.format("%s %s",firstName ,lastName);
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FxCashier getFxCashier() {
        return fxCashier;
    }

    public void setFxCashier(FxCashier fxCashier) {
        this.fxCashier = fxCashier;
    }

    public List<UserRole> getSelectedRolesList() {
        return selectedRolesList;
    }

    public void setSelectedRolesList(List<UserRole> selectedRolesList) {
        this.selectedRolesList = selectedRolesList;
    }
}
