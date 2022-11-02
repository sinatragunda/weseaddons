package com.wese.weseaddons.controller;

///Written by Sinatra Gunda 1 / 28 / 2020

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.dashboard.action.ClientDashboardAction;
import com.wese.weseaddons.dashboard.action.LoansDashboardAction;
import com.wese.weseaddons.helper.Parameters;
import com.wese.weseaddons.requests.RequestForNewClients;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@RestController
@CrossOrigin
@RequestMapping(value ="/dashboard")
public class DashboardController {


    @RequestMapping(value="/clientsstats", method = RequestMethod.GET)
    public ObjectNode newClient(@RequestParam("tenantIdentifier")String tenantIdentifier){

        return ClientDashboardAction.clientStats(tenantIdentifier);

    }

    @RequestMapping(value="/portfolioAtRisk", method = RequestMethod.GET)
    public ObjectNode portfolioAtRisk(@RequestParam("tenantIdentifier")String tenantIdentifier){

        return LoansDashboardAction.portfolioAtRisk(tenantIdentifier);

    }

    @RequestMapping(value="/agingDetails", method = RequestMethod.GET)
    public ObjectNode agingDetails(@RequestParam("tenantIdentifier")String tenantIdentifier){

        return LoansDashboardAction.agingDetails(tenantIdentifier);

    }

    @RequestMapping(value="/loansDistrubution", method = RequestMethod.GET)
    public ObjectNode agingDetails(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("branchId")long branchId){

        return LoansDashboardAction.loansDistrubution(tenantIdentifier,branchId);

    }

    @RequestMapping(value="/loansSector", method = RequestMethod.GET)
    public ObjectNode loansSector(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("branchId")long branchId){

        return LoansDashboardAction.loansSector(tenantIdentifier,branchId);

    }

    @RequestMapping(value="/genderDistrubution", method = RequestMethod.GET)
    public ObjectNode genderDistrubution(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("branchId")long branchId){

        return LoansDashboardAction.genderDistrubution(tenantIdentifier,branchId);

    }


   
}


//sapiens Rubau Noah and Harie
