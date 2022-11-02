/*Created by Sinatra Gunda
  At 12:39 on 8/30/2020 */

package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.pojo.Client;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(value="/search")
public class WeseCbsController {


    @RequestMapping(value = "/client")
    public ObjectNode nonAuthenticatedClientSearch(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("clientId") String clientId){
        ClientRequest clientRequest = new ClientRequest(tenantIdentifier);
        Client client = clientRequest.findClient(clientId);
        return client.objectNode() ;

    }
}
