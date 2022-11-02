package com.wese.weseaddons.ussd.menuaction.submenuaction;

import com.wese.weseaddons.ussd.helper.MonetaryInput;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.menu.submenu.subsubmenu.USSDLoanProductsMenu;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.ussd.session.Session;

public class TakeLoanAction implements ExecuteMenuAction{

    private USSDLoanProductsMenu ussdTakeLoanMenu;

    @Override
    public String execute(Session session){
    	
    	MonetaryInput monetaryInput = session.getMonetaryInput();
   	
        return String.format("You want to take a %s loan\n" ,monetaryInput.getAmount());

    }
}
