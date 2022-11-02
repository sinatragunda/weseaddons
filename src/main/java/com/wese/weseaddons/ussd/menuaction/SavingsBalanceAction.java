package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.ussd.tree.TreeDataStructure ;

import java.util.List;

public class SavingsBalanceAction implements ExecuteMenuAction{


    @Override
    public String execute(Session session){

        if(session==null) {
            System.err.println("This is still null son");
        }

        List<SavingsAccount> savingsAccountList = session.getClientSavingsAccountList();

        if(savingsAccountList.isEmpty()){
            return Constants.noValidAccount;
        }

        StringBuilder stringBuilder = new StringBuilder(Constants.connection);

        for(int i=0 ; i < savingsAccountList.size() ;++i){

            int count = i+1;
            String accountNo = savingsAccountList.get(i).getAccountNumber();
            String currencyCode = savingsAccountList.get(i).getCurrencyCode();
            double balance = savingsAccountList.get (i).getAccountBalance().doubleValue();
            stringBuilder.append(String.format("%s : %s %.2f\n",accountNo ,currencyCode ,balance));

        }

        stringBuilder.append(Constants.backRequest);

        return stringBuilder.toString();

    }
}
