package com.wese.weseaddons.networkrequests;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import com.wese.weseaddons.batchprocessing.client.AccountTransferParser;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.utility.ThreadContext;
import jdk.internal.org.objectweb.asm.TypeReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.batchprocessing.helper.Constants;
import com.wese.weseaddons.batchprocessing.helper.Parameters;

import javax.crypto.spec.OAEPParameterSpec;


public class SavingsRequest {


    private JSONObject jsonObject ;
    private String tenantIdentifier ;
    private Parameters parameters ;

    public static SavingsRequest instance ;
    private List<SavingsAccount> savingsAccountList ;

    public SavingsRequest(){}

    public SavingsRequest(Parameters parameters){
        this.parameters = parameters ;
        this.tenantIdentifier = ThreadContext.getTenant() ;
        this.instance = this ;
    }

    public static SavingsAccount savingsAccount(Long id){

        String url = String.format("savingsaccounts/%d",id);
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String response = httpClientBridge.makeRequest(url);

        System.err.println("------------------response is -------------"+response);
        
        return SavingsAccount.fromHttpResponse(response);
    }

    // added 08/02/2022

    public static MakerCheckerEntry invokeAccountTransfer(AccountTransferParser accountTransferParser) {

        JSONObject jsonObject = accountTransferParser.getJsonObject();

        String url = String.format("accounttransfers");

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);
        String response = httpClientBridge.makeRequest(url);
        return MakerCheckerEntry.fromHttpResponse(response);
    }

    // added 18/03/2022 
    public static MakerCheckerEntry reverseTransaction(Long accountId ,Long transactionId ,String requestBody){

        String url = String.format("savingsaccounts/%d/transactions/%d?command=undo",accountId ,transactionId);
        String response = RequestBuilder.build(REQUEST_METHOD.POST ,url ,requestBody).makeRequest();
        return MakerCheckerEntry.fromHttpResponse(response);

    }


    public void loadSavingsAccounts(){

        String clientEndpoint ="savingsaccounts?limit=10000";
        savingsAccountList = new ArrayList();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String stringResponse = httpClientBridge.makeRequest(clientEndpoint);

        if(stringResponse==null){
            return;
        }

        try{

            jsonObject = new JSONObject(stringResponse);
            JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray("pageItems");
            int totalFilteredRecords = jsonObject.getInt("totalFilteredRecords");

            if(totalFilteredRecords==0){
                return ;
            }

            System.err.println(totalFilteredRecords);

            for(int i =0 ;i < jsonArray.length() ;++i){

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                JSONObject summaryJsonObject = jsonObject1.getJSONObject("summary");
                String accountNumber = jsonObject1.getString("accountNo");
                long clientId = jsonObject1.getLong("clientId");
                long id = jsonObject1.getLong("id");
                String clientName = jsonObject1.getString("clientName");
                long savingsProductId = jsonObject1.getLong("savingsProductId");
                String savingsProductName = jsonObject1.getString("savingsProductName");
                BigDecimal accountBalance = summaryJsonObject.getBigDecimal("accountBalance");
                JSONObject currencyJsonObject = jsonObject1.getJSONObject("currency");
                String currencyCode = currencyJsonObject.getString("code");
                JSONObject statusJsonObject = jsonObject1.getJSONObject("status");
                boolean active = statusJsonObject.getBoolean("active");
                if(active){
                    SavingsAccount savingsAccount = new SavingsAccount(id ,accountNumber ,clientId ,savingsProductId ,savingsProductName ,accountBalance ,currencyCode ,active);
                    savingsAccountList.add(savingsAccount);
                }
            }
        }
        catch(JSONException jsonException){
            jsonException.printStackTrace();
            System.out.println("Thrown some exception here in savings account"+jsonException.getMessage());
        }
        Constants.savingsAccountList = savingsAccountList ;
    }


    public List<SavingsAccount> getClientSavingsAccount(Client client){

        List<SavingsAccount> clientSavingsAccounts = new ArrayList();
        boolean isPresent = Optional.ofNullable(getSavingsAccounts()).isPresent();
        boolean isClient = Optional.ofNullable(client).isPresent();

        if(isClient && isPresent) {
            for (SavingsAccount savingsAccount : savingsAccountList) {
                Long clientId = savingsAccount.getClientId();
                Long thisClientId = client.getId();
                if (thisClientId.equals(clientId)) {
                    clientSavingsAccounts.add(savingsAccount);
                }
            }
        }

        if(clientSavingsAccounts.isEmpty()){

            //System.err.println("----------------------client id is "+client.getId());

            String url = String.format("savingsaccounts/clientaccounts/%d",client.getId());
            String response = RequestBuilder.build(REQUEST_METHOD.GET ,url).makeRequest();

            //System.err.println("------------------response is ------------------"+response);
            clientSavingsAccounts = JsonHelper.serializeListFromHttpResponse(new SavingsAccount(),response);
        }
        return clientSavingsAccounts ;
    }

    /**
     * Added 28/09/2022 at 0423
     * Added to solve the problem of savings accounts list not loading at startup hence clients would get no valid drawdown error
     */ 
    public List<SavingsAccount> getClientSavingsAccountsEx(Client client){

        String url = String.format("savingsaccounts/clientaccounts/%d",client.getId());
        String response = RequestBuilder.build(REQUEST_METHOD.GET ,url).makeRequest();
        return JsonHelper.serializeListFromHttpResponse(new SavingsAccount(),response);
    }

    public List<SavingsAccount> getSavingsAccounts(){
        return savingsAccountList;
    }

}
