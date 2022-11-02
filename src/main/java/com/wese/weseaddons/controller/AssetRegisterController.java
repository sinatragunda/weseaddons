
package com.wese.weseaddons.controller ;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.assetregister.action.NodeListingAction;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value ="/assetregister")
public class AssetRegisterController{

    @RequestMapping(value ="/list",method = RequestMethod.GET)
    public ObjectNode list(@RequestParam("type")int type){

        NodeListingAction nodeListingAction = new NodeListingAction();
        return nodeListingAction.getList(type);

    }



}