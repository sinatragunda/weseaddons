/*Created by Sinatra Gunda
  At 08:29 on 7/24/2020 */

package com.wese.weseaddons.taskmanager.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;


import java.util.List;

public class TaskArrangement implements PojoInterface {


    private long id ;
    private TaskManager taskManager ;
    private TaskAction taskAction ;
    private List<TaskNote> taskNoteList ;
    private TaskJob taskJob ;


    public TaskArrangement(){}

    public TaskArrangement(TaskManager taskManager, TaskAction taskAction, List<TaskNote> taskNoteList, TaskJob taskJob) {
        this.taskManager = taskManager;
        this.taskAction = taskAction;
        this.taskNoteList = taskNoteList;
        this.taskJob = taskJob;
    }


    @Override
    public Long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskAction getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(TaskAction taskAction) {
        this.taskAction = taskAction;
    }

    public List<TaskNote> getTaskNoteList() {
        return taskNoteList;
    }

    public void setTaskNoteList(List<TaskNote> taskNoteList) {
        this.taskNoteList = taskNoteList;
    }

    public TaskJob getTaskJob() {
        return taskJob;
    }

    public void setTaskJob(TaskJob taskJob) {
        this.taskJob = taskJob;
    }

    @Override
    public String getSchema() {
        return null;
    }
}
