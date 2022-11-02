package com.wese.weseaddons.taskmanager.pojo;



import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.EmailNotification;
import com.wese.weseaddons.pojo.Email;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificationGenerator{

	private long id ;
	private String objectType ;
	private long objectIdentifier;
	private String action ;
	private long actor ;
	private boolean isSystemGenerated  ;
	private String notificationContent ;
	private String createdAt ;
	private Email email ;


	public NotificationGenerator(long objectIdentifier ,String objectType ,String action ,String notificationContent ,Email email){
		this.objectType = objectType ;
		this.objectIdentifier = objectIdentifier ;
		this.action = action ;
		this.notificationContent = notificationContent ;
		this.email = email;
	}


	public String getCreatedAt(){

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.dateTimeFormat);
		return localDateTime.format(dateTimeFormatter);
	}

	public Long getId() {
		return id;
	}

	public long getActor() {
		return 3;
	}

	public void setEmail(Email emailNotification) {
		this.email = email ;
	}

	public Email getEmail() {
		return email ;
	}


	public boolean isSystemGenerated() {
		return false;
	}

	public String getAction() {
		return action;
	}

	public long getObjectIdentifier() {

		return objectIdentifier;
	}

	public String getNotificationContent() {
		return notificationContent;
	}

	public String getObjectType() {
		return objectType;
	}

}