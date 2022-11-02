package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.ussd.tree.SessionInit;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.PostRequestArg;
import com.wese.weseaddons.ussd.interactive.ApproveSavingsAplicationParser;
import com.wese.weseaddons.ussd.interactive.CreateSavingsAccountParser;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.networkrequests.RequestBuilder;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;

public class CreateSavingsAccountAction implements ExecuteMenuAction {

    @Override
    public String execute(Session session){

        CreateSavingsAccountParser createSavingsAccountParser = new CreateSavingsAccountParser(session);
        JSONObject jsonObject = createSavingsAccountParser.getJsonObject();


        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);

        String restUrl ="/savingsaccounts";
        String httpResponse = httpClientBridge.makeRequest(restUrl);

        reloadSavingsAccounts(session);

        return response(session ,httpResponse);

    }

    public void reloadSavingsAccounts(Session session){

        Runnable runnable = ()->{

            SessionInit.getInstance().loadSavingsAccountsAsync(session);

        };

        runnable.run();
    }

    public String response(Session session ,String r){

        try{

            JSONObject jsonObject = new JSONObject(r);

            if(jsonObject.has("resourceId")){
                return "Savings Account waiting for authorisation";
            }
            long savingsId = jsonObject.getLong("savingsId");

            return approveSavingsRequest(session ,savingsId);
        }

        catch (JSONException jsonException){

            System.out.println(jsonException.getMessage());

        }

        return "Failed to create savings account ,please try again\n";

    }

    public String approveSavingsRequest(Session session ,long savingsId){

        ApproveSavingsAplicationParser approveSavingsAplicationParser = new ApproveSavingsAplicationParser();
        JSONObject jsonObject = approveSavingsAplicationParser.getJsonObject();

        PostRequestArg postRequestArg = new PostRequestArg();
        postRequestArg.setPostData(jsonObject);

        session.setPostRequestArg(postRequestArg);

        StringBuilder stringBuilder = new StringBuilder("/savingsaccounts/");
        stringBuilder.append(savingsId);
        stringBuilder.append("?command=approve");
        String restUrl = stringBuilder.toString();
        String httpResponse = RequestBuilder.build(REQUEST_METHOD.POST ,restUrl).makeRequest();


        try{

            JSONObject jsonObject1 = new JSONObject(httpResponse);
            boolean approved = jsonObject1.getJSONObject("changes").getBoolean("approved");
            String value = jsonObject1.getJSONObject("changes").getString("value");

            if(approved){

                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder.append("Your savings application has been approved\n");
                stringBuilder1.append(String.format("Your savings id is %d\n",savingsId));
                stringBuilder1.append(Constants.backRequest);
                return stringBuilder1.toString();
            }

            return value ;
        }

        catch (JSONException j){

            System.out.println(j.getMessage());
        }

        return null ;

    }
}
