/*

    Created by Sinatra Gunda
    At 8:30 AM on 3/18/2021

*/
package com.wese.weseaddons.utility.chartofaccounts.pojo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wese.weseaddons.utility.chartofaccounts.enmerations.CHART_OF_ACCOUNT_TYPE;
import org.json.JSONObject;

public class ChartOfAccount {

    private int glCode ;
    private int header ;
    private String glName ;
    private ChartOfAccount parent ;
    private String description ;
    private CHART_OF_ACCOUNT_TYPE chartOfAccountType ;

    public ChartOfAccount(){}

    public ChartOfAccount(int glCode, int header, String glName, ChartOfAccount parent, CHART_OF_ACCOUNT_TYPE chartOfAccountType) {
        this.glCode = glCode;
        this.header = header;
        this.glName = glName;
        this.parent = parent;
        this.description = glName;
        this.chartOfAccountType = chartOfAccountType;
    }

    public int getGlCode() {
        return glCode;
    }

    public void setGlCode(int glCode) {
        this.glCode = glCode;
    }

    public int isHeader() {
        return header;
    }

    public void setHeader(int header) {
        header = header;
    }

    public String getGlName() {
        return glName;
    }

    public void setGlName(String glName) {
        this.glName = glName;
    }

    public ChartOfAccount getParent() {
        return parent;
    }

    public void setParent(ChartOfAccount parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CHART_OF_ACCOUNT_TYPE getChartOfAccountType() {
        return chartOfAccountType;
    }

    public void setChartOfAccountType(CHART_OF_ACCOUNT_TYPE chartOfAccountType) {
        this.chartOfAccountType = chartOfAccountType;
    }

    public JSONObject jsonObject(){

        JSONObject jsonObject =new JSONObject();
        jsonObject.put("name" ,glName);
        jsonObject.put("glCode",glCode);
        jsonObject.put("type",chartOfAccountType.ordinal());
        jsonObject.put("description",description);
        jsonObject.put("parentId",parent.glCode);
        jsonObject.put("usage",header);
        jsonObject.put("manualEntriesAllowed",true);
        return jsonObject ;
    }
}
