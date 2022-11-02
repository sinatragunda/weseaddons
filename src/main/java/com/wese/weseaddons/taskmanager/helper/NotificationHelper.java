package com.wese.weseaddons.taskmanager.helper;

import com.wese.weseaddons.enumerations.EMAIL_MESSAGE_TYPE;
import com.wese.weseaddons.pojo.Email;
import com.wese.weseaddons.taskmanager.actions.NotificationAction;
import com.wese.weseaddons.taskmanager.body.NotificationRequestBody;
import com.wese.weseaddons.taskmanager.enumerations.NOTIFICATION_EVENT_TYPE;
import com.wese.weseaddons.taskmanager.pojo.NotificationGenerator;
import com.wese.weseaddons.taskmanager.pojo.TaskArrangement;
import com.wese.weseaddons.taskmanager.pojo.TaskManager;

public class NotificationHelper {

    private long objectId ;
    private Object object ;
    private String code ;
    private NotificationGenerator notificationGenerator ;

    public NotificationHelper(String code ,long objectId){
        this.objectId = objectId ;
        this.code = code ;
    }

    public NotificationGenerator generateNotificationType(){

        NOTIFICATION_EVENT_TYPE notificationEventType = NOTIFICATION_EVENT_TYPE.fromCode(code);

        Email email = new Email();


        switch(notificationEventType){

            case CLOSED_TASK:
                email.setEmailMessageType(EMAIL_MESSAGE_TYPE.CLOSED_TASK);
                this.notificationGenerator = new NotificationGenerator(objectId ,"taskmanager" ,"taskFinished","A task has been marked as finished" ,email);

                break;

            case OVERDUE_TASK:
                this.notificationGenerator = new NotificationGenerator(objectId ,"taskmanager","overdueTask" ,"A task is overdue for submission but status is still pending" ,null);
                break ;

            case FEEDBACK_ADDED_TASK:
                this.notificationGenerator = new NotificationGenerator(objectId,"tasknote","feedbackAdded" ,"A task you a following has been a added a review to" ,null);
                break;

            case ASSIGNED_TASK:
                email.setEmailMessageType(EMAIL_MESSAGE_TYPE.ALLOCATED_TASK);
                this.notificationGenerator = new NotificationGenerator(objectId ,"taskmanager" ,"assigned" ,"A new task has been assigned to you" ,email);
                break;

            case MANAGE_TASK:
                this.notificationGenerator = new NotificationGenerator(objectId ,"taskmanager","createdtask" ,"You have assigned a new task successufully" ,null);
                break;

            case REVERSE_FX_DEAL:
                this.notificationGenerator = new NotificationGenerator(objectId ,"fxdeal" ,"reversefxdeal" ,"An Fx Deal has been submitted for reversal" ,null);
                break;

        }

        return notificationGenerator ;

    }


    public static NotificationAction createNotification(TaskArrangement taskArrangement , long userId , boolean isAssigner , String tenantIdentifier){

        NotificationRequestBody notificationRequestBody = new NotificationRequestBody();
        notificationRequestBody.setTenantIdentifier(tenantIdentifier);
        notificationRequestBody.setUserId(userId);
        notificationRequestBody.setCode("Assigned");

        if(isAssigner){

            notificationRequestBody.setCode("Manage");
        }


        TaskManager taskManager = taskArrangement.getTaskManager();

        notificationRequestBody.setObjectIdentifier(taskManager.getId());
        NotificationAction notificationAction = new NotificationAction(notificationRequestBody);
        notificationAction.createNotification();
        return  notificationAction ;
    }
}
