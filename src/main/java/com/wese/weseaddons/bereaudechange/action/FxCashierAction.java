package com.wese.weseaddons.bereaudechange.action;


import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.FxCashierBody;
import com.wese.weseaddons.bereaudechange.dao.FxCashierDAO;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;


import java.util.List;

public class FxCashierAction {

    private String tenant ;
    private FxCashierDAO fxCashierDao ;

    public FxCashierAction(String tenant) {
        this.tenant = tenant ;
        this.fxCashierDao = new FxCashierDAO(tenant);
    }

    public ObjectNode findAll(){

        List<FxCashier> fxCashierList = fxCashierDao.findAll();

        if(fxCashierList.isEmpty()){
            return Helper.statusNodes(false);
        }

        ArrayNode arrayNode = Helper.createArrayNode();
        for(FxCashier fxCashier : fxCashierList){
            arrayNode.add(createNode(fxCashier));
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.putPOJO("pageItems",arrayNode);
        objectNode.put("status",true);
        return objectNode ;
    }


    public ObjectNode find(long id){

        FxCashier fxCashier = fxCashierDao.find(id);

        if(fxCashier==null){
            return Helper.statusNodes(false);
        }

        ObjectNode objectNode = createNode(fxCashier);
        objectNode.put("status",true);
        return objectNode ;
    }

    public ObjectNode create(FxCashierBody fxCashierBody){

        return fxCashierDao.create(fxCashierBody);

    }

    public ObjectNode createNode(FxCashier fxCashier){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id" ,fxCashier.getId());
        objectNode.put("cashierId",fxCashier.getSystemCashierId());
        objectNode.put("branchName" ,fxCashier.getAppUser().getOfficeName());
        objectNode.put("tellerName" ,fxCashier.getTellerName());
        objectNode.put("activeFrom" , TimeHelper.timestampToStringWithTimeForSecond(fxCashier.getActiveFromDate()));
        objectNode.put("activeTo",TimeHelper.timestampToStringWithTimeForSecond(fxCashier.getActiveToDate()));

        return objectNode ;
    }
}
