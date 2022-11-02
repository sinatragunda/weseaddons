package com.wese.weseaddons.cronjob;

import org.json.JSONObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.batchprocessing.helper.Constants;



public class UpdateCronJob {

    private JSONObject jsonObject ;
    //private String newCronExpression = "0 0 0 * * ?";
    private String newCronExpression = " 0 30 23 1/1 * ? *";
    private JobDetails jobDetails ;
    private String password ;
    private String tenant ;
    private String username ;


    public UpdateCronJob(JobDetails jobDetails,String tenant ,String username ,String password){
        this.jobDetails = jobDetails ;
        this.tenant = tenant ;
        this.password = password ;
        this.username = username ;
        Constants.username = username ;
    }


    public void loadJsonObject(){

        this.jsonObject = new JSONObject();
        this.jsonObject.put("displayName" ,jobDetails.getDisplayName());
        this.jsonObject.put("cronExpression" ,newCronExpression);
        this.jsonObject.put("active" ,Boolean.toString(jobDetails.isActive()));
        
    }

    public void update(){

        Constants.password = password ;
        StringBuilder stringBuilder = new StringBuilder("/jobs/");
        stringBuilder.append(jobDetails.getJobId());
        
        String url = stringBuilder.toString();
      
        loadJsonObject();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.PUT);
        httpClientBridge.setPostObject(jsonObject);
        String httpResponse = httpClientBridge.makeRequest(url);

        System.out.println("Response string is "+httpResponse);


    }
}
