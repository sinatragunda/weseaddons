package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.taskmanager.actions.*;
import com.wese.weseaddons.taskmanager.body.*;
import com.wese.weseaddons.taskmanager.dao.TaskArrangementDAO;
import com.wese.weseaddons.taskmanager.pojo.TaskAction;
import com.wese.weseaddons.taskmanager.pojo.TaskArrangement;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskNote;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/taskmanager")
public class TaskManagerController {


    @RequestMapping(method = RequestMethod.GET ,params = {"userId","tenantIdentifier"})
    public ObjectNode getUserTasks(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("userId")long userId){

        TaskOverview taskOverview = new TaskOverview(tenantIdentifier);
        return taskOverview.getAllTasksForUser(userId);

    }

    @RequestMapping(method = RequestMethod.GET ,params = {"tenantIdentifier" ,"id"})
    public ObjectNode viewTask(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("id")long id){

        // if role == get ,else get where username is bla bla
        TaskArrangementAction taskArrangementAction = new TaskArrangementAction(tenantIdentifier);
        return taskArrangementAction.find(id);

    }


    @RequestMapping(value = "/tasknote" ,method = RequestMethod.POST)
    public ObjectNode createTaskNote(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestBody TaskNote taskNote){

        // if role == get ,else get where username is bla bla

        TaskNoteAction taskNoteAction = new TaskNoteAction(tenantIdentifier);
        return taskNoteAction.create(taskNote);

    }


    @RequestMapping(value = "/taskjob" ,method = RequestMethod.PUT)
    public ObjectNode updateTaskJob(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestBody TaskJob taskJob){

        // if role == get ,else get where username is bla bla

        TaskJobAction taskJobAction = new TaskJobAction(tenantIdentifier);
        taskJobAction.updateState(taskJob);

        String newState = taskJob.getState().getCode();

        ObjectNode objectNode = Helper.statusNodes(true);

        if(newState.equalsIgnoreCase("closed")){

            objectNode.put("closed" ,1);
            return objectNode ;
        }

        objectNode.put("closed",0);
        return objectNode ;

    }

    @RequestMapping(value = "/tasknote" ,method = RequestMethod.GET ,params = {"tenantIdentifier" ,"taskJobId"})
    public ObjectNode getTaskNotesForTaskJob(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestParam("taskJobId") long taskJobId){

        TaskNoteAction taskNoteAction = new TaskNoteAction(tenantIdentifier);
        return taskNoteAction.getTaskNotesForTaskJob(taskJobId);

    }

    @RequestMapping(value = "/tasknote" ,method = RequestMethod.GET ,params = {"tenantIdentifier" ,"id"})
    public ObjectNode viewTaskNote(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id") long id){

        TaskNoteAction taskNoteAction = new TaskNoteAction(tenantIdentifier);
        return taskNoteAction.viewTaskNote(id);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ObjectNode createTaskManager(@RequestParam("tenantIdentifier") String tenantIdentifier, @RequestBody TaskArrangement taskArrangement){

        if(taskArrangement==null){
            return Helper.statusNodes(false);
        }

        TaskArrangementAction taskArrangementAction = new TaskArrangementAction(tenantIdentifier);
        return  taskArrangementAction.create(taskArrangement);

    }


    @RequestMapping(value = "/taskaction" ,method = RequestMethod.POST)
    public ObjectNode createTaskAction(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestBody TaskAction taskAction){

        if(taskAction==null){
            return Helper.statusNodes(false);
        }

        TaskActionAction taskActionAction = new TaskActionAction(tenantIdentifier);
        return taskActionAction.create(taskAction);

    }

    @RequestMapping(value= "/taskaction" ,method = RequestMethod.GET)
    public ObjectNode taskActions(@RequestParam("tenantIdentifier")String tenantIdentifier){

        TaskActionAction taskActionAction = new TaskActionAction(tenantIdentifier);
        ObjectNode objectNode = taskActionAction.findAll();
        return objectNode ;
        
    }
    
    @RequestMapping(value= "/taskaction" ,method = RequestMethod.GET ,params = {"tenantIdentifier" ,"id"})
    public ObjectNode viewTaskAction(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("id")long  id){


        TaskActionAction taskActionAction = new TaskActionAction(tenantIdentifier);
        ObjectNode objectNode = taskActionAction.find(id);
        return objectNode ;
    }
    
    @RequestMapping(value= "/taskaction" ,method = RequestMethod.PUT)
    public ObjectNode editTaskAction(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestBody TaskAction taskAction){

        TaskActionAction taskActionAction = new TaskActionAction(tenantIdentifier);
        return taskActionAction.edit(taskAction);


    }

}
