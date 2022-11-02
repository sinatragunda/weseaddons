package com.wese.weseaddons.bereaudechange.body;

import java.util.List;

public class FxBulkDealBody {

    private int allocationType ;
    private List<FxDealBody> fxDealBodyList ;

    public FxBulkDealBody() {
    }

    public int getAllocationType() {
        return allocationType;
    }

    public void setAllocationType(int allocationType) {
        this.allocationType = allocationType;
    }

    public List<FxDealBody> getFxDealBodyList() {
        return fxDealBodyList;
    }

    public void setFxDealBodyList(List<FxDealBody> fxDealBodyList) {
        this.fxDealBodyList = fxDealBodyList;
    }


}
