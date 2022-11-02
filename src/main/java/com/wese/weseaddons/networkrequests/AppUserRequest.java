package com.wese.weseaddons.networkrequests;


import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Parameters;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.interfaces.Listable;
import com.wese.weseaddons.pojo.UserRole;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppUserRequest implements Listable {

    private JSONObject jsonObject ;
    private Parameters parameters ;
    private String tenantIdentifier;
    private List<AppUser> userList ;
    public static AppUserRequest instance ;


    public AppUserRequest(String tenantIdentifier){
    	this.tenantIdentifier  = tenantIdentifier ;

    }

    public AppUserRequest(Parameters parameters){

        this.parameters = parameters ;
        this.tenantIdentifier = parameters.getTenantIdentifier();
        this.instance = this ;

    }

    public static AppUserRequest getInstance() {
        return instance;
    }

    public void loadAppUsers(){

        String userEndpoint ="/users";

        userList = new ArrayList();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String stringResponse = httpClientBridge.makeRequest(userEndpoint);

        if(stringResponse==null){

            System.out.println("String response is null "+stringResponse);
            return;
        }


        try{

            JSONArray jsonArray = new JSONArray(stringResponse);
            if(jsonArray==null){
                return ;
            }


            for(int i =0 ;i < jsonArray.length() ;++i){

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                AppUser user= parseAppUser(jsonObject1);
                this.userList.add(user);

            }
        }

        catch(JSONException jsonException){
            System.out.println("Thrown some exception here "+jsonException.getMessage());
        }
    }


    public AppUser parseAppUser(JSONObject jsonObject){
        
        if(!jsonObject.has("id")) {
            return null ;
        }

        long id = jsonObject.getLong("id");
        String username = jsonObject.getString("username");
        String firstname = jsonObject.getString("firstname");
        String officeName = jsonObject.getString("officeName");
        String email = jsonObject.getString("email");

        List<UserRole> selectedRolesList = new ArrayList<>();

        JSONArray selectedRolesArray = jsonObject.getJSONArray("selectedRoles");

        for(int k = 0 ;k < selectedRolesArray.length() ;++k){

            JSONObject jsonObject1 = selectedRolesArray.getJSONObject(k);
            String name = jsonObject1.getString("name");
            UserRole userRole = new UserRole(name);

            selectedRolesList.add(userRole);

        }

        AppUser user= new AppUser(id ,0 ,username ,firstname,null ,officeName ,selectedRolesList ,email);
        return user ;

    }


    public AppUser getAppUser(long id){

        String userEndpoint = String.format("/users/%d", id);
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String stringResponse = httpClientBridge.makeRequest(userEndpoint);
  
        if(stringResponse==null){
           return null;
        }

        return parseAppUser(new JSONObject(stringResponse));

    }


    @Override
    public List<AppUser> getList() {
        return userList;
    }

}