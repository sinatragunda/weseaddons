/*Created by Sinatra Gunda
  At 12:22 on 7/21/2020 */

package com.wese.weseaddons.helper;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.errors.springexceptions.AppUserNotFound;
import com.wese.weseaddons.errors.springexceptions.EmailAddressNotFound;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.networkrequests.AppUserRequest;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.pojo.Email;

public class EmailNotification {


    public static void sendWeseAppUserEmail(Email email , AppUser appUser ,String tenantIdentifier){

        if(email==null){
                  
        }

        AppUserRequest appUserRequest = new AppUserRequest(tenantIdentifier);

        if(appUser.getUserName()==null){
            appUser = appUserRequest.getAppUser(appUser.getId());
        }

        if(appUser==null){
            throw new AppUserNotFound(appUser.getId());
        }

        String emailAdress = appUser.getEmail();

        if(emailAdress==null){
            throw new EmailAddressNotFound();
        }

        email.setEmailAddress(emailAdress);
        email.setContact(appUser.getDisplayName());

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(email.jsonObject());

        String url = "/weseexternal";

        httpClientBridge.makeRequest(url);

    }

    public static void sendTaskManagerEmailNotification(Long userId ,String subject ,String message ,String tenantIdentifier){

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);

        String url = String.format("/weseexternal/taskmanagermailnotification?userId=%d&subject=%s&message=%s",userId ,subject ,message);

        httpClientBridge.makeRequest(url);


    }
}
