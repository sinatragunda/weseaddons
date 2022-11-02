package com.wese.weseaddons.taskmanager.actions;

import com.wese.weseaddons.helper.EmailNotification;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.pojo.Email;
import com.wese.weseaddons.taskmanager.body.NotificationRequestBody;
import com.wese.weseaddons.taskmanager.dao.NotificationGeneratorDAO;
import com.wese.weseaddons.taskmanager.helper.NotificationHelper;
import com.wese.weseaddons.taskmanager.pojo.NotificationGenerator;

public class NotificationAction {

    private NotificationRequestBody notificationRequestBody ;

    public NotificationAction(NotificationRequestBody notificationRequestBody){

        this.notificationRequestBody = notificationRequestBody ;

    }

    public void createNotification(){

        NotificationHelper notificationHelper = new NotificationHelper(notificationRequestBody.getCode() ,notificationRequestBody.getObjectIdentifier());
        NotificationGenerator notificationGenerator = notificationHelper.generateNotificationType();
        NotificationGeneratorDAO notificationGeneratorDAO = new NotificationGeneratorDAO(notificationGenerator ,notificationRequestBody);
        notificationGeneratorDAO.createNotification();
        //emailNotification(notificationGenerator);

    }
}
