package com.wese.weseaddons.pojo;


import com.wese.weseaddons.enumerations.EMAIL_MESSAGE_TYPE;
import org.json.JSONObject;

public class Email{

	private EMAIL_MESSAGE_TYPE emailMessageType ;
	private String contact ;
	private String emailAddress ;
	private String message ;
	private String body ;
	private String subject ;


	public Email(){}

    public Email(EMAIL_MESSAGE_TYPE emailMessageType, String contact, String emailAddress) {
        this.emailMessageType = emailMessageType;
        this.contact = contact;
        this.emailAddress = emailAddress;
    }

    public Email(String subject ,String message ,String emailAddress ,String contact){
	    this.subject = subject ;
	    this.message = message ;
	    this.emailAddress = emailAddress ;
	    this.contact = contact ;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EMAIL_MESSAGE_TYPE getEmailMessageType() {
        return emailMessageType;
    }

    public void setEmailMessageType(EMAIL_MESSAGE_TYPE emailMessageType) {
        this.emailMessageType = emailMessageType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public JSONObject getJsonObject(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emailAddress" ,emailAddress);
        jsonObject.put("contact" ,contact);
        jsonObject.put("messageType" ,emailMessageType.ordinal());

        return  jsonObject ;
    }

    public JSONObject jsonObject(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("emailAddress" ,emailAddress);
        jsonObject.put("contact" ,contact);
        jsonObject.put("message" ,message);
        jsonObject.put("subject" ,subject);
        return jsonObject;
    }
}