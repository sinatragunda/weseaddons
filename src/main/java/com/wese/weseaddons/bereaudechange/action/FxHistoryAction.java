package com.wese.weseaddons.bereaudechange.action;



import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser ;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxHistoryDAO;
import com.wese.weseaddons.bereaudechange.pojo.FxHistory;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class FxHistoryAction{

	private String tenant ;
	private FxHistoryDAO fxHistoryDao ;


	public FxHistoryAction(String s){
		this.tenant = s ;
		this.fxHistoryDao = new FxHistoryDAO(s);
	}


	public ObjectNode findAll(){

		List<FxHistory> fxHistoryList = fxHistoryDao.findAll();

		ArrayNode arrayNode = Helper.createArrayNode();

		for(FxHistory f :fxHistoryList){

			arrayNode.add(createNode(f));
		}

		if(fxHistoryList.isEmpty()){
			return null;
		}


		ObjectNode objectNode = Helper.createObjectNode();
		objectNode.putPOJO("pageItems",arrayNode);
		return objectNode ;
	}



	public ObjectNode createNode(FxHistory fxHistory){

		ObjectNode objectNode = Helper.createObjectNode();
		objectNode.put("resourceId" ,fxHistory.getResourceId());
		objectNode.put("auditTrail",fxHistory.getAuditTrail().getCode());
		objectNode.put("authoriser","Not documented");
		objectNode.put("authoriserId" ,"Not documented");
		objectNode.put("date" , TimeHelper.timestampToStringWithTimeForSecond(fxHistory.getDate()));

		AppUser appUser = fxHistory.getAuthorizer();

		if(appUser !=null){
			objectNode.put("authoriser",appUser.getDisplayName());
			objectNode.put("authoriserId" ,appUser.getId());

		}
		return objectNode ;
	
	}


}