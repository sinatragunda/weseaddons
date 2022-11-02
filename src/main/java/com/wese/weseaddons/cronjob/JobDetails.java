package com.wese.weseaddons.cronjob;

public class JobDetails {

    private String displayName ;
    private long jobId ;
    private boolean active ;

    public JobDetails(long jobId ,String displayName ,boolean active){
        this.active = active ;
        this.displayName = displayName ;
        this.jobId = jobId ;
    }

    public boolean isActive() {
        return active;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getJobId() {
        return jobId;
    }


}
