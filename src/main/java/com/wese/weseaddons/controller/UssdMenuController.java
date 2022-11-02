
///Written by Trevis Gunda 02/07/2019

package com.wese.weseaddons.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wese.weseaddons.ussd.menu.USSDMenuRouter;

@RestController
@RequestMapping(value="/ussd")
public class UssdMenuController {
	
	@RequestMapping(value="/menus")
	public String getUSSDMenu(@RequestParam("sessionId") String sessionId ,@RequestParam("phoneNumber") String phoneNumber ,@RequestParam("serviceCode") String serviceCode ,@RequestParam("text")String text){

		USSDMenuRouter ussdMenuRouter = new USSDMenuRouter(sessionId ,phoneNumber ,serviceCode ,text);
		String menuListing = ussdMenuRouter.routeMenu();
		return menuListing ;

	}

}
