/*Created by Sinatra Gunda
  At 2:49 PM on 2/17/2020 */

package com.wese.weseaddons.crb.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.crb.enumerations.RECORD_TYPE;
import com.wese.weseaddons.crb.httpex.HttpClientBridgeEx;
import com.wese.weseaddons.crb.pojo.MobileLoansInformation;
import com.wese.weseaddons.crb.pojo.builder.MobileLoansInformationBuilder;
import com.wese.weseaddons.enumerations.AUTHENTICATION_TYPE;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.pojo.Loan;

public class MobileLoansInformationHelper {

    public static ObjectNode upload(long loanId , String tenantIdentifier){

        LoansRequest loansRequest = new LoansRequest(tenantIdentifier);
        Loan loan = loansRequest.findLoan(loanId);


        String submittedDate = TimeHelper.dateToString(loan.getSubmittedDate() ,Constants.crbDateFormat);
        String disbursedDate = TimeHelper.dateToString(loan.getDisbursedDate() ,Constants.crbDateFormat);

        
        MobileLoansInformation mobileLoansInformation = new MobileLoansInformation();

        RECORD_TYPE recordType =  RECORD_TYPE.ML;

        MobileLoansInformationBuilder mobileLoansInformationBuilder = new MobileLoansInformationBuilder(mobileLoansInformation ,recordType);

        String jsonRequest = JsonHelper.objectToJson(mobileLoansInformationBuilder);

        HttpClientBridgeEx httpClientBridgeEx = new HttpClientBridgeEx(REQUEST_METHOD.POST);

        String token = LoginCrbHelper.getTokenData(tenantIdentifier);

        String response = httpClientBridgeEx.makeRequest(Constants.crbSecureUrl+"mobile" ,token , AUTHENTICATION_TYPE.BEARER);

        return CrbSubmissionErrorHelper.errorCode(response);


    }


}
