/* Created on 30/09/2020
by Sinatra Gunda 
*/


// This file contains all kinds of available configuration for applications running weseaddons 

package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.config.action.AndroidAppSettingsAction;
import com.wese.weseaddons.config.pojo.AndroidAppSettings;
import com.wese.weseaddons.helper.Helper;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value="/androidconfig")
public class AndroidAppConfigController {


	@RequestMapping(method = RequestMethod.GET)
	public ObjectNode appSettings(@RequestParam("tenantIdentifier")String tenantIdentifier){

		AndroidAppSettingsAction androidAppSettingsAction = new AndroidAppSettingsAction(tenantIdentifier);
		return androidAppSettingsAction.find();

	}


	@RequestMapping(method = RequestMethod.PUT)
	public ObjectNode createOrUpdate(@RequestParam("tenantIdentifier")String tenantIdentifier , @RequestBody AndroidAppSettings androidAppSettings){

		AndroidAppSettingsAction androidAppSettingsAction = new AndroidAppSettingsAction(tenantIdentifier);
		return androidAppSettingsAction.update(androidAppSettings);

	}

}