package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.FxBulkDealBody;
import com.wese.weseaddons.bereaudechange.body.FxDealBody;
import com.wese.weseaddons.bereaudechange.dao.FxBulkDealDAO;
import com.wese.weseaddons.bereaudechange.dao.FxDealDAO;
import com.wese.weseaddons.bereaudechange.pojo.Blotter;
import com.wese.weseaddons.bereaudechange.pojo.FxBulkDeal;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.pojo.Client;


import java.util.List;

public class FxBulkDealAction {

    private String tenant ;
    private List<FxDealBody> fxDealBodyList;
    private FxDealAction fxDealAction ;
    private FxBulkDealDAO fxBulkDealDAO ;
    private long rootNode ;
    private boolean firstTransaction = true ;


    public FxBulkDealAction() {
    }

    public FxBulkDealAction(String tenant) {
        this.tenant = tenant;
        this.fxBulkDealDAO = new FxBulkDealDAO(tenant);
    }


    public ObjectNode create(FxBulkDealBody fxBulkDealBody){

        this.fxDealBodyList = fxBulkDealBody.getFxDealBodyList();

        for(FxDealBody fxDealBody : fxDealBodyList){

            fxDealBody.setTenant(tenant);

            fxDealAction = new FxDealAction(fxDealBody);
            ObjectNode objectNode = fxDealAction.create(true);

            boolean status = objectNode.get("status").asBoolean();

            if(!status){

                return objectNode ;
            }

            long id = objectNode.get("id").asLong();


            if(true){

                if(firstTransaction){

                    firstTransaction = false ;
                    setRootNode(id);

                }

                FxDeal fxDeal = fxDealAction.getFxDeal();
                fxDeal.setId(id);

                Blotter blotter = fxDeal.getBlotter();
                Client client = fxDeal.getClient();
                int allocationType = fxBulkDealBody.getAllocationType();
                fxBulkDealDAO.create(rootNode ,fxDeal, client, blotter ,allocationType);

            }
        }

        return Helper.statusNodes(true).put("id",getRootNode());

    }

    public long getRootNode() {
        return rootNode;
    }

    public void setRootNode(long rootNode) {
        this.rootNode = rootNode;
    }
}
