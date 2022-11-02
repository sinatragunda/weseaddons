package com.wese.weseaddons.bereaudechange.fxrequest;

import com.wese.weseaddons.bereaudechange.pojo.Staff;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.Client;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StaffRequest {

    private String tenantIdentifier ;

    public StaffRequest(String s){
        this.tenantIdentifier = s ;
    }

    public JSONObject jsonArg(Staff staff){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("officeId" ,staff.getOfficeId());
        jsonObject.put("firstname" ,staff.getFirstName());
        jsonObject.put("lastname", staff.getLastName());
        jsonObject.put("isLoanOfficer", true);
        jsonObject.put("externalId", staff.getMobileNumber());
        jsonObject.put( "mobileNo", staff.getMobileNumber());
        jsonObject.put("isActive", "true");
        jsonObject.put("joiningDate", staff.getJoiningDate());
        jsonObject.put("locale","en");
        jsonObject.put("dateFormat","dd MMMM yyyy");

        return jsonObject ;
    }

    public Staff createStaff(Staff staff){

        String clientEndpoint ="/staff";

        StringBuilder stringBuilder = new StringBuilder(clientEndpoint);
        String restUrl = stringBuilder.toString();


        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonArg(staff));
        String stringResponse = httpClientBridge.makeRequest(restUrl);

        if(stringResponse==null){

            return null;
        }

        try{

            JSONObject jsonObject = new JSONObject(stringResponse);
            long id = jsonObject.getInt("resourceId");
            staff.setId(id);
        }

        catch(JSONException jsonException){

            System.out.println("Thrown some exception here "+jsonException.getMessage());
            System.out.println("Then what now ,exit ?");


        }

        return staff ;
    }
}
