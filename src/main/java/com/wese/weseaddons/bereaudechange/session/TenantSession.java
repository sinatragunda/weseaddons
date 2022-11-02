
package com.wese.weseaddons.bereaudechange.session;

import com.wese.weseaddons.bereaudechange.dao.SettingsDAO;
import com.wese.weseaddons.bereaudechange.fxrequest.ClientRequest;
import com.wese.weseaddons.bereaudechange.fxrequest.UserRequest;
import com.wese.weseaddons.bereaudechange.dao.SettingsDAO;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.networkrequests.AppUserRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.AppUser;



import java.util.List;


public class TenantSession {

    private String tenant ;
    private Settings settings ;
    private List<Client> clientsList;
    private List<AppUser> appUserList ;

    public TenantSession(String tenant) {
        this.tenant = tenant;
    }

    public void loadClients(){
        ClientRequest clientRequest = new ClientRequest(tenant);
        clientRequest.loadClients();
        this.clientsList = clientRequest.getClientList();
    }

    public void loadAppUsers(){
        AppUserRequest userRequest = new AppUserRequest(tenant);
        this.appUserList = userRequest.getList();
    }

    public void loadSettings(){

        SettingsDAO settingsDAO = new SettingsDAO(tenant);
        try{
            this.settings = settingsDAO.find();
        }
        catch (Exception e){

        }
    }

    public Settings getSettings(){
        return this.settings ;
    }

    public String getTenant(){
        return this.tenant ;
    }

    public List getClientsList(){
        return this.clientsList;

    }

    public List getAppUsersList(){
        return this.appUserList;

    }


    public Client getClient(long id){

        for(Client client : clientsList){
            if(client.getId()==id){
                return client ;
            }
        }
        return null ;
    }

     public AppUser getAppUser(long id){

        for(AppUser appUser: appUserList){
            if(appUser.getId()==id){
                return appUser ;
            }
        }
        return null ;
    }

}
