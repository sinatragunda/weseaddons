package com.wese.weseaddons.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.helper.ResponseBuilder;
import com.wese.weseaddons.batchprocessing.wese.Authenticate;
import com.wese.weseaddons.batchprocessing.wese.LoanProductsRequest;
import com.wese.weseaddons.batchprocessing.wese.OfficeRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value ="/excel")
public class WeseDataController {


    @RequestMapping(value = "/offices")
    public ObjectNode getOffices(@RequestParam("tenant")String tenant){

        System.out.println("Requesting office data now");


        OfficeRequest officeRequest = new OfficeRequest(tenant);
        officeRequest.loadOffices();
        return ResponseBuilder.offices(officeRequest.getOffice());

    }

    @RequestMapping(value = "/loanproducts")
    public ObjectNode loanProducts(@RequestParam("tenant")String tenant){

        System.out.println("Requesting loans data now");

        LoanProductsRequest loanProductsRequest = new LoanProductsRequest();
        loanProductsRequest.loadLoanProducts();
        return ResponseBuilder.loanProducts(loanProductsRequest.getLoanProducts());

    }



    @RequestMapping(value = "/authorise")
    public ObjectNode login(@RequestParam("tenant")String tenant , @RequestParam("username")String username , @RequestParam("password")String password){

        Authenticate authenticate = new Authenticate(username ,password);
        return authenticate.login();

    }





}
