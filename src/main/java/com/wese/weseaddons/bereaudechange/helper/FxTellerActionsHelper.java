package com.wese.weseaddons.bereaudechange.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxTellerAllocationsDAO;
import com.wese.weseaddons.bereaudechange.pojo.FxTellerAllocations;
import com.wese.weseaddons.helper.Helper;

public class FxTellerActionsHelper {

    private String tenant ;
    private FxTellerAllocationsDAO fxTellerAllocationsDAO ;

    public FxTellerActionsHelper(String s){

        this.tenant = s ;
        this.fxTellerAllocationsDAO = new FxTellerAllocationsDAO(tenant);
    }

    public ObjectNode allocate(FxTellerAllocations fxTellerAllocations){

        switch (fxTellerAllocations.gettAccount()){
            case SETTLE:
                break;
            case WITHDRAW:
                return withdraw(fxTellerAllocations);

            case DEPOSIT:
                return deposit(fxTellerAllocations);
        }

        return null ;
    }

    public ObjectNode withdraw(FxTellerAllocations fxTellerAllocations){


        return Helper.statusNodes(true);

    }

    public ObjectNode deposit(FxTellerAllocations fxTellerAllocations){

        fxTellerAllocationsDAO.create(fxTellerAllocations);
        return Helper.statusNodes(true);
    }
}
