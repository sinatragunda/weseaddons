package com.wese.weseaddons.taskmanager.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class TaskManager implements PojoInterface{


    private long id ;
    private long startDate ;
    private long dueDate ;
    private long assignedTo ;
    private long assignedBy ;
    private String assignedToName ;
    private String assignedByName ;
    private TaskAction taskAction ;


    public TaskManager(){}

    public TaskManager(long id){
        this.id = id ;
    }

    public TaskManager(long id ,long createdDate ,long assignedBy ,long assignedTo){
        this.id = id ;
        this.startDate = createdDate ;
        this.assignedBy = assignedBy ;
        this.assignedTo = assignedTo ;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public void setAssignedByName(String assignedByName) {
        this.assignedByName = assignedByName;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public String getAssignedByName() {
        return assignedByName;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setAssignedTo(long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public long getAssignedBy() {
        return assignedBy;
    }

    public long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedBy(long assignedBy) {
        this.assignedBy = assignedBy;
    }

    public long getDueDate() {
        return dueDate;
    }

    public TaskAction getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(TaskAction taskAction) {
        this.taskAction = taskAction;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long createdDate) {
        this.startDate = createdDate;
    }

    @Override
    public String getSchema() {
        return null;
    }
}
