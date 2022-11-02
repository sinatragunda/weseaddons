package com.wese.weseaddons.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.crb.helper.CrbMakerCheckerEntryHelper;
import com.wese.weseaddons.crb.helper.CreditApplicationHelper;
import com.wese.weseaddons.crb.helper.CreditInformationRecordHelper;
import com.wese.weseaddons.crb.helper.LoginCrbHelper;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.utility.ThreadContext;
import com.wese.weseaddons.weselicense.helper.ObjectNodeBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value ="/crb")
public class CrbController {


    @RequestMapping(value = "/test")
    public ObjectNode test(){
        return ObjectNodeBuilder.accountStatusNode(true ,50 ,true);
        //return  Constants.separator+" Successful testing connection to crb for client with loan id ";
    }

    @RequestMapping(value = "/carecord")
    public ObjectNode creditApplication(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestParam("loanId")long loanId){
        ThreadContext.setTenant(tenantIdentifier);
        return CreditApplicationHelper.upload(loanId ,tenantIdentifier);
    }


    @RequestMapping(value = "/makercheckerentry" ,method = RequestMethod.GET)
    public ObjectNode makerCheckerEntry(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestParam("commandId")long commandId){
        return CrbMakerCheckerEntryHelper.upload(commandId ,tenantIdentifier);
    }

    @RequestMapping(value = "/riskscore" ,method = RequestMethod.GET)
    public ObjectNode riskScore(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestParam("clientId")long clientId){
        //return CrbMakerCheckerEntryHelper.upload(commandId ,tenantIdentifier);
        return null ;
    }


    @RequestMapping(value = "/ccrecord" ,method = RequestMethod.GET)
    public ObjectNode creditInformationRecord(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestParam("loanId")long loanId){
        
        return CreditInformationRecordHelper.upload(loanId ,null ,null ,tenantIdentifier);
    
    }



}
