/*

    Created by Sinatra Gunda
    At 9:34 AM on 6/22/2021

*/
package com.wese.weseaddons.networkrequests;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.*;
import com.wese.weseaddons.utility.ThreadContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SharesRequest {

    String tenantIdentifier = ThreadContext.getTenant();

    public ShareAccount shareAccount(Long id){

        String url = String.format("accounts/share/%d",id);
        String response = RequestBuilder.build(REQUEST_METHOD.GET ,url).makeRequest();
        ShareAccount shareAccount = ShareAccount.fromHttpResponse(response);
        return shareAccount ;

    }


    public ShareProduct shareProduct(Long id){

        String url = String.format("products/share/%d" ,id);
        String response = RequestBuilder.build(REQUEST_METHOD.GET ,url).makeRequest();
        ShareProduct shareProduct = ShareProduct.fromHttpResponse(response);
        return shareProduct ;

    }

    // added 19/03/2022
    public static MakerCheckerEntry reverseShareTransaction(Long transactionId,String requestBody){

        String url = String.format("shareaccounttransaction/%d?command=undo" ,transactionId);
        String response = RequestBuilder.build(REQUEST_METHOD.POST ,url ,requestBody).makeRequest();
        MakerCheckerEntry makerCheckerEntry = MakerCheckerEntry.fromHttpResponse(response);
        return makerCheckerEntry ;

    }

    public List<ShareAccount> clientShareAccounts(Long clientId){

        String url = String.format("clients/%d/accounts?fields=shareAccounts",clientId);
        String response = RequestBuilder.build(REQUEST_METHOD.GET ,url).makeRequest();
        List<ShareAccount> shareAccountsList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("shareAccounts");
            for(int i=0 ; i < jsonArray.length() ;++i){
                String arg = jsonArray.get(i).toString();
                ShareAccount shareAccount = ShareAccount.fromHttpResponse(arg);
                shareAccountsList.add(shareAccount);
            }
        }
        catch (JSONException j){

        }
        return shareAccountsList ;
    }

    public MakerCheckerEntry applyAdditionalShares(Long accountNumber ,int numberOfShares ,String date){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestedDate", date);
        jsonObject.put("requestedShares", numberOfShares);
        jsonObject.put("locale", "en");
        jsonObject.put("dateFormat", "dd MMMM yyyy");

        String url = String.format("accounts/share/%d?command=applyadditionalshares" ,accountNumber);

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);
        String response = httpClientBridge.makeRequest(url);
        return MakerCheckerEntry.fromHttpResponse(response);

    }

    public MakerCheckerEntry applyAdditionalSharesViaTransfer(SavingsAccount savingsAccount , ShareAccount shareAccount , BigDecimal amount , String date){

        Long clientId = savingsAccount.getClientId();
        Long savingsAccountId = savingsAccount.getId();
        Long officeId = 1L;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transferDate", date);
        jsonObject.put("fromClientId",clientId);
        jsonObject.put("fromOfficeId",officeId);
        jsonObject.put("fromAccountId",savingsAccountId);
        //jsonObject.put("requestedShares", numberOfShares);
        jsonObject.put("locale", "en");
        jsonObject.put("dateFormat", "dd MMMM yyyy");
        jsonObject.put("fromAccountType",2);
        jsonObject.put("toAccountType" ,4);
        jsonObject.put("toAccountId",shareAccount.getId());
        jsonObject.put("toOfficeId" ,officeId);
        jsonObject.put("toClientId",shareAccount.getId());
        jsonObject.put("transferAmount" ,amount.doubleValue());
        jsonObject.put("transferDescription","Savings Account Share Purchase Transfer");

        String url = String.format("accounttransfers");

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);
        String response = httpClientBridge.makeRequest(url);

        System.err.println("---------------response from share purchase ----------------"+response);
        return MakerCheckerEntry.fromHttpResponse(response);

    }




    public boolean approveAdditionalShares(Long accountNumber ,int requestedSharesId ,String date){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("requestedDate", date);

        JSONObject requestedShares = new JSONObject();
        requestedShares.put("id",requestedSharesId);
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(requestedShares);
        jsonObject.put("requestedShares",jsonArray);
        jsonObject.put("locale", "en");
        jsonObject.put("dateFormat", "dd MMMM yyyy");


        String url = String.format("accounts/share/%d?command=approveadditionalshares" ,accountNumber);

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);
        String response = httpClientBridge.makeRequest(url);

        System.err.println("----------response -------------"+response);

        MakerCheckerEntry makerCheckerEntry = MakerCheckerEntry.fromHttpResponse(response);

        if(makerCheckerEntry.getChanges()!=null){
            if(!makerCheckerEntry.getChanges().getRequestedShares().isEmpty()){
                return true;
            }
        }
        return false ;

    }

}
