package com.wese.weseaddons.batchprocessing.init;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.pojo.SsbPaymentsResults;
import com.wese.weseaddons.utility.ThreadContext;
import org.springframework.stereotype.Component;

import com.wese.weseaddons.batchprocessing.enumerations.PROCESS_STATUS;



@Component
public class SSBPaymentsInit {


    private Map<String ,PROCESS_STATUS> tenantProcesses;
    private Map<String ,ObjectNode> processResults ;
    private Map<String ,SsbPaymentsResults> paymentResultsMap ;
    public static SSBPaymentsInit instance;
    private static String transactionDate ;
      
    @PostConstruct
    public void init(){
        
        tenantProcesses = new HashMap<>();
        processResults = new HashMap<>();
        paymentResultsMap = new HashMap<>();
        instance = this ; 
    }
    
    public boolean addNewProcess(String key) {

        PROCESS_STATUS processStatus = getProcessStatus();

        if(processStatus==PROCESS_STATUS.PROCESSING){
            return false;
            // failed to add new process because current is running 
        }

        processStatus = tenantProcesses.putIfAbsent(key, PROCESS_STATUS.PROCESSING);
        
        if(processStatus!=PROCESS_STATUS.PROCESSING){    
             updateProcessStatus(key, PROCESS_STATUS.PROCESSING);
        }
        return true ;
    }
    
    public void updateProcessStatus(String key ,PROCESS_STATUS process_STATUS) {
        
        tenantProcesses.replace(key, process_STATUS);
      
    }
    
    public PROCESS_STATUS getProcessStatus() {
       
       String key = ThreadContext.getTenant();
       PROCESS_STATUS processStatus = PROCESS_STATUS.INIT;
       boolean hasKey = tenantProcesses.containsKey(key);

       if(hasKey){
            processStatus = tenantProcesses.get(key);
       }
       return processStatus;
        
    }

    public ObjectNode getProcessResults(){
        return processResults.get(ThreadContext.getTenant());
    }

    public SsbPaymentsResults getPaymentResults(){
        String tenantIdentifier = ThreadContext.getTenant();
        return paymentResultsMap.getOrDefault(tenantIdentifier ,new SsbPaymentsResults());
        //return paymentResultsMap.get(tenantIdentifier);
    }

    public void saveProcessResults(String tenantIdentifier , SsbPaymentsResults ssbPaymentResults , ObjectNode results){

        if(processResults.containsKey(tenantIdentifier)){
            processResults.replace(tenantIdentifier ,results);
            paymentResultsMap.replace(tenantIdentifier ,ssbPaymentResults);
            return;
        }
        processResults.put(tenantIdentifier ,results);
        paymentResultsMap.put(tenantIdentifier ,ssbPaymentResults);
    }


    public void saveProcessResults(ObjectNode objectNode){

        String tenant = ThreadContext.getTenant();
        SsbPaymentsResults ssbPaymentResults = getPaymentResults();
        saveProcessResults(tenant ,ssbPaymentResults ,objectNode);
    }

    public void updateResults(SsbPaymentsResults ssbPaymentResults){
        String key = ThreadContext.getTenant();
        paymentResultsMap.replace(key ,ssbPaymentResults);
    } 
    
    public static SSBPaymentsInit getInstance(){
        
        if(instance==null) {
            instance = new SSBPaymentsInit();
            instance.init();
        }    
        return instance ;
    }


    public void setTransactionDate(String date){
        this.transactionDate = date;
    }

    public String getTransactionDate(){
        return this.transactionDate;
    }
}
