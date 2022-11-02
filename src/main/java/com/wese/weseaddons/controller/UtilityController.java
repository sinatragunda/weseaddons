package com.wese.weseaddons.controller;

///Written by Sinatra Gunda
/// 30/06/2019

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.requests.RequestForAccountingData;
import com.wese.weseaddons.requests.RequestForNewClients;
import com.wese.weseaddons.helper.Parameters;
import com.wese.weseaddons.utility.ThreadContext;
import com.wese.weseaddons.utility.chartofaccounts.ChartOfAccountsHelper;
import com.wese.weseaddons.utility.chartofaccounts.action.ChartOfAccountAction;
import com.wese.weseaddons.utility.chartofaccounts.pojo.ChartOfAccountPortfolioMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value ="/utility")
public class UtilityController {


    @RequestMapping(value = "/portfoliomapping",method= RequestMethod.POST)
    public ObjectNode autoPortfolioMapping(@RequestBody List<ChartOfAccountPortfolioMapping> accountPortfolioMappingList){

        ChartOfAccountAction.portfolioMapping(accountPortfolioMappingList);

        return null ;

    }

}
