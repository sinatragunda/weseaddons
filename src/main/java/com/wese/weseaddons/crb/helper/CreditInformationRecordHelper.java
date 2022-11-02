/*Created by Sinatra Gunda
  At 3:00 AM on 3/2/2020 */

package com.wese.weseaddons.crb.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.enumerations.STATUS_UPDATE_REASON;
import com.wese.weseaddons.crb.error.CrbFailedToCollectDataException;
import com.wese.weseaddons.crb.httpex.HttpClientBridgeEx;
import com.wese.weseaddons.crb.pojo.ConsumerCreditInformationRecord;
import com.wese.weseaddons.crb.pojo.CorporateCreditInformationRecord;
import com.wese.weseaddons.crb.pojo.CreditInformationRecord;
import com.wese.weseaddons.crb.pojo.builder.ConsumerCreditInformationRecordBuilder;
import com.wese.weseaddons.crb.pojo.builder.CreditApplicationRecordBuilder;
import com.wese.weseaddons.enumerations.AUTHENTICATION_TYPE;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.enumerations.TYPE_OF_RESOURCE;
import com.wese.weseaddons.errors.springexceptions.LoanNotFoundException;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Loan;
import org.json.JSONObject;
import org.opensaml.saml1.core.StatusCode;

import java.util.*;

public class CreditInformationRecordHelper {


    public static ObjectNode upload(long loanId ,Double transactionAmount ,Date transactionDate , String tenantIdentifier){

        LoansRequest loansRequest = new LoansRequest(tenantIdentifier);
        Loan loan = loansRequest.findLoan(loanId);

        if(loan==null){
            throw new LoanNotFoundException(loanId);
        }

        if(SyncWithCrbHelper.sync(tenantIdentifier ,loanId , TYPE_OF_RESOURCE.CREDIT_UPDATE)){

            return Helper.statusNodes(true);

        }

        /// check if item has passed through maker checker property

        if(transactionAmount!=null){
            loan.setTransactionPayment(transactionAmount.doubleValue());

        }

        if(transactionDate!=null){

            loan.setLoanRepaymentDate(transactionDate);

        }


        ClientRequest clientRequest = new ClientRequest(tenantIdentifier);
        //Client client = clientRequest.findClient(loan.getClient().getId());
        Client client = null ;
        client.setTenantIdentifier(tenantIdentifier);

        RECORD_TYPE recordType = null ;

        ConsumerCreditInformationRecord consumerCreditInformationRecord = null ;
        CorporateCreditInformationRecord corporateCreditInformationRecord = null ;

        CreditInformationRecord creditInformationRecord= null ;

        StringBuilder urlStringBuilder = new StringBuilder(Constants.crbSecureUrl);

        
        String jsonRequest = null ;

        switch (loan.getLoanType().getValue().toUpperCase()){

            case "INDIVIDUAL":

                urlStringBuilder.append("pirecord");
                jsonRequest = individualRequest(loan, client);

                break;

            case "CORPORATE":


                urlStringBuilder.append("parecord");
                recordType = RECORD_TYPE.PA;

                if(consumerCreditInformationRecord==null){
                    throw new CrbFailedToCollectDataException();
                }
                
                break;
        }


        HttpClientBridgeEx httpClientBridgeEx = new HttpClientBridgeEx(REQUEST_METHOD.POST);
        String token = LoginCrbHelper.getTokenData(tenantIdentifier);
        httpClientBridgeEx.setPostObject(new JSONObject(jsonRequest));

        String response = httpClientBridgeEx.makeRequest(urlStringBuilder.toString() ,token , AUTHENTICATION_TYPE.BEARER);

        return CrbSubmissionErrorHelper.errorCode(response);


    }
    
    public static String individualRequest(Loan loan ,Client client) {
        
        
        ConsumerCreditInformationRecord consumerCreditInformationRecord = new ConsumerCreditInformationRecord(loan ,client);

        if(consumerCreditInformationRecord==null){
            
            throw new CrbFailedToCollectDataException();
        }

        ConsumerCreditInformationRecordBuilder consumerCreditInformationRecordBuilder = new ConsumerCreditInformationRecordBuilder(consumerCreditInformationRecord ,RECORD_TYPE.PI);
         
        return JsonHelper.objectToJson(consumerCreditInformationRecordBuilder);

        
    }
    
}