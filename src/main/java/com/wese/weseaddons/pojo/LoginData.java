/*

    Created by Sinatra Gunda
    At 9:45 AM on 9/14/2022

*/
package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.interfaces.IdIndexedClass;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginData {

    private Integer status;
    private String username ;
    private String password ;

    public LoginData(){}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static LoginData fromHttpResponse(String arg){
        LoginData loginData = JsonHelper.serializeFromHttpResponse(new LoginData() ,arg);
        boolean hasUsername = Optional.ofNullable(loginData.username).isPresent();
        if(!hasUsername){
            loginData= null;
        }
        return loginData;
    }
}
