package com.wese.weseaddons.batchprocessing.processor;


import com.wese.weseaddons.batchprocessing.enumerations.TRANSACTION_STATUS;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.batchprocessing.client.AccountTransferParser;
import com.wese.weseaddons.batchprocessing.client.ClientSavingsExcelData;
import com.wese.weseaddons.batchprocessing.enumerations.REPAY_LOAN_STATUS;
import com.wese.weseaddons.batchprocessing.helper.Parameters;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.SavingsAccount;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;


public class ProcessClientLoanRepayments {

    private BigDecimal amount ;
    private ClientSavingsExcelData clientSavingsExcelData ;
    private Parameters parameters ;
    private MakerCheckerEntry makerCheckerEntry = null;

    public ProcessClientLoanRepayments(Parameters parameters ,ClientSavingsExcelData clientSavingsExcelData ,BigDecimal amount){
        this.parameters = parameters ;
        this.amount = amount ;
        this.clientSavingsExcelData = clientSavingsExcelData ;

    }

    public TRANSACTION_STATUS repayLoan(){

        String url = "accounttransfers";

        // all amounts less than 0 should be skipped 
        boolean isRepayableAmount = isRepayableAmount(amount);

        if(!isRepayableAmount){
            return TRANSACTION_STATUS.LOAN_REPAYMENT_SKIPPED;
        }

        SavingsAccount savingsAccount = clientSavingsExcelData.getSavingsAccount();
        Loan loan = clientSavingsExcelData.getExcelClientData().getLoan();

        String description = String.format("Loan repayment from account %s to Loan Account %s" ,savingsAccount.getSavingsProductName() ,loan.getId());

        parameters.setTranferDescription(description);

        AccountTransferParser accountTransferParser = new AccountTransferParser(parameters ,clientSavingsExcelData ,amount);
        JSONObject jsonObject = accountTransferParser.getJsonObject();

        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
        httpClientBridge.setPostObject(jsonObject);
        String httpResponse = httpClientBridge.makeRequest(url);

        System.err.println("-------------repay loan response ,maker checker entry value  ----------"+httpResponse);

        makerCheckerEntry = MakerCheckerEntry.fromHttpResponse(httpResponse);
        return transactionStatus(makerCheckerEntry);

    }

    public TRANSACTION_STATUS transactionStatus(MakerCheckerEntry makerCheckerEntry){

        TRANSACTION_STATUS transactionStatus = TRANSACTION_STATUS.FAILED ;

        try{

            if(makerCheckerEntry.hasErrors()){
                return TRANSACTION_STATUS.LOAN_REPAYMENT_FAILED;
            }

            Long savingsId = makerCheckerEntry.getSavingsId();
            if(savingsId != null){
                return TRANSACTION_STATUS.LOAN_REPAYMENT_SUCCESS;
            }
            Long resourceId = makerCheckerEntry.getResourceId();
            ///much more likely we wont get there since error would have been thrown
            if(resourceId != null){
                return TRANSACTION_STATUS.LOAN_REAPYMENT_WAITING_APPROVAL;
            }

        }

        catch (NullPointerException n){
        }
        return transactionStatus ;
    }

    public MakerCheckerEntry getMakerCheckerEntry(){
        return this.makerCheckerEntry ;
    }


    private boolean isRepayableAmount(BigDecimal amount){
        BigDecimal zero = BigDecimal.ZERO ;
        boolean isEqual = amount.toBigInteger().equals(zero.toBigInteger());
        // if its equal to 0 then dont pay 
        return !isEqual ;
    }
}
