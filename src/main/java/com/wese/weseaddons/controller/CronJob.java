package com.wese.weseaddons.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wese.weseaddons.cronjob.JobDetails;
import com.wese.weseaddons.cronjob.ListJobs;
import com.wese.weseaddons.cronjob.UpdateCronJob;
import com.wese.weseaddons.batchprocessing.helper.Constants;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="/cronjob")
public class CronJob {

    @RequestMapping(value = "/update")
    public void update(@RequestParam("tenant")String tenant ,@RequestParam("username")String username ,@RequestParam("password")String password){
        
        Constants.username = username ;
        Constants.password = password ;
        Constants.tenantIdentifier = tenant ;
        List<JobDetails> jobDetailsList = new ArrayList<>();
        ListJobs listJobs = new ListJobs();
        listJobs.listJobs();
        jobDetailsList = listJobs.getJobDetailsList();

        for(JobDetails jobDetails : jobDetailsList){

            UpdateCronJob updateCronJob = new UpdateCronJob(jobDetails ,tenant ,username,password);
            updateCronJob.update();
            
        }
    }
    
    
}
