/*Created by Sinatra Gunda
  At 12:34 on 7/24/2020 */

package com.wese.weseaddons.taskmanager.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.taskmanager.dao.TaskActionDAO;
import com.wese.weseaddons.taskmanager.pojo.TaskAction;

import java.util.List;

public class TaskActionAction  {


    private String tenantIdentifier ;
    private TaskActionDAO taskActionDAO ;


    public TaskActionAction(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
        this.taskActionDAO = new TaskActionDAO(tenantIdentifier);
    }


    public ObjectNode create(TaskAction taskAction){

        Long id = taskActionDAO.create(taskAction);

        if(id.equals(0)){
            return Helper.statusNodes(false);
        }

        return Helper.statusNodes(true).put("id" ,id);
    }

    public ObjectNode edit(TaskAction taskAction){

        boolean status = taskActionDAO.edit(taskAction);

        if(status){
            return objectNode(taskAction).put("status" ,true);
        }

        return Helper.statusNodes(status);

    }

    public ObjectNode find(long id){

        TaskAction taskAction = taskActionDAO.find(id);
        return objectNode(taskAction);
    }

    public ObjectNode findAll(){

        List<TaskAction> taskActionList = taskActionDAO.findAll();

        ArrayNode arrayNode = Helper.createArrayNode();

        for(TaskAction taskAction : taskActionList){

            ObjectNode objectNode = objectNode(taskAction);
            arrayNode.addPOJO(objectNode);
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("pageItems" ,arrayNode);

        return objectNode ;
    }

    public ObjectNode objectNode(TaskAction taskAction){

        ObjectNode objectNode = Helper.createObjectNode() ;

        if(taskAction != null){

            objectNode.put("status", true);
            objectNode.put("name",taskAction.getName());
            objectNode.put("id",taskAction.getId());
            objectNode.put("description",taskAction.getDescription());
            objectNode.put("duration" ,taskAction.getDuration());
            objectNode.put("durationType" , taskAction.getDurationType().getCode());
            objectNode.put("taskActionType" ,taskAction.getTaskType().getCode());
            return objectNode ;
        }
        return Helper.statusNodes(false) ;
    }
}
