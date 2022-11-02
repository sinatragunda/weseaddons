package com.wese.weseaddons.taskmanager.body;

public class TaskActionRequestBody {

    private long id ;
    private String name ;
    private String description ;
    private String taskAction ;
    private String durationType ;
    private long duration ;
    private String tenantIdentifier ;

    public TaskActionRequestBody(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTaskAction(String taskAction) {
        this.taskAction = taskAction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getDurationType() {
        return durationType;
    }

    public String getTaskAction() {
        return taskAction;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    @Override
    public String toString(){

        return String.format(" %s : %s : %s : %s : %d\n",name ,description ,taskAction ,durationType ,duration);

    }
}
