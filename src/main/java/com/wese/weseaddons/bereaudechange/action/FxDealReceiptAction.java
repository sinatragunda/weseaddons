package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxDealDAO;
import com.wese.weseaddons.bereaudechange.helper.FxReceiptHelper;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;

public class FxDealReceiptAction {

    private long fxDealId ;
    private String tenant ;


    public FxDealReceiptAction(long fxDealId, String tenant) {
        this.fxDealId = fxDealId;
        this.tenant = tenant;
    }

    public ObjectNode find(){

        FxDealDAO fxDealDAO = new FxDealDAO(tenant);
        FxDeal fxDeal = fxDealDAO.find(fxDealId);

        return FxReceiptHelper.getObjectNode(fxDeal ,tenant);
    }
}
