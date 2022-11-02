package com.wese.weseaddons.requests;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.DateComparator;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.Parameters;
import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.pojo.Client;


import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class RequestForNewClients implements RequestDataService {


    private List<Client> clientList ;
    private long newClientsCount = 0;
    private Parameters parameters ;

    private Predicate<Date> newClientsPredicate = (d)->{

        Date dateNow = Helper.dateNow();

        if(d==null){

            return false ;
        }

        DateComparator dateComparator = new DateComparator();
        int result = dateComparator.compare(dateNow ,d);

        return  result != 0? false : true ;

    };

    public RequestForNewClients(Parameters parameters){

        this.parameters = parameters ;
        request();
        sort();
    }


    @Override
    public ObjectNode status(){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("count" ,newClientsCount);
        return objectNode ;

    }


    public void request(){

        ClientRequest clientRequest = new ClientRequest(null ,parameters.getTenantIdentifier());
        clientRequest.loadClientsAsync(parameters.getTenantIdentifier());
        clientList = clientRequest.getClientList();

    }


    public void sort(){

        if(clientList.isEmpty()){
            return ;
        }
        newClientsCount = clientList.stream().map(Client::getActivationDate).filter(newClientsPredicate).count();

    }


}