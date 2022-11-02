package com.wese.weseaddons.taskmanager.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.taskmanager.enumerations.STATE;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


public class TaskNote implements PojoInterface{

    private long id ;
    private String note ;
    private long updatedDate ;
    private AppUser postedBy ;
    private TaskJob taskJob ;


    public TaskNote(){}

    public void setPostedBy(AppUser postedBy) {
        this.postedBy = postedBy;
    }

    public AppUser getPostedBy() {
        return postedBy;
    }


    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getNote() {
        return note;
    }

    public TaskJob getTaskJob() {
        return taskJob;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTaskJob(TaskJob taskJob) {
        this.taskJob = taskJob;
    }

    @Override
    public String getSchema() {
        return "m_task_note";
    }
}
