package com.wese.weseaddons.dashboard.action ;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.dashboard.helper.ClientDashboardHelper;
import com.wese.weseaddons.helper.Helper;

public class ClientDashboardAction{

    public static ObjectNode clientStats(String tenantIdentifier){

		ObjectNode objectNode = Helper.createObjectNode();

		int data[] = ClientDashboardHelper.clientStats(tenantIdentifier);

		objectNode.put("Day",data[0]);
		objectNode.put("Week",data[1]);
		objectNode.put("Month",data[2]);
		objectNode.put("Year",data[3]);
		return objectNode ;

	}

}