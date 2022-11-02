/*

    Created by Sinatra Gunda
    At 7:10 AM on 6/25/2021

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.batchprocessing.client.AccountTransferParser;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.batchprocessing.enumerations.STAGING;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.BigDecimalHelper;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.SavingsAccount;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SsbPaymentsHelper{

    public static void transactionBalance(Map<String ,BigDecimal> data ,String key ,BigDecimal amount){
        // used to get transaction balance per client transactions
        amount = BigDecimalHelper.roundOf2(amount);

        if(data.containsKey(key)){
            BigDecimal cur = data.get(key);
            amount = amount.add(cur);
            data.replace(key ,amount);
            return;
        }
        data.put(key ,amount);
    }

    public static MakerCheckerEntry moveExcessBalances(Map<String,BigDecimal> data ,ExcelClientData excelClientData ,AccountTransferParser accountTransferParser, SavingsAccount sourceAccount ,SavingsAccount destinationAccount ,BigDecimal threshold){
        //take excel client the orignal copy and compare moving balances

        String key = excelClientData.getNrcNumber();

        if(data.containsKey(key)) {

            BigDecimal balance = data.get(key);
            BigDecimal orignalAmount = excelClientData.getOrignalDeposit();

            /**
             * Added 29/09/2022 at 1321
             * Need more sound validation here
             * If orignalAmount is equal to balance ,then no transaction took place ,its ideal to push all money to settle account
             */

            Client client = excelClientData.getClient();
            /**
             * Orignal amount 5000 ,balance 4000 = 1000 deposited to another account
             * Reviewed 29/09/2022 at 1318
             */
            int cmp = orignalAmount.compareTo(balance);

            if(cmp==0){
                /**
                 * Added 29/09/2022 at 1324
                 * If orignal amount is equal to balance then orignal amount should be set to zero ,so that when compared it equals a value to be pushed
                 * WE multiply it by 2 so that we can subtract it from balance  ,to get the orignal amount set to be moved
                 */
                orignalAmount = orignalAmount.multiply(new BigDecimal(2));
                cmp = 1 ;
            }

            if (cmp > 0) {

                BigDecimal diff = orignalAmount.subtract(balance);

                diff = sweepingBalance(threshold ,sourceAccount ,diff);

                BigDecimal scaled = BigDecimalHelper.roundOf2(diff);
                Float amountFloat = scaled.floatValue();
                excelClientData.setAmount(scaled);
                boolean proceed = accountTransferParser.savingToSavingTransfer(client.getId(), sourceAccount, destinationAccount, scaled, "Excess Drawdown Balance");

                if(proceed) {
                    data.remove(key);
                    HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
                    JSONObject jsonObject = accountTransferParser.getJsonObject();
                    httpClientBridge.setPostObject(jsonObject);

                    String url = "accounttransfers";
                    String response = httpClientBridge.makeRequest(url);

                    //System.err.println("--------------response is -----------"+response);
                    MakerCheckerEntry makerCheckerEntry = MakerCheckerEntry.fromHttpResponse(response);
                    return makerCheckerEntry;
                }
            }
        }
        return null ;

    }

    private static BigDecimal sweepingBalance(BigDecimal threshold ,SavingsAccount sourceAccount ,BigDecimal transferAmount){

        /**
         * Added 21/09/2022
         * First update source account to get actual balance
         */
        Long accountId = sourceAccount.getId();
        sourceAccount = SavingsRequest.savingsAccount(accountId);
        BigDecimal sourceAccountBalance  = sourceAccount.getAccountBalance();

        BigDecimal difference = sourceAccountBalance.subtract(transferAmount);
        /// 350.7 -350.9 = -0.2
        /// 350.9 - 350.9 = 0
        /// 350.9 - 350.7 = 0.2

        int cmp = difference.compareTo(threshold);

        if(cmp <= 0){
            return sourceAccountBalance;
        }

        return transferAmount ;
    }

    public static boolean transactionStatus(MakerCheckerEntry makerCheckerEntry){
        if(makerCheckerEntry.hasErrors()){
            return false ;
        }
        return true ;
    }

    public static SavingsAccount getAccountFromStreamedList(List<SavingsAccount> list ,Long productId){

        Predicate<SavingsAccount> equals = (e)-> productId.equals(e.getSavingsProductId());

        return list.stream().filter(equals).findFirst().orElse(null);

    }

    public static Boolean shouldDoProcess(STAGING staging ,STAGING permission ,boolean allowStaging){

        boolean allow = true;
        if(!allowStaging){
            return allow ;
        }
        //5 (none ) > pay loan (3)
        if(staging.ordinal() > permission.ordinal()){
            allow = false ;
        }

        if(staging == STAGING.SUCCESS){
            allow=false;
        }
        // 2 < 4
        return allow ;

    }
}
