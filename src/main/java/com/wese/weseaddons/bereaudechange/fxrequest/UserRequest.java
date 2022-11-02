package com.wese.weseaddons.bereaudechange.fxrequest;

import com.wese.weseaddons.bereaudechange.dao.FxCashierDAO;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.FxCashier;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class UserRequest {

    private JSONObject jsonObject ;
    private String tenantIdentifier;
    private List<AppUser> appUserList ;

    public UserRequest(String tenantIdentifier){

        this.tenantIdentifier = tenantIdentifier;
        this.appUserList = new ArrayList<>();

    }

    public void loadUsers(){

        String restUrl = "/users";
        appUserList = new ArrayList();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String stringResponse = httpClientBridge.makeRequest(restUrl);

        if(stringResponse==null){
            return;
        }

        try{

            JSONArray jsonArray = new JSONArray(stringResponse);

            if(jsonArray==null){
                return ;
            }


            for(int i =0 ;i < jsonArray.length() ;++i){

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                long id = jsonObject1.getLong("id");
                long officeId = jsonObject1.getLong("officeId");
                String username = jsonObject1.getString("username");
                String officeName = jsonObject1.getString("officeName");
                String firstName = jsonObject1.getString("firstname");
                String lastName = jsonObject1.getString("lastname");

                AppUser appUser = new AppUser(id ,officeId ,username ,firstName ,lastName ,officeName);
                FxCashierDAO fxCashierDAO = new FxCashierDAO(tenantIdentifier);
                FxCashier fxCashier = fxCashierDAO.findWhere(appUser.getId() ,"app_user_id");

                if(fxCashier!=null){
                    appUser.setFxCashier(fxCashier);
                }
                appUserList.add(appUser);
            }
        }

        catch(JSONException jsonException){
            System.out.println("Thrown some exception here "+jsonException.getMessage());
        }
    }

    public List<AppUser> getAppUserList() {
        return appUserList;
    }

}
