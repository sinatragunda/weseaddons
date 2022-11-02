package com.wese.weseaddons.taskmanager.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.concurrent.ConsumerThread;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.taskmanager.body.NotificationRequestBody;
import com.wese.weseaddons.taskmanager.dao.TaskJobDAO;
import com.wese.weseaddons.taskmanager.dao.TaskNoteDAO;
import com.wese.weseaddons.taskmanager.enumerations.STATE;
import com.wese.weseaddons.taskmanager.pojo.TaskJob;
import com.wese.weseaddons.taskmanager.pojo.TaskNote;

import java.util.List;
import java.util.function.Consumer;

public class TaskNoteAction {

    private String tenantIdentifier;
    private TaskJob taskJob ;
    private TaskNoteDAO taskNoteDAO ;
    private NotificationRequestBody notificationRequestBody ;

    private Consumer<Long> updateTaskJob = (e)->{

        TaskJobDAO taskJobDAO = new TaskJobDAO(tenantIdentifier);

        TaskJob taskJob = taskJobDAO.find(e);
        taskJob.setState(STATE.FEEDBACK);
        taskJobDAO.update(taskJob);
    };

    public TaskNoteAction(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
        this.taskNoteDAO = new TaskNoteDAO(tenantIdentifier);

    }



    public ObjectNode create(TaskNote taskNote){

        Long taskJobId = null ;

        if(taskNote !=null){

            taskJobId = taskNote.getTaskJob().getId();
            ConsumerThread.run(updateTaskJob ,taskJobId);

            Long taskNoteId = taskNoteDAO.create(taskNote);
            if(taskNoteId!=null){
                taskNote.setId(taskNoteId);
                Runnable runnable = createNotification(taskNote);
                OffshoreThread.run(runnable);
            }

        }

        return getTaskNotesForTaskJob(taskJobId);
    }

    public ObjectNode viewTaskNote(long id){

        TaskNoteDAO taskNoteDAO = new TaskNoteDAO(tenantIdentifier);
        TaskNote taskNote = taskNoteDAO.find(id);

        if(taskNote==null){

            return Helper.statusNodes(false);
        }

        return objectNode(taskNote);
    }

    public ObjectNode objectNode(TaskNote taskNote){

        String updatedDate = TimeHelper.timestampToStringWithTimeForSecond(taskNote.getUpdatedDate());

        System.err.println(updatedDate);

        ObjectNode objectNode1 = Helper.createObjectNode();
        objectNode1.put("note" ,taskNote.getNote());
        objectNode1.put("sentBy",taskNote.getPostedBy().getDisplayName());
        objectNode1.put("sentTime",updatedDate);
        objectNode1.put("taskJobId" ,taskNote.getTaskJob().getId());

        return objectNode1 ;
    }



    public ObjectNode getTaskNotesForTaskJob(long jobId){


        TaskNoteDAO taskNoteDAO = new TaskNoteDAO(tenantIdentifier);

        List<TaskNote> taskNoteList = taskNoteDAO.findWhereTaskJob(jobId);

        if(taskNoteList.isEmpty()){

            return Helper.statusNodes(false);

        }

        ObjectNode objectNode = Helper.createObjectNode();
        ArrayNode arrayNode = Helper.createArrayNode();

        for(TaskNote taskNote : taskNoteList){

            ObjectNode objectNode1 = objectNode(taskNote);
            arrayNode.addPOJO(objectNode1);
        }

        objectNode.put("status",true);
        objectNode.putPOJO("pageItems" ,arrayNode);

        return objectNode ;

    }

    public Runnable createNotification(TaskNote taskNote){

        Runnable runnable = ()->{

            this.notificationRequestBody = new NotificationRequestBody();
            notificationRequestBody.setUserId(taskNote.getPostedBy().getId());
            notificationRequestBody.setTenantIdentifier(tenantIdentifier);
            notificationRequestBody.setCode("Feedback");
            notificationRequestBody.setObjectIdentifier(taskNote.getId());

            NotificationAction notificationAction = new NotificationAction(notificationRequestBody);
            notificationAction.createNotification();
        };

        return runnable ;
    }
}
