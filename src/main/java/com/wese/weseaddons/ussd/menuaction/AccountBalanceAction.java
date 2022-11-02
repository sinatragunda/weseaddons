package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;

public class AccountBalanceAction implements ExecuteMenuAction{


    @Override
    public String execute(Session session){

        return "Airtime and bundles function executed test 1";

    }

}