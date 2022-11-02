package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FundsActionDAO;
import com.wese.weseaddons.bereaudechange.enumerations.FUNDS_ACTION_TYPE;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FundsAction;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;

import java.util.List;

public class FundsActionAction {

    private String tenant ;
    private FundsActionDAO fundsActionDAO ;
    private boolean includeCharges ;

    public FundsActionAction(String tenant){
        this.tenant = tenant ;
        this.fundsActionDAO = new FundsActionDAO(tenant);
    }

    public FundsActionAction(String tenant ,boolean includeCharges) {
        this.tenant = tenant;
        this.includeCharges = includeCharges ;
        this.fundsActionDAO = new FundsActionDAO(tenant);
    }

    public ObjectNode create(FundsAction fundsAction){

        return fundsActionDAO.create(fundsAction ,includeCharges);

    }

    public ObjectNode find(long id){

        List<FundsAction> fundsActionList = fundsActionDAO.find(id);
        ArrayNode arrayNode = Helper.createArrayNode() ;

        for(FundsAction fundsAction : fundsActionList){
            arrayNode.addPOJO(createNode(fundsAction));
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;

    }

    public ObjectNode createNode(FundsAction fundsAction){

    	AppUser appUser = FxSession.getInstance().getTenantSession(tenant).getAppUser(fundsAction.getAuthoriser().getId());

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id" ,fundsAction.getId());
        objectNode.put("actionType" ,fundsAction.getFundsActionType().getCode());
        objectNode.put("amount" ,fundsAction.getAmount());
        objectNode.put("charges" ,fundsAction.getCharges());
        objectNode.put("authoriser" ,appUser.getDisplayName());

        return objectNode ;
    }
}
