package com.wese.weseaddons.ussd.menuaction.submenuaction;

import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.helper.MonetaryInput;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.menu.submenu.USSDDepositMenu;
import com.wese.weseaddons.ussd.menu.submenu.subsubmenu.USSDLoanProductsMenu;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;

public class DepositMoneyAction implements ExecuteMenuAction{

    private USSDDepositMenu ussdDepositMenu ;

    @Override
    public String execute(Session session){


		ussdDepositMenu = new USSDDepositMenu(session);

		MonetaryInput monetaryInput = session.getMonetaryInput();
    	String phoneNumber = session.getPhoneNumber();
    	int option = monetaryInput.getOption();

    	double amount = monetaryInput.getAmount();

    	String response = String.format("You have to deposit %.2f into account %s successfully\n" ,amount,phoneNumber);
    	return response ;
    }
}
