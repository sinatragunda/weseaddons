package com.wese.weseaddons.taskmanager.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.errors.springexceptions.DependantNull;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.taskmanager.dao.*;
import com.wese.weseaddons.taskmanager.enumerations.STATE;
import com.wese.weseaddons.taskmanager.helper.TaskArrangmentComparator;
import com.wese.weseaddons.taskmanager.pojo.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaskOverview {


    private String tenantIdentifier ;
    private List<TaskManager> taskManagerList ;
    private List<TaskArrangement> taskArrangementList ;
    private TaskManagerDAO taskManagerDAO ;
    private TaskActionDAO taskActionDAO ;
    private TaskArrangementDAO taskArrangementDAO;

    private TaskJobDAO taskJobDAO ;


    private Predicate<TaskArrangement> jobsNotClosed = (e)->{
        TaskJob t = e.getTaskJob();
        if(t.getState()!= STATE.CLOSED){
            return true ;
        }

        return false ;
    };

    private Predicate<TaskArrangement> jobsCompleted = (e)->{
        
        TaskJob t = e.getTaskJob();
        if(t!=null) {
            if(t.getState()== STATE.CLOSED){
                return true ;
            }
        }

        return false ;
    };

    private Predicate<TaskArrangement> overDueJobs = (e)->{

        TaskManager taskManager = e.getTaskManager();
        long dueDateEpoch = taskManager.getDueDate();
        Date dueDate = TimeHelper.timestampToDate(dueDateEpoch);
        Date dateNow = TimeHelper.dateNow(Constants.zimbabweTimeZone);

        if(dueDate.after(dateNow)){
            return true ;
        }

        return false ;
    };

    public Predicate<TaskArrangement> newJobs = (e)->{

        TaskJob t = e.getTaskJob();
        if(t!=null) {
            if(t.getState()== STATE.ASSIGNED){
                return true ;
            }
        }
        return false ;
    };


    private Predicate<TaskArrangement> dueTodayJob = (e)->{

        TaskManager taskManager = e.getTaskManager();
        long dueDateEpoch = taskManager.getDueDate();
        Date dateNow = TimeHelper.dateNow(Constants.zimbabweTimeZone);
        Date dueDate = TimeHelper.timestampToDate(dueDateEpoch);

        return TimeHelper.compareDates(dateNow ,dueDate);

    };

    public TaskOverview(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
        this.taskArrangementDAO = new TaskArrangementDAO(tenantIdentifier);
    }



    public ObjectNode getAllTasksForUser(long userId){


        taskManagerList = new ArrayList<>();
        taskArrangementList = new ArrayList<>();

        ////some functions to see if someone is username etc
        taskArrangementList = taskArrangementDAO.findWhereUser(userId);

        if(taskArrangementList.isEmpty()){
           throw new DependantNull() ;
        }

        ArrayNode arrayNode = Helper.createArrayNode();

        TaskArrangmentComparator taskArrangmentComparator = new TaskArrangmentComparator();

        taskArrangementList.sort(taskArrangmentComparator);

        for(TaskArrangement taskArrangement : taskArrangementList){

            ObjectNode objectNode = createTaskNode(taskArrangement);
            if(objectNode!=null){
                arrayNode.addPOJO(objectNode);
            }
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.putPOJO("pageItems",arrayNode);


        long jobsOverdueCount = taskArrangementList.stream().filter(overDueJobs).count();
        long jobsDueTodayCount = taskArrangementList.stream().filter(dueTodayJob).count() ;
        long jobsCompletedCount = taskArrangementList.stream().filter(jobsCompleted).count();
        long newJobsCount = taskArrangementList.stream().filter(newJobs).count() ;

        objectNode.put("newJobsCount",newJobsCount);

        if(newJobsCount != 0){

            List<TaskArrangement> newTaskJobsList = taskArrangementList.stream().filter(newJobs).collect(Collectors.toList());

            ArrayNode arrayNode1 = Helper.createArrayNode();

            for(TaskArrangement taskArrangement : taskArrangementList){

                ObjectNode objectNode1 = createTaskNode(taskArrangement);
                if(objectNode1!=null){
                    arrayNode1.addPOJO(objectNode1);
                }
            }

            objectNode.putPOJO("newJobItems",arrayNode1);

        }

        objectNode.put("overdue" ,jobsOverdueCount);
        objectNode.put("dueToday" ,jobsDueTodayCount);
        objectNode.put("completed" ,jobsCompletedCount);

        return objectNode ;

    }


    public TaskJob getTaskJob(TaskArrangement taskArrangement){

        taskJobDAO = new TaskJobDAO(tenantIdentifier);
        long id = taskArrangement.getTaskJob().getId();
        TaskJob taskJob = taskJobDAO.findWhere("task_manager_id",String.valueOf( id));
        return taskJob ;
    }


    public TaskAction getTaskAction(TaskArrangement taskArrangement){

        long id = taskArrangement.getTaskAction().getId();
        taskActionDAO = new TaskActionDAO(tenantIdentifier);
        TaskAction taskAction = taskActionDAO.findWhere(id);
        return taskAction ;
    }


    public ObjectNode createTaskNode(TaskArrangement taskArrangement){

        ObjectNode objectNode = Helper.createObjectNode();
        try{
            TaskJob taskJob = taskArrangement.getTaskJob();
            TaskAction taskAction = taskArrangement.getTaskAction();
            TaskManager taskManager = taskArrangement.getTaskManager();

            objectNode.put("id",taskArrangement.getId());
            objectNode.put("taskJobId",taskJob.getId());

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
        }
        catch (Exception e){
            return null ;
        }

        return objectNode ;
    }
}
