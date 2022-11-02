package com.wese.weseaddons.taskmanager.body;

public class TaskJobRequestBody {

    private String state ;
    private long jobId ;
    private String tenantIdentifier ;

    public TaskJobRequestBody(){}

    public void setState(String state) {
        this.state = state;
    }

    public long getJobId() {
        return jobId;
    }

    public String getState() {
        return state;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }
}
