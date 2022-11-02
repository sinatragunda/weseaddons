package com.wese.weseaddons.batchprocessing.wese;




import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;

public class OfficeRequest {

    private String tenantIdentifier ;
    private JSONArray jsonArray  ;
    private List<Office> officeList ;

    public void loadOffices(){

        String url = "/offices";
        officeList = new ArrayList<>();
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String httpResponse = httpClientBridge.makeRequest(url);


        try{

            jsonArray = new JSONArray(httpResponse);

            for(int i =0 ; i < jsonArray.length() ;i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                long id = jsonObject.getLong("id");
                String name = jsonObject.getString("name");

                Office office = new Office(id ,name);
                officeList.add(office);

            }
        }

        catch (JSONException jsonException){

        }
    }

    public OfficeRequest(String tenant) {
        this.tenantIdentifier = tenant ;
    }

    public List<Office> getOffice(){
        return officeList ;
    }

}
