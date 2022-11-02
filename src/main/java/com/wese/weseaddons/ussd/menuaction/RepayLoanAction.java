package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import org.json.JSONObject;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.PostRequestArg;
import com.wese.weseaddons.ussd.interactive.RepayLoanParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.networkrequests.RequestBuilder;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Loan;

public class RepayLoanAction implements ExecuteMenuAction{

    @Override
    public String execute(Session session){

        Constants.setFlagsOff(session);

        String stringAmount = session.getOption();

        double transactionAmount = Double.parseDouble(stringAmount);
        int index = session.getExecuteIndex();

        Client client = session.getClient();
        Loan loans = null ;
        
        try {  
        	
        	loans = session.getLoansList().get(index);
            	
        }
        
        catch(Exception exception) {
        	
        	System.out.println(exception.getMessage());
        }
        


        StringBuilder stringBuilder = new StringBuilder("/loans/");
        stringBuilder.append(String.valueOf(loans.getId()));
        stringBuilder.append("/transactions?command=repayment");


        String restUrl = stringBuilder.toString();
        String note = client.getDisplayName()+" Repayment" ;

        RepayLoanParser repayLoanParser = new RepayLoanParser(transactionAmount ,note);
        JSONObject httpRequestJsonObject = repayLoanParser.getJsonObject();

        session.getPostRequestArg().setPostData(httpRequestJsonObject);

        String httpResponse = RequestBuilder.build(REQUEST_METHOD.POST ,restUrl).makeRequest();
        return httpResponse ;


    }
}
