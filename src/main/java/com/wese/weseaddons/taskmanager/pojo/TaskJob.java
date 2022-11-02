package com.wese.weseaddons.taskmanager.pojo;


import com.wese.weseaddons.interfaces.PojoInterface;
import com.wese.weseaddons.taskmanager.enumerations.ACCOUNT_TYPE;
import com.wese.weseaddons.taskmanager.enumerations.STATE;

import javax.persistence.*;


public class TaskJob implements PojoInterface{


    private long id ;
    private long accountToActOnId ;

    private TaskManager taskManager ;

    @Enumerated(EnumType.ORDINAL)
    private STATE state ;

    @Enumerated(EnumType.ORDINAL)
    private ACCOUNT_TYPE accountType ;

    public TaskJob(){}

    public TaskJob(long id){
        this.id = id ;
    }

    public TaskJob(long id ,TaskManager taskManager ,STATE state){
        this.id = id ;
        this.taskManager = taskManager ;
        this.state = state ;
    }


    @Override
    public Long getId() {
        return id;
    }

    public ACCOUNT_TYPE getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType){
        this.accountType = ACCOUNT_TYPE.fromCode(accountType);
    }


    public void setAccountType(int accountType) {

        this.accountType = ACCOUNT_TYPE.fromInt(accountType);

    }

    public void setAccountType(ACCOUNT_TYPE accountType) {
        this.accountType = accountType;
    }

    public void setAccountToActOnId(long accountToActOnId) {
        this.accountToActOnId = accountToActOnId;
    }

    public long getAccountToActOnId() {
        return accountToActOnId;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setId(long id) {
        this.id = id;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public void setState(int state) {
        this.state = STATE.fromInt(state);
    }


    @Override
    public String getSchema() {
        return null;
    }
}
