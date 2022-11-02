package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.SettingsDAO;
import com.wese.weseaddons.bereaudechange.helper.FxAuthorizationHelper;
import com.wese.weseaddons.bereaudechange.helper.FxClearWorkingSet;
import com.wese.weseaddons.bereaudechange.pojo.Settings;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;


public class FxSettingsAction {

    private String tenant ;
    private SettingsDAO settingsDAO ;

    public FxSettingsAction(String tenant) {
        this.tenant = tenant;
        this.settingsDAO = new SettingsDAO(tenant);
    }

    public ObjectNode find(){

        Settings settings = settingsDAO.find();
        if(settings==null){
            return Helper.statusNodes(false);
        }

        return createNode(settings).put("status",true);

    }

    public ObjectNode clearWorkingSet(String username ,String password ,long authorizerId){

        // boolean authorization = FxAuthorizationHelper.authorize(tenant ,username ,password);

        // if(!authorization){

        //     return Helper.statusNodes(false).put("message", Constants.failedAuthorization);
        // }

        FxClearWorkingSet fxClearWorkingSet = new FxClearWorkingSet(tenant);
        boolean status = fxClearWorkingSet.clear(authorizerId);

        if(!status){
            return Helper.statusNodes(status).put("message","Failed to clear working set please try again or contact admin as it may cause inconsistencies");
        }
        return Helper.statusNodes(status).put("message","Working set cleared sucessfully");

    }

    public ObjectNode create(Settings settings){
        return settingsDAO.create(settings);
    }

    public ObjectNode update(Settings settings){
        return settingsDAO.update(settings);
    }

    public ObjectNode createNode(Settings settings){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("settings",settings.getId());
        objectNode.put("dailyLimit",settings.getDailyLimit());
        objectNode.put("openingTime",toHourNotation(settings.getOpeningTime()));
        objectNode.put("closingTime",toHourNotation(settings.getClosingTime()));
        objectNode.put("currencyPair" ,settings.getCurrencyPair().getTick());
        objectNode.put("accountingCurrency",settings.getAccountingCurrency().getName());
        objectNode.put("localCurrency",settings.getLocalCurrency().getName());
        objectNode.put("autoRevalType" ,settings.getAutoRevalType().getCode());
        objectNode.put("district" ,settings.getDistrict());
        objectNode.put("companyName",settings.getCompanyName());
        objectNode.put("companyCity",settings.getCompanyCity());
        objectNode.put("companyAddress" ,settings.getCompanyAddress());
        objectNode.put("profitCalculationMethod", settings.getProfitCalculationMethod().getCode());

        return objectNode ;

    }

    public String toHourNotation(int arg){

        StringBuilder builder = new StringBuilder();
        if(arg > 9){
            builder.append(String.format("%d00",arg));
            return builder.toString();
        }

        builder.append(String.format("0%d00",arg));
        return builder.toString();

    }
}
