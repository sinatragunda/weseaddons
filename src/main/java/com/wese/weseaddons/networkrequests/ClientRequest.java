package com.wese.weseaddons.networkrequests;

import com.wese.weseaddons.errors.springexceptions.ClientNotFoundException;
import com.wese.weseaddons.pojo.ClientLoanAccounts;
import com.wese.weseaddons.ussd.tree.SessionInit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.pojo.Client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientRequest {


    private JSONObject jsonObject ;
    private String phoneNumber ;
    private String tenantIdentifier;
    private List<Client> clientList ;
    private String clientResponse ;
    public static ClientRequest instance ;

    public ClientRequest(){}

    public ClientRequest(String phoneNumber,String tenantIdentifier){

        this.phoneNumber = phoneNumber ;
        this.tenantIdentifier = tenantIdentifier ;
        instance= this ;
    }

    public ClientRequest(String tenantIdentifier){

        this.tenantIdentifier = tenantIdentifier ;
        instance= this ;
    }

    public Client findClient(String id) {

        String clientEndpoint = String.format("clients/%s" ,id);
        String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,clientEndpoint,null).makeRequest();

        JSONObject jsonObject = new JSONObject(stringResponse);

        Client client = null ;

        if(jsonObject !=null){
            client = parseClientFromJson(jsonObject);
            return client ;
        }

        throw new ClientNotFoundException(0);

    }

    public Client getClientWhere(String columnName ,String value){

        String clientEndpoint = String.format("/clients/column/%s?value=%s" ,columnName ,value);

        String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,clientEndpoint,null).makeRequest();

        JSONObject jsonObject = new JSONObject(stringResponse);

        Client client = null ;

        if(jsonObject !=null){
            client = parseClientFromJson(jsonObject);
            return client ;
        }

        throw new ClientNotFoundException(0);

    }



    public void loadClientsAsync(String tenantIdentifier){

        String clientEndpoint ="clients";

        StringBuilder stringBuilder = new StringBuilder(clientEndpoint);
        String restUrl = stringBuilder.toString();

        clientList = new ArrayList();

        String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,restUrl,null).makeRequest();

        if(stringResponse==null){
        	System.out.println("String response is null "+stringResponse);
            return;
        }

        try{

            jsonObject = new JSONObject(stringResponse);

            JSONArray jsonArray = jsonObject.getJSONArray("pageItems");
            int totalFilteredRecords = jsonObject.getInt("totalFilteredRecords");

            if(jsonArray==null){
            	System.out.println("Json arraY is null "+totalFilteredRecords);
            }

            for(int i =0 ;i < totalFilteredRecords ;++i){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Client client1 = parseClientFromJson(jsonObject1);
                if(client1!=null){
                    this.clientList.add(client1);
                }
            }

        }

        catch(JSONException jsonException){

            System.out.println("Thrown some exception here"+jsonException.getMessage());
            
        }

    }

    public Client parseClientFromJson(JSONObject jsonObject1){

        Client client = null ;


        try{
            long id = jsonObject1.getLong("id");
            Long accountNumber = jsonObject1.getLong("accountNo");
            boolean isActive = jsonObject1.getBoolean("active");
            String displayName = jsonObject1.getString("displayName");
            String officeName = jsonObject1.getString("officeName");

            String lastname = null ;
            if(jsonObject1.has("lastname")){
                lastname = jsonObject1.getString("lastname");
            }

            String firstname = null ;
            if(jsonObject1.has("firstname")){
                firstname = jsonObject1.getString("firstname");
            }

            String externalId = null;

            if(jsonObject1.has("externalId")){
                externalId = jsonObject1.getString("externalId");

            }
            client = new Client(id ,accountNumber.toString(), externalId ,firstname ,lastname ,displayName ,officeName,isActive);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null ;
        }

        return client ;
    }

    public List<Client> getClientList() {
        return clientList;
    }


    public List<ClientLoanAccounts> getClientLoanAccounts(){

        Client client = null ;
        List<ClientLoanAccounts> clientLoanAccountsList = new ArrayList();

        if(client==null){
            return clientLoanAccountsList ;
        }


        long clientId = client.getId();
        String clientEndpoint = "clients/";
        StringBuilder stringBuilder = new StringBuilder(clientEndpoint);
        stringBuilder.append(String.valueOf(clientId));
        stringBuilder.append("/accounts");

        String restUrl = stringBuilder.toString();

        String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,restUrl).makeRequest();

        if(stringResponse==null){

            return clientLoanAccountsList ;
        }

        try{
            jsonObject = new JSONObject(stringResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("loanAccounts");

            if(jsonArray.length()==0){

                return clientLoanAccountsList ;
            }

            int size = jsonArray.length();

            for(int i =0 ; i < size ;++i){

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                long loanId  = jsonObject1.getLong("id");
                String accountNumber = jsonObject1.getString("accountNo");
                String produtName = jsonObject1.getString("productName");


                JSONObject statusObject = jsonObject1.getJSONObject("status");

                boolean active = statusObject.getBoolean("active");
                ClientLoanAccounts clientLoanAccounts= new ClientLoanAccounts(loanId ,accountNumber);
                clientLoanAccountsList.add(clientLoanAccounts);
            }

        }

        catch(JSONException jsonException){

            return clientLoanAccountsList ;

        }

        return clientLoanAccountsList ;

    }



    public static boolean doesClientExist(String phoneNumber){

//        List<Client> clientsList = SessionInit.getInstance().getClientsList();
//
//        if(clientsList.isEmpty()){
//
//            return false;
//        }
//
//        Iterator<Client> clientIterator = clientsList.iterator();
//        while (clientIterator.hasNext()){
//
//            Client searchClient = clientIterator.next();
//
//            if(phoneNumber.equalsIgnoreCase(searchClient.getExternalId())){
//
//                return true ;
//            }
//
//        }

        return false ;
    }

}
