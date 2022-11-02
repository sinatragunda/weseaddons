package com.wese.weseaddons.cronjob;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.batchprocessing.helper.Constants;

public class ListJobs {

    private List<JobDetails> jobDetailsList ;
 
    public void listJobs(){

        StringBuilder stringBuilder = new StringBuilder("/jobs");
        String url = stringBuilder.toString();
        String tenantIdentifier = Constants.tenantIdentifier;
        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String httpResponse = httpClientBridge.makeRequest(url);
        
        System.out.println(httpResponse);

        jobDetailsList = new ArrayList<>();

        try{

                JSONArray jsonArray = new JSONArray(httpResponse);
                for(int i =0 ; i < jsonArray.length() ;i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long jobId = jsonObject.getLong("jobId");
                String displayName = jsonObject.getString("displayName");
                boolean active = jsonObject.getBoolean("active");

                JobDetails jobDetails = new JobDetails(jobId ,displayName ,active);
                jobDetailsList.add(jobDetails);
            }
            
        }

        catch (JSONException j){

            System.out.println(j.getMessage());
        }


    }

    public List<JobDetails> getJobDetailsList() {
        return jobDetailsList;
    }
}
