package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.ALLOCATION_TYPE;
import com.wese.weseaddons.pojo.Client;

import java.util.List;

public class FxBulkDeal{

    private long id ;
    private long rootNode ;
    private FxDeal fxDeal ;
    private Client client ;
    private Blotter blotter ;
    private ALLOCATION_TYPE allocationType;
    private List<FxDeal> fxDealList ;

    public FxBulkDeal() {
    }



    public FxBulkDeal(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public ALLOCATION_TYPE getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(int allocationType) {

        this.allocationType = ALLOCATION_TYPE.fromInt(allocationType);

    }

    public FxDeal getFxDeal() {
        return fxDeal;
    }

    public void setFxDeal(FxDeal fxDeal) {
        this.fxDeal = fxDeal;
    }

    public List<FxDeal> getFxDealList() {
        return fxDealList;
    }

    public void setFxDealList(List<FxDeal> fxDealList) {
        this.fxDealList = fxDealList;
    }


    public long getRootNode() {
        return rootNode;
    }

    public void setRootNode(long rootNode) {
        this.rootNode = rootNode;
    }


    public Client getClient() {
        return client;
    }

 
    public void setClient(Client client) {
        this.client = client;
    }


    public Blotter getBlotter() {
        return blotter;
    }

  
    public void setBlotter(Blotter blotter) {
        this.blotter = blotter;
    }
}
