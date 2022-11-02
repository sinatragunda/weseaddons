package com.wese.weseaddons.taskmanager.body;

public class TaskNoteRequestBody {

    private String note ;
    private long taskJobId ;
    private long postedBy ;
    private String tenantIdentifier;
    private long taskNoteId ;

    public TaskNoteRequestBody(){}


    public long getTaskNoteId() {
        return taskNoteId;
    }

    public void setTaskNoteId(long taskNoteId) {
        this.taskNoteId = taskNoteId;
    }

    public long getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(long postedBy) {
        this.postedBy = postedBy;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public long getTaskJobId() {
        return taskJobId;
    }

    public void setTaskJobId(long taskJobId) {
        this.taskJobId = taskJobId;
    }

    @Override
    public String toString(){

        return String.format("Data to post is %s : %s\n",tenantIdentifier ,note);
    }

}
