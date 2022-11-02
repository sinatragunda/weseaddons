package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.CurrencyPairDAO;
import com.wese.weseaddons.bereaudechange.helper.StringHelper;
import com.wese.weseaddons.helper.Helper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@RestController
@CrossOrigin
@RequestMapping(value="/bereaudechange")
public class HelperController {

    @RequestMapping(value ="/fxdealswitch" ,method=RequestMethod.GET)
    public ObjectNode fxDealSwitch(@RequestParam("arg")String arg){
        
        
        String value = StringHelper.getInverseTick(arg);

        /// get exchange rate for new switch deal

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("value",value);
        return objectNode ;

    }
}
