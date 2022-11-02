package com.wese.weseaddons.taskmanager.actions;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.taskmanager.body.TaskJobRequestBody;
import com.wese.weseaddons.taskmanager.dao.TaskActionDAO;
import com.wese.weseaddons.taskmanager.dao.TaskJobDAO;
import com.wese.weseaddons.taskmanager.enumerations.DURATION_TYPE;
import com.wese.weseaddons.taskmanager.enumerations.STATE;
import com.wese.weseaddons.taskmanager.enumerations.TASK_TYPE;
import com.wese.weseaddons.taskmanager.pojo.TaskAction;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import javafx.concurrent.Task;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TaskJobAction {

    private long id ;
    private String tenantIdentifier;
    private TaskJobDAO taskJobDAO ;
    private TaskJob taskJob ;


    public TaskJobAction(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
        this.taskJobDAO = new TaskJobDAO(tenantIdentifier);
    }

    public ObjectNode updateState(TaskJob taskJob){

        boolean status = taskJobDAO.update(taskJob);
        return Helper.statusNodes(status);
    }
}
