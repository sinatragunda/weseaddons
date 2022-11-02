package com.wese.weseaddons.batchprocessing.client;

public class User {

    private String username ;
    private String officeName ;
    public boolean hasPermissions ;

    public User(String username){
        this.username = username ;
    }

    public boolean isHasPermissions() {
        return hasPermissions;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getUsername() {
        return username;
    }
}
