package com.wese.weseaddons.batchprocessing.client;


import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.SavingsAccount;
import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.batchprocessing.helper.Parameters;

import java.math.BigDecimal;

public class AccountTransferParser{

    private JSONObject jsonObject ;
    private BigDecimal transactionAmount ;
    private String transferDescription ;
    private Parameters parameters ;
    private Client client ;
    private ClientSavingsExcelData clientSavingsExcelData ;

    public JSONObject getJsonObject(){
        return jsonObject ;
    }


    public void init(){

        Client client = clientSavingsExcelData.getClient();
        SavingsAccount savingsAccount = clientSavingsExcelData.getSavingsAccount();

        Loan loan = clientSavingsExcelData.getExcelClientData().getLoan();

        String prependedTransferDescription = String.format("%s %s\n",client.getDisplayName() ,parameters.getTranferDescription());

        try{

            jsonObject = new JSONObject();
            jsonObject.put("fromOfficeId" ,parameters.getOfficeId());
            jsonObject.put("fromClientId" ,client.getId());
            jsonObject.put("fromAccountType",2);///this is savings account
            jsonObject.put("fromAccountId",savingsAccount.getId());
            jsonObject.put("toOfficeId" ,parameters.getOfficeId());
            jsonObject.put("toClientId" ,client.getId());
            jsonObject.put("toAccountType",1);
            jsonObject.put("toAccountId",loan.getId()); ///loan account needed here
            jsonObject.put("dateFormat","dd MMM yyyy");
            jsonObject.put("locale" ,"en_ZW");
            jsonObject.put("transferDate" , parameters.getPostedDate());
            jsonObject.put("transferAmount" ,transactionAmount);
            jsonObject.put("transferDescription" ,prependedTransferDescription);

        }

        catch (JSONException jsonException){

            System.out.println(jsonObject.toString());

        }
    }

    public boolean savingToSavingTransfer(Long clientId,SavingsAccount sourceAccount ,SavingsAccount destinationAccount ,BigDecimal amount ,String description){
        // to do if source = destination skip transaction
        try{
            jsonObject = new JSONObject();
            jsonObject.put("fromOfficeId" ,parameters.getOfficeId());
            jsonObject.put("fromClientId" ,sourceAccount.getClientId());
            jsonObject.put("fromAccountType",2);///this is savings account
            jsonObject.put("fromAccountId",sourceAccount.getId());
            jsonObject.put("toOfficeId" ,parameters.getOfficeId());
            jsonObject.put("toClientId" ,clientId);
            jsonObject.put("toAccountType",2);
            jsonObject.put("toAccountId",destinationAccount.getId()); ///savings account needed here
            jsonObject.put("dateFormat","dd MMM yyyy");
            jsonObject.put("locale" ,"en_ZW");
            jsonObject.put("transferDate" , parameters.getPostedDate());
            jsonObject.put("transferAmount" ,amount);
            jsonObject.put("transferDescription" ,description);
        }

        catch (JSONException jsonException){
            return false;
        }
        return true ;
    }

    public AccountTransferParser(Parameters parameters){
        this.parameters = parameters ;
    }

    public AccountTransferParser(Parameters parameters ,ClientSavingsExcelData clientSavingsExcelData,BigDecimal transactionAmount){
        this.parameters = parameters ;
        this.clientSavingsExcelData = clientSavingsExcelData ;
        this.transactionAmount = transactionAmount;
        this.transferDescription = parameters.getTranferDescription();
        init();
    }

}
