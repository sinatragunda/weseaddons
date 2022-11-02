/*Created by Sinatra Gunda
  At 10:20 on 7/24/2020 */

package com.wese.weseaddons.taskmanager.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.taskmanager.dao.TaskArrangementDAO;
import com.wese.weseaddons.taskmanager.dao.TaskJobDAO;
import com.wese.weseaddons.taskmanager.enumerations.STATE;
import com.wese.weseaddons.taskmanager.pojo.TaskAction;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;
import com.wese.weseaddons.taskmanager.pojo.TaskArrangement;

import java.util.List;


public class TaskArrangementAction {

    private String tenantIdentifier ;
    private TaskArrangementDAO taskArrangementDAO ;

    public TaskArrangementAction(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier;
        this.taskArrangementDAO = new TaskArrangementDAO(tenantIdentifier);
    }

    public ObjectNode create(TaskArrangement taskArrangement){

        Long id = taskArrangementDAO.create(taskArrangement);
        if(id!=null){
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(false);
    }

    public ObjectNode find(long id){

        TaskArrangementDAO taskArrangementDAO = new TaskArrangementDAO(tenantIdentifier);
        TaskArrangement taskArrangement = taskArrangementDAO.find(id);
        return objectNode(taskArrangement);

    }

    public ObjectNode findWhereUser(long userId){

        List<TaskArrangement> taskArrangementList = taskArrangementDAO.findWhereUser(userId);

        ArrayNode arrayNode = Helper.createArrayNode();

        for(TaskArrangement taskArrangement : taskArrangementList){

            ObjectNode objectNode = Helper.createObjectNode();
            objectNode = objectNode(taskArrangement);
            arrayNode.addPOJO(arrayNode);
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("pageItems" ,arrayNode);

        return objectNode ;

    }


    public ObjectNode objectNode(TaskArrangement taskArrangement){

        ObjectNode objectNode = Helper.createObjectNode();

        TaskAction taskAction = taskArrangement.getTaskAction();
        TaskJob taskJob = taskArrangement.getTaskJob();
        TaskManager taskManager = taskArrangement.getTaskManager();

        if(taskArrangement !=null){

            objectNode.put("id", taskArrangement.getId());

            String startDate = TimeHelper.timestampToString(taskManager.getStartDate());
            String dueOn = TimeHelper.timestampToString(taskManager.getDueDate());

            objectNode.put("startDate",startDate);
            objectNode.put("dueOn",dueOn);
            objectNode.put("state",taskJob.getState().getCode());
            objectNode.put("taskActionName",taskAction.getName());
            objectNode.put("taskActionDescription",taskAction.getDescription());
            objectNode.put("assignedTo" ,taskManager.getAssignedTo());
            objectNode.put("assignedBy" ,taskManager.getAssignedBy());
            objectNode.put("accountType" ,taskJob.getAccountType().getCode());
            objectNode.put("accountToActOnId" ,taskJob.getAccountToActOnId());
            objectNode.put("assignedToName" ,taskManager.getAssignedToName());
            objectNode.put("assignedByName" ,taskManager.getAssignedByName());

            return objectNode ;

        }

        return Helper.statusNodes(false);

    }

    public void updateState(TaskJob taskJob){
        Runnable runnable = ()->{
            if(taskJob.getState()== STATE.ASSIGNED){
                taskJob.setState(STATE.OPENED);
                TaskJobDAO taskJobDAO = new TaskJobDAO(tenantIdentifier);
                taskJobDAO.update(taskJob);
            }
        };

        OffshoreThread.run(runnable);

    }
}
