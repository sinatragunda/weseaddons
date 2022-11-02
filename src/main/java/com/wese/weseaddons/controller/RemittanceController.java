package com.wese.weseaddons.controller;

/// Written by Sinatra Gunda
/// 01/07/2019

import com.fasterxml.jackson.databind.node.ObjectNode;

import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.remittance.action.RemittanceRecieverAction;
import com.wese.weseaddons.remittance.action.RemittanceSenderAction;
import com.wese.weseaddons.remittance.action.RemittanceTransactionAction;
import com.wese.weseaddons.remittance.pojo.RemittanceTransaction;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value = "/remittance")
public class RemittanceController {


    @RequestMapping(method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ObjectNode rxRemit(@RequestParam("tenantIdentifier") String tenant, @RequestBody RemittanceTransaction remittanceTransaction) {

        return RemittanceSenderAction.send(remittanceTransaction, tenant);

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ObjectNode rxRecieve(@RequestParam("tenantIdentifier") String tenant ,@RequestBody RemittanceTransaction remittanceTransaction) {

        return RemittanceRecieverAction.recieve(remittanceTransaction ,tenant);
        //return RemittanceRecieverAction.recieve(null ,tenant);


    }

    @RequestMapping(method = RequestMethod.GET ,params = {"tenantIdentifier" ,"key"})
    public ObjectNode rxVerify(@RequestParam("tenantIdentifier") String tenant ,@RequestParam("key")String key) {

        return RemittanceRecieverAction.verify(key ,tenant);

    }


    @RequestMapping(method = RequestMethod.PUT ,params = {"tenantIdentifier" ,"key"})
    public ObjectNode rxRecieve(@RequestParam("tenantIdentifier") String tenant ,@RequestParam("key")long id ,@RequestParam("state")int state) {

        RemittanceTransactionAction remittanceTransactionAction= new RemittanceTransactionAction(tenant);
        return remittanceTransactionAction.updateTransactionState(id,state);

    }

    @RequestMapping(method = RequestMethod.GET ,params = "tenantIdentifier")
    public ObjectNode rxRemittanceTransactions(@RequestParam("tenantIdentifier") String tenant) {

        RemittanceTransactionAction remittanceTransactionAction= new RemittanceTransactionAction(tenant);
        return remittanceTransactionAction.findAll();

    }

    @RequestMapping(method = RequestMethod.GET ,params = {"tenantIdentifier","id"})
    public ObjectNode rxTransaction(@RequestParam("tenantIdentifier") String tenant ,@RequestParam("id") long id) {

        RemittanceTransactionAction remittanceTransactionAction= new RemittanceTransactionAction(tenant);
        return remittanceTransactionAction.find(id);

    }


}
