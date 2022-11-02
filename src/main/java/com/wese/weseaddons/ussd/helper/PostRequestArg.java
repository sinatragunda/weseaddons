
package com.wese.weseaddons.ussd.helper;


import org.json.JSONObject;

import com.wese.weseaddons.ussd.interfaces.Pojo;

public class PostRequestArg{

	private JSONObject jsonObject;
	private Pojo pojo ;
	private String customMenu ;
	private String customFunctionName ;
	private String airtimePhoneNumber ;
	private Object preReasonId ;
	private Object objectId ;

	public JSONObject getPostData(){

		return jsonObject;
	}

	public Object getObjectId() {
		return objectId;
	}

	public void setObjectId(Object objectId) {
		this.objectId = objectId;
	}

	public Object getPreReasonId() {
		return preReasonId;
	}

	public void setPreReasonId(Object preReasonId) {
		this.preReasonId = preReasonId;
	}

	public void setAirtimePhoneNumber(String airtimePhoneNumber) {
		this.airtimePhoneNumber = airtimePhoneNumber;
	}

	public String getAirtimePhoneNumber() {
		return airtimePhoneNumber;
	}

	public void setPostData(JSONObject jsonObject){
		this.jsonObject = jsonObject;
	}

	public void setPojoRequest(Pojo pojo){
		this.pojo = pojo ;
	}

	public Pojo getPojoRequest(){
		return pojo ;
	}

	public String getCustomFunctionName() {
		return customFunctionName;
	}

	public void setCustomFunctionName(String customFunctionName) {
		this.customFunctionName = customFunctionName;
	}

	public String getCustomMenu() {
		return customMenu;
	}

	public void setCustomMenu(String customMenu) {
		this.customMenu = customMenu;
	}
}