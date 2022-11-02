package com.wese.weseaddons.bereaudechange.fxrequest;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.Parameters;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class ClientRequest {

    private JSONObject jsonObject ;
    private String tenantIdentifier;
    private List<Client> clientList ;

    public ClientRequest(String tenantIdentifier){

 
        this.tenantIdentifier = tenantIdentifier;

    }


    public void loadClients(){

        String clientEndpoint ="/clients?limit=2000";

        StringBuilder stringBuilder = new StringBuilder(clientEndpoint);
        String restUrl = stringBuilder.toString();

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
                String displayName = jsonObject1.getString("displayName");
                String officeName = jsonObject1.getString("officeName");

//                  new Client()


                Client client = new Client(id ,accountNumber ,null ,displayName ,officeName,true ,null ,null);
                this.clientList.add(client);

            }
        }

        catch(JSONException jsonException){

            System.out.println("Thrown some exception here "+jsonException.getMessage());
            System.out.println("Then what now ,exit ?");
            

        }
    }


    public List<Client> getClientList() {
        return clientList;
    }

}
