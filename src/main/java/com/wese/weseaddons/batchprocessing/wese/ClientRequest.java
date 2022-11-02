package com.wese.weseaddons.batchprocessing.wese;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.batchprocessing.client.ClientLoanAccount;
import com.wese.weseaddons.batchprocessing.helper.Parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ClientRequest {

    private JSONObject jsonObject ;
    private Parameters parameters ;
    private String tenantIdentifier;
    private List<Client> clientList ;
    private String clientResponse ;
    public static ClientRequest instance ;
    private HttpClientBridge httpClientBridge ;
    private boolean isDoneLoadingClients ;

    public ClientRequest(){}

    public ClientRequest(Parameters parameters){

        this.parameters = parameters ;
        this.tenantIdentifier = parameters.getTenantIdentifier();
        this.isDoneLoadingClients = false ;
        this.instance = this ;

    }

    public static ClientRequest getInstance() {
        return instance;
    }

    public void loadClients(){

        String clientEndpoint ="clients?limit=10000";

        StringBuilder stringBuilder = new StringBuilder(clientEndpoint);
        String restUrl = stringBuilder.toString();

        Client client =null;
        clientList = new ArrayList();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String stringResponse = httpClientBridge.makeRequest(restUrl);

        if(stringResponse==null){
            return;
        }

        try{

            jsonObject = new JSONObject(stringResponse);

            JSONArray jsonArray = jsonObject.getJSONArray("pageItems");
            int totalFilteredRecords = jsonObject.getInt("totalFilteredRecords");

            if(jsonArray==null){
                return ;
            }

            for(int i =0 ;i < jsonArray.length() ;++i){


                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                long id = jsonObject1.getLong("id");
                String accountNumber = jsonObject1.getString("accountNo");
                boolean isActive = jsonObject1.getBoolean("active");
                String displayName = jsonObject1.getString("displayName");
                String officeName = jsonObject1.getString("officeName");
                String externalId = null;

                if(jsonObject1.has("externalId")){
                    externalId = jsonObject1.getString("externalId");
                }

                client = new Client(id ,accountNumber ,externalId ,displayName ,officeName,isActive);
                this.clientList.add(client);

            }
        }
        catch(JSONException jsonException){
            jsonException.printStackTrace();
        }

        isDoneLoadingClients = true ;

    }

    public boolean isDoneLoadingClients() {
        return isDoneLoadingClients;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public Client getClient(String searchNrcNumber){

        ///this function needs to be replaced

        for(Client client : clientList){
             String nrcNumber = client.getNrcNumber();
             if(nrcNumber==null){
                continue;
             }
             if(nrcNumber.equalsIgnoreCase(searchNrcNumber)){
                 return client ;
             }
        }

        /// search as if its client number
        for(Client client : clientList){

             String accountNo = client.getAccountNumber();

             Boolean isNrcPresent = Optional.ofNullable(searchNrcNumber).isPresent();
             Boolean isAccountNoPresent = Optional.ofNullable(accountNo).isPresent();

             if(isNrcPresent && isAccountNoPresent) {
                 accountNo = accountNo.replaceAll("\\s+", "");
                 searchNrcNumber = searchNrcNumber.replaceAll("\\s+", "");

                 if (searchNrcNumber.equalsIgnoreCase(accountNo)) {
                     return client;
                 }
             }
        }

        for(Client client : clientList){

            String accountNo = String.valueOf(client.getId());

            Boolean isNrcPresent = Optional.ofNullable(searchNrcNumber).isPresent();
            Boolean isAccountNoPresent = Optional.ofNullable(accountNo).isPresent();

            if(isNrcPresent && isAccountNoPresent) {
                if (searchNrcNumber.equalsIgnoreCase(accountNo)) {
                    return client;
                }
            }
        }


        return null ;

    }

    public List<ClientLoanAccount> getClientLoanAccounts(Client client){

        List<ClientLoanAccount> clientLoanAccountList = new ArrayList();
        if(client==null){
            return clientLoanAccountList;
        }

        String clientId = client.getAccountNumber();
        String clientEndpoint = "clients/";
        StringBuilder stringBuilder = new StringBuilder(clientEndpoint);
        stringBuilder.append(String.valueOf(clientId));
        stringBuilder.append("/accounts");

        String restUrl = stringBuilder.toString();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);

        String stringResponse = httpClientBridge.makeRequest(restUrl);

        if(stringResponse==null){
            return clientLoanAccountList;
        }

        try{

            jsonObject = new JSONObject(stringResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("loanAccounts");

            if(jsonArray.length()==0){
                return clientLoanAccountList;
            }

            int size = jsonArray.length();

            for(int i =0 ; i < size ;++i) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                long loanId = jsonObject1.getLong("id");
                String accountNumber = jsonObject1.getString("accountNo");
                long productId = jsonObject1.getLong("productId");
                String produtName = jsonObject1.getString("productName");

                JSONObject statusObject = jsonObject1.getJSONObject("status");

                boolean active = statusObject.getBoolean("active");
                if (active) {
                    ClientLoanAccount clientLoanAccount = new ClientLoanAccount(loanId, accountNumber, productId, produtName, active);
                    clientLoanAccountList.add(clientLoanAccount);
                }
            }

        }

        catch(JSONException jsonException){

            return clientLoanAccountList;

        }

        return clientLoanAccountList;
    }

    public ClientLoanAccount findAccountThatsPendingPayment(List<ClientLoanAccount> clientLoanAccountList){

        Iterator<ClientLoanAccount> clientLoanAccountIterator = clientLoanAccountList.iterator();

        while(clientLoanAccountIterator.hasNext()){

            ClientLoanAccount clientLoanAccount = clientLoanAccountIterator.next();
            long clientLoanProduct = clientLoanAccount.getProductId();
            long parameterLoanProduct = parameters.getLoanProduct().getId();

            if(clientLoanProduct==parameterLoanProduct){

                if(clientLoanAccount.isActive()){
                    return clientLoanAccount;
                }
            }
        }
        
        return null ;

    }
    
    public ClientLoanAccount findAccountWithSuppliedAccountNumber(List<ClientLoanAccount> clientLoanAccountList,String accountNumber){

        if(accountNumber==null){
            return null ;
        }
        Iterator<ClientLoanAccount> clientLoanAccountIterator = clientLoanAccountList.iterator();

        while(clientLoanAccountIterator.hasNext()){

            ClientLoanAccount clientLoanAccount = clientLoanAccountIterator.next();
            String iteratorAccountNumber = clientLoanAccount.getAccountNumber();

            if(iteratorAccountNumber.equalsIgnoreCase(accountNumber)){
                if(clientLoanAccount.isActive()){
                    return clientLoanAccount;
                }
            }
        }
        
        return null ;

    }



    public boolean doesClientExist(String nrcNumber){

        if(clientList.isEmpty()){
            return false;
        }

        Iterator<Client> clientIterator = clientList.iterator();
        while (clientIterator.hasNext()){
            Client searchClient = clientIterator.next();
            if(nrcNumber.equalsIgnoreCase(searchClient.getExternalId())){
                return true ;
            }
        }

        return false ;
    }

}
