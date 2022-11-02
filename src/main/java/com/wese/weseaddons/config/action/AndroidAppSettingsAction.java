/*Created by Sinatra Gunda
  At 08:24 on 10/5/2020 */

package com.wese.weseaddons.config.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.config.dao.AndroidAppSettingsDAO;
import com.wese.weseaddons.config.dao.AndroidAppSettingsDAO;
import com.wese.weseaddons.config.pojo.AndroidAppSettings;
import com.wese.weseaddons.employeerelations.pojo.CompanyOfficials;
import com.wese.weseaddons.helper.Helper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class AndroidAppSettingsAction {

    private String tenantIdentifier ;
    private AndroidAppSettingsDAO androidAppsSettingsDAO ;


    public AndroidAppSettingsAction(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
        this.androidAppsSettingsDAO = new AndroidAppSettingsDAO(tenantIdentifier);
    }

    public ObjectNode find(){

        AndroidAppSettings androidAppSettings = androidAppsSettingsDAO.find(tenantIdentifier);
        if(androidAppSettings!=null){

            StringTokenizer stringTokenizer = tokenizer(androidAppSettings.getLoanProducts());
            ArrayNode loanProductsArrayNode = Helper.createArrayNode();
            Set<String> loanSet = new HashSet<>();
            while (stringTokenizer.hasMoreTokens()){
                String element = stringTokenizer.nextToken();
                if(!loanSet.contains(element)){
                        ObjectNode objectNode = Helper.createObjectNode();
                        objectNode.put("id" ,Long.valueOf(element));
                        loanProductsArrayNode.add(objectNode);
                        loanSet.add(element);
                }
            
            }

            List<CompanyOfficials> emailNotificationList = androidAppSettings.getEmailNotificationList();
            ArrayNode emailNotificationArrayNode = Helper.createArrayNode();
            for(CompanyOfficials companyOfficials : emailNotificationList){
                emailNotificationArrayNode.add(companyOfficials.objectNode());
            }


            StringTokenizer stringTokenizer3 = tokenizer(androidAppSettings.getActivityNotification());
            ArrayNode activityNotificationArrayNode = Helper.createArrayNode();
            Set<String> activitySet = new HashSet<>();
            while(stringTokenizer3.hasMoreTokens()){
                String element = stringTokenizer3.nextToken();
                if(!activitySet.contains(element)){
                    ObjectNode objectNode = Helper.createObjectNode();
                    objectNode.put("id" ,Long.valueOf(element));
                    activityNotificationArrayNode.add(objectNode);
                    activitySet.add(element);
                }
            }


            StringTokenizer stringTokenizer2 = tokenizer(androidAppSettings.getAddress());
            ArrayNode addressArrayNode = Helper.createArrayNode();
            while(stringTokenizer2.hasMoreTokens()){
            	String element = stringTokenizer2.nextToken();
            	ObjectNode objectNode = Helper.createObjectNode();
            	objectNode.put("address" ,element);
            	addressArrayNode.add(objectNode);
            }


            ObjectNode objectNode = Helper.statusNodes(true);
            objectNode.putPOJO("loanproducts",loanProductsArrayNode);
            objectNode.putPOJO("activitynotification" ,activityNotificationArrayNode);
            objectNode.putPOJO("emailnotification" ,emailNotificationArrayNode);
            objectNode.putPOJO("address" ,addressArrayNode);
            objectNode.put("tenant" ,androidAppSettings.getTenant());
            objectNode.put("appversion",androidAppSettings.getAppVersion());

            return objectNode ;
        }
        return Helper.statusNodes(false);
    }

    public ObjectNode update(AndroidAppSettings androidAppSettings){

        if(androidAppSettings.getActivityNotification()==null){
            androidAppSettings.setActivityNotification("");
        }

        if(androidAppSettings.getLoanProducts()==null){
            androidAppSettings.setLoanProducts("");
        }

        boolean status = androidAppsSettingsDAO.update(androidAppSettings);
        return Helper.statusNodes(status);
    }

    public StringTokenizer tokenizer(String arg){
    	 return new StringTokenizer(arg ,",");

    }


}
