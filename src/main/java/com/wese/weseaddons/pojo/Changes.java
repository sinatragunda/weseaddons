/*

    Created by Sinatra Gunda
    At 6:04 PM on 6/27/2021

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wese.weseaddons.helper.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class Changes {

    private int additionalshares ;

    @JsonProperty
    private List<Integer> requestedShares = new ArrayList<>();

    public Changes(){}

    public List<Integer> getRequestedShares() {
        return requestedShares;
    }

    public void setRequestedShares(List<Integer> requestedShares) {
        this.requestedShares = requestedShares;
    }

    public int getAdditionalshares() {
        return additionalshares;
    }

    public void setAdditionalshares(int additionalshares) {
        this.additionalshares = additionalshares;
    }

    public static Changes fromHttpResponse(String arg){
        return JsonHelper.serializeFromHttpResponse(new Changes() ,arg);
    }
}
