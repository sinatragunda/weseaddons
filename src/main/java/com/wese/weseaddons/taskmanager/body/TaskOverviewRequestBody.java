package com.wese.weseaddons.taskmanager.body;

public class TaskOverviewRequestBody {

    private String username ;
    private String tenantIdentifier ;
    private long taskId ;
    private long assignedTo ;
    private long userId ;

    public TaskOverviewRequestBody(){}


    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public void setAssignedTo(long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public long getAssignedTo() {
        return assignedTo;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString(){
        return String.format("Tenant Identifier is %s ",tenantIdentifier);
    }
}
