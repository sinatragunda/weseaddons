/*Created by Sinatra Gunda
  At 2:49 PM on 2/17/2020 */

package com.wese.weseaddons.crb.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.crb.enumerations.APPLICATION_STATUS;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.enumerations.STATUS_UPDATE_REASON;
import com.wese.weseaddons.crb.enumerations.TYPE_OF_COMPANY;
import com.wese.weseaddons.crb.error.CrbException;
import com.wese.weseaddons.crb.httpex.HttpClientBridgeEx;
import com.wese.weseaddons.crb.pojo.CreditApplicationRecord;
import com.wese.weseaddons.crb.pojo.builder.CreditApplicationRecordBuilder;
import com.wese.weseaddons.enumerations.AUTHENTICATION_TYPE;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.enumerations.TYPE_OF_RESOURCE;
import com.wese.weseaddons.errors.springexceptions.LoanNotFoundException;
import com.wese.weseaddons.helper.*;
import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Loan;
import org.json.JSONObject;

public class CreditApplicationHelper {

    public static ObjectNode upload(long loanId , String tenantIdentifier){

        LoansRequest loansRequest = new LoansRequest(tenantIdentifier);
        Loan loan = loansRequest.findLoan(loanId);
        //Loan loan = null ;

        if(loan==null){
            throw new LoanNotFoundException(loanId);
        }

        /* block this line of code for now 
        if(SyncWithCrbHelper.sync(tenantIdentifier ,loanId , TYPE_OF_RESOURCE.LOAN)){
            return Helper.statusNodes(true);
        } */

        /// check if item has passed through maker checker property

        //System.err.println(loan);


        ClientRequest clientRequest = new ClientRequest(tenantIdentifier);


        Client client = clientRequest.findClient(loan.getClientId().toString());

        //Client client = null ;

        CreditApplicationRecord creditApplicationRecord = new CreditApplicationRecord();
        RECORD_TYPE recordType = null ;

        switch (loan.getLoanType().getValue().toUpperCase()){

            case "INDIVIDUAL":
                recordType = RECORD_TYPE.CA ;
                loan.setApplicationStatus(APPLICATION_STATUS.F);
                creditApplicationRecord = creditApplicationRecord.individual(loan ,client , loan.getApplicationStatus() ,STATUS_UPDATE_REASON.NEW_LOAN);
                break;

            case "CORPORATE":
                recordType = RECORD_TYPE.AC;
                creditApplicationRecord = creditApplicationRecord.corporate(loan ,client , TYPE_OF_COMPANY.F);
                break;
        }

        if(creditApplicationRecord==null){
            throw new CrbException(CrbException.CRB_EXCEPTION.CA);
        }

        CreditApplicationRecordBuilder creditApplicationRecordBuilder = new CreditApplicationRecordBuilder(creditApplicationRecord ,recordType);
        String jsonRequest = JsonHelper.objectToJson(creditApplicationRecordBuilder);


        HttpClientBridgeEx httpClientBridgeEx = new HttpClientBridgeEx(REQUEST_METHOD.POST);

        String token = LoginCrbHelper.getTokenData(tenantIdentifier);

        System.err.println("----------------json request is --------------------"+jsonRequest);

        httpClientBridgeEx.setPostObject(new JSONObject(jsonRequest));

        String response = httpClientBridgeEx.makeRequest(Constants.crbSecureUrl+"carecord" ,token , AUTHENTICATION_TYPE.BEARER);

        System.err.println(response);

        return CrbSubmissionErrorHelper.errorCode(response);

    }


}
