package com.wese.weseaddons.helper;

import com.wese.weseaddons.enumerations.COMMANDS;

public class Parameters {

    private String tenantIdentifier ;
    private COMMANDS command ;

    public Parameters(){}

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public COMMANDS getCommand() {
        return command;
    }

    public void setCommand(COMMANDS command) {
        this.command = command;
    }
}
