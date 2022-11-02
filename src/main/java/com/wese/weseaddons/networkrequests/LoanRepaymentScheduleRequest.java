/*

    Created by Sinatra Gunda
    At 1:32 PM on 6/16/2021

*/
package com.wese.weseaddons.networkrequests;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.LoanRepaymentSchedule;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoanRepaymentScheduleRequest {

    private Loan loan ;
    private List<LoanRepaymentSchedule> loanRepaymentScheduleList;

    public LoanRepaymentScheduleRequest(Loan loan){
        this.loan = loan ;
        this.loanRepaymentScheduleList = new ArrayList<>();
        schedules();
    }

    public List<LoanRepaymentSchedule> schedules(){

        String clientEndpoint = String.format("loans/%d?associations=all&exclude=guarantors,futureSchedule",loan.getId());

        String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,clientEndpoint).makeRequest();
        if(stringResponse==null){
            return null;
        }

        JSONObject jsonObject = new JSONObject(stringResponse);
        JSONArray jsonArray = jsonObject.getJSONObject("repaymentSchedule").getJSONArray("periods");

        for(int i = 0 ; i < jsonArray.length() ; ++i){

            JSONObject j = jsonArray.getJSONObject(i);
            LoanRepaymentSchedule loanRepaymentSchedule = LoanRepaymentSchedule.fromJson(j.toString());
            loanRepaymentScheduleList.add(loanRepaymentSchedule);
        }

        return  loanRepaymentScheduleList;

    }

    public LoanRepaymentSchedule activeSchedule(){

        for(LoanRepaymentSchedule loanRepaymentSchedule : loanRepaymentScheduleList){
            if(!loanRepaymentSchedule.isComplete()){
                return loanRepaymentSchedule;
            }
        }
        return null ;
    }
}
