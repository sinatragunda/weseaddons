/*

    Created by Sinatra Gunda
    At 3:47 PM on 3/19/2022

*/
package com.wese.weseaddons.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeveloperErrors {

    private String developerMessage;
    private String userMessageGlobalisationCode; 

    public DeveloperErrors(){}

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getUserMessageGlobalisationCode() {
        return userMessageGlobalisationCode;
    }

    public void setUserMessageGlobalisationCode(String userMessageGlobalisationCode) {
        this.userMessageGlobalisationCode = userMessageGlobalisationCode;
    }
}
