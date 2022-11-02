package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import org.json.JSONObject;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interactive.CancelLoanParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.networkrequests.RequestBuilder;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;

public class CancelLoanApplicationAction implements ExecuteMenuAction{



    @Override
    public String execute(Session session){


        int loanIdIndex = (int)session.getInteruptId();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/self/loans/");
        stringBuilder.append(session.getLoansList().get(loanIdIndex).getId());
        stringBuilder.append("?command=withdrawnByApplicant");

        String restUrl = stringBuilder.toString();

        int preReasonIndex = (int)session.getPostRequestArg().getPreReasonId();
        String reason = Constants.getCancelLoanReasons().get(preReasonIndex);

        System.out.println("Reason for cancelling loan is "+reason);

        CancelLoanParser cancelLoanParser = new CancelLoanParser(reason);
        JSONObject jsonObject = cancelLoanParser.getJsonObject();

        System.out.println("Json object is "+jsonObject.toString());

        session.getPostRequestArg().setPostData(jsonObject);

        String httpResponse = RequestBuilder.build(REQUEST_METHOD.POST ,restUrl).makeRequest();
        return httpResponse ;
        
    }


    public String response(String arg){

        return "Some response here";

    }
}
