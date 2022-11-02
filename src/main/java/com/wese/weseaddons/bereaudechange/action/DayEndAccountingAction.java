package com.wese.weseaddons.bereaudechange.action;


import com.wese.weseaddons.pojo.AppUser ;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.DayEndAccountBody;
import com.wese.weseaddons.bereaudechange.helper.DayEndAccountingHelper;

public class DayEndAccountingAction {

    private String tenant ;
    private long officerId ;

    public DayEndAccountingAction(String tenant, long officerId) {
        this.tenant = tenant;
        this.officerId = officerId;
    }

    public ObjectNode getSummary(){

        DayEndAccountingHelper dayEndAccountingHelper = new DayEndAccountingHelper(tenant ,officerId);
        return dayEndAccountingHelper.summary(null);
    }

    public ObjectNode getSummary(DayEndAccountBody dayEndAccountBody){

        DayEndAccountingHelper dayEndAccountingHelper = new DayEndAccountingHelper(tenant ,officerId);
        return dayEndAccountingHelper.summary(dayEndAccountBody);
    }
}
