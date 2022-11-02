/*

    Created by Sinatra Gunda
    At 12:53 PM on 2/2/2022

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.batchprocessing.client.AccountTransferParser;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.networkrequests.SavingsRequest;

import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.FundDDAAccount;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.SavingsAccount;

import java.math.BigDecimal;
import java.util.Optional;

public class FundDDAHelper {

    // if false then insufficient balance from DDA Account ,if else then
    public static MakerCheckerEntry transferFromFundDDAToClientDDA(ExcelClientData excelClientData , SavingsRequest savingsRequest , Parameters parameters){

        boolean isPresent = Optional.ofNullable(excelClientData.getFundDDAAccount()).isPresent();

        if(isPresent){

            Client client = excelClientData.getClient();
            Long clientId = client.getId();
            SavingsAccount clientDestinationAccount = excelClientData.getDrawdownAccount();
            BigDecimal amount  = excelClientData.getOrignalDeposit();

            // fund dda account is the source account
            FundDDAAccount sourceFundDDAAccount = (FundDDAAccount) excelClientData.getFundDDAAccount();

            String description = String.format("DDA Transfer From %s ,with client id of %d To %s",sourceFundDDAAccount.getName() ,sourceFundDDAAccount.getClientId(), client.getDisplayName());

            AccountTransferParser accountTransferParser = new AccountTransferParser(parameters);

            accountTransferParser.savingToSavingTransfer(clientId ,sourceFundDDAAccount ,clientDestinationAccount ,amount ,description);

            MakerCheckerEntry makerCheckerEntry = SavingsRequest.invokeAccountTransfer(accountTransferParser);
            //return MakerCheckerEntry.fromHttpResponse(response);
            //excelClientData.setResourceId(clientDestinationAccount.getId());
            return makerCheckerEntry;
        }
        return null ;
    }
}
