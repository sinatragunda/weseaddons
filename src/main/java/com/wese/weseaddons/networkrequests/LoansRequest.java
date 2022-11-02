package com.wese.weseaddons.networkrequests;

import com.wese.weseaddons.pojo.ClientLoanAccounts;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoansRequest {


    private Session session ;
    private String tenantIdentifier ;
    private JSONObject jsonObject ;
    public static LoansRequest instance ;
    private List<Loan> loansList ;


    public static LoansRequest getInstance(){

        if(instance==null){
            instance= new LoansRequest();
        }

        return instance ;
    }

    public LoansRequest(){
        //this.session = session ;
    }

    public static Loan findLoan(Long id){

        String clientEndpoint = String.format("loans/%d",id);
        String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,clientEndpoint).makeRequest();
        return Loan.fromHttpResponse(stringResponse);
    }

    // added 19/03/2022
    public static MakerCheckerEntry reverseLoanTransaction(Long accountId ,Long transactionId ,String jsonRequest){

        String clientEndpoint = String.format("loans/%d/transactions/%d?command=undo", accountId, transactionId);
        String stringResponse = RequestBuilder.build(REQUEST_METHOD.POST ,clientEndpoint ,jsonRequest).makeRequest();
        MakerCheckerEntry checkerEntry = MakerCheckerEntry.fromHttpResponse(stringResponse);
        return checkerEntry ;
    }

    public LoansRequest(String tenantIdentifier){
        this.tenantIdentifier = tenantIdentifier ;
    }

    public LoansRequest(Session session){
        this.session = session;
    }

    public List<Loan> getLoanList() {
        return loansList;
    }

    public void loadClientLoans(Long clientId){

        List<Loan> loansList = new ArrayList<>();

        String clientEndpoint = String.format("clients/%d/accounts?fields=loanAccounts",clientId);

        String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,clientEndpoint).makeRequest();
        if(stringResponse==null){
            return ;
        }

        parseLoanRequest(new JSONObject(stringResponse));
    }

    public void parseLoanRequest(JSONObject jsonObject){

        try{

            JSONArray jsonArray = jsonObject.getJSONArray("loanAccounts");

            int totalRecords = jsonArray.length();
            for(int i = 0 ; i < totalRecords ;++i){

                long loanId = jsonArray.getJSONObject(i).getLong("id");
                String clientEndpoint = String.format("/loans/%d",loanId);
                String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,clientEndpoint).makeRequest();

                JSONObject jsonObject1 = new JSONObject(stringResponse);
                double principal = jsonObject1.getDouble("principal");
                boolean inArrears = false ;
                if(jsonObject1.has("inArrears")){
                    inArrears = jsonObject1.getBoolean("inArrears");
                }

                JSONObject statusJsonObject = jsonObject1.getJSONObject("status");
                boolean pendingApproval = statusJsonObject.getBoolean("pendingApproval");

                if(pendingApproval){
                    continue;
                }

                JSONObject summaryJsonObject = jsonObject1.getJSONObject("summary");
                double principalOutstanding = summaryJsonObject.getDouble("principalOutstanding");
                double interestOutstanding = summaryJsonObject.getDouble("interestOutstanding");

                Loan loans = new Loan(loanId ,principal ,principalOutstanding ,interestOutstanding ,inArrears ,pendingApproval);
                loansList.add(loans);

            }

        }

        catch(JSONException jsonException){
            System.out.println("Some exception thrown here "+jsonException.getMessage());

        }

    }

    public double getPrincipalOustanding(){

        List<Loan> loansList = session.getLoansList();
        double outstanding = loansList.stream()
                                      .mapToDouble(Loan::getPrincipalOutstanding)
                                      .sum();
                                      
        return outstanding ;
    }



}
