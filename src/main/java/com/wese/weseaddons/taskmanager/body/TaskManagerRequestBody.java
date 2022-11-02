package com.wese.weseaddons.taskmanager.body;

import java.io.IOException;

public class TaskManagerRequestBody {

    private String startDate ;
    private long assignedTo ;
    private long assignedBy ;
    private long taskActionId ;
    private String tenantIdentifier ;
    private String accountType ;
    private long accountToActOnId ;


    public TaskManagerRequestBody(){}

    public String getAccountType() {

        try{
            if(accountType==null){
                return "Nan";
            }
        }

        catch (Exception i){
            return "Nan";
        }

        return accountType;
    }


    public long getAccountToActOnId() {

        return accountToActOnId;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setAssignedBy(long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public void setAccountToActOnId(long accountToActOnId) {
        this.accountToActOnId = accountToActOnId;
    }

    public long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public long getAssignedBy() {
        return assignedBy;
    }

    public long getTaskActionId() {
        return taskActionId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setTaskActionId(long taskActionId) {
        this.taskActionId = taskActionId;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    @Override
    public String toString(){

        return String.format("Start Date : %s\n : Assigned To : %d\n Assigned By : %d\n",startDate ,assignedTo  ,assignedBy);
    }
}
