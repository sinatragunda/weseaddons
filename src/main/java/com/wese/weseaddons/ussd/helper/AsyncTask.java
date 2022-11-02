package com.wese.weseaddons.ussd.helper;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import com.wese.weseaddons.networkrequests.RequestBuilder;

import java.util.concurrent.Callable;

public class AsyncTask implements Callable{


    private TreeDataStructure treeDataStructure ;

    @Override
    public Object call(){
    	
    	
		return treeDataStructure;

        //treeDataStructure.call();

    }

    public void loadClientsAsync(){

        String clientEndpoint ="/clients?";

        StringBuilder stringBuilder = new StringBuilder(clientEndpoint);
        String restUrl = stringBuilder.toString();
        //String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,restUrl).makeRequest();



    }


    public AsyncTask(TreeDataStructure treeDataStructure){
        this.treeDataStructure = treeDataStructure ;

    }
}
