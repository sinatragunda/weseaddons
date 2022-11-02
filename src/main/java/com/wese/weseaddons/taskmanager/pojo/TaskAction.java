package com.wese.weseaddons.taskmanager.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.taskmanager.enumerations.DURATION_TYPE;
import com.wese.weseaddons.taskmanager.enumerations.TASK_TYPE;

import javax.persistence.*;
import java.util.Date;


public class TaskAction implements PojoInterface{


    private long id ;

    private String name ;
    private String description;
    private long duration ;

    @Enumerated(EnumType.ORDINAL)
    private DURATION_TYPE durationType ;

    @Enumerated(EnumType.ORDINAL)
    private TASK_TYPE taskType;

    private AppUser createdBy ;


    public TaskAction(){}


    public TaskAction(long id){
        this.id = id ;
    }

    public TaskAction(long id ,String name ,String description ,DURATION_TYPE durationType ,TASK_TYPE taskType){

        this.id= id ;
        this.name = name ;
        this.description = description ;
        this.durationType = durationType ;
        this.taskType = taskType ;

    }

    public AppUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AppUser createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(long id) {
        this.id = id;
    }
    

    @Override
    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public DURATION_TYPE getDurationType() {
        return durationType;
    }

    public long getDuration() {
        return duration;
    }

    public TASK_TYPE getTaskType() {
        return taskType;
    }


    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setDurationType(DURATION_TYPE durationType) {
        this.durationType = durationType;
    }

    public void setDurationType(int durationType) {
        this.durationType = DURATION_TYPE.fromInt(durationType);
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setTaskType(TASK_TYPE taskType) {
        this.taskType =  taskType;
    }
    public void setTaskType(int taskType) {
        this.taskType = TASK_TYPE.fromInt(taskType);
    }

    @Override
    public String getSchema() {
        return null;
    }
}
