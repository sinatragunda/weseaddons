package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.ussd.tree.SessionInit;

public class AirtimeBundlesAction implements ExecuteMenuAction{


    @Override
    public String execute(Session session){

        return "Airtime and bundles function executed test 1";

    }

}
