/*

    Created by Sinatra Gunda
    At 1:30 PM on 6/11/2021

*/
package com.wese.weseaddons.batchprocessing.helper;

import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.batchprocessing.enumerations.STAGING;
import com.wese.weseaddons.batchprocessing.enumerations.TRANSACTION_STATUS;
import com.wese.weseaddons.batchprocessing.pojo.SsbPayment;
import com.wese.weseaddons.batchprocessing.processor.ProcessClientDeposits;
import com.wese.weseaddons.helper.ComparatorUtility;
import com.wese.weseaddons.helper.MoneyHelper;
import com.wese.weseaddons.helper.ShareAccountHelper;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.networkrequests.SharesRequest;
import com.wese.weseaddons.pojo.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ShareAccountPreloadingHelper {

    private  boolean presharePurchase ;
    private Long savingsProductId ;
    private Long clientId ;
    private BigDecimal amount ;
    private BigDecimal balance = BigDecimal.ZERO;
    private Parameters parameters ;
    private SsbPayment ssbPayment ;
    private boolean status ;
    private TRANSACTION_STATUS transactionStatus ;
    private Double transactionAmount = 0.0;
    private ShareAccount shareAccount= null;
    private MakerCheckerEntry makerCheckerEntry = null;

    public ShareAccountPreloadingHelper(SsbPayment ssbPayment , Parameters parameters , BigDecimal amount , Long clientId){
        this.presharePurchase = ssbPayment.isPreshareAccountPurchase();
        this.amount = amount ;
        this.parameters = parameters;
        this.clientId = clientId;
        this.ssbPayment = ssbPayment ;
        this.balance = amount; 
    }

    public ShareAccountPreloadingHelper(boolean presharePurchase,Long savingsProductId, Long clientId, BigDecimal amount , Parameters parameters) {
        this.presharePurchase = presharePurchase;
        this.savingsProductId = savingsProductId;
        this.clientId = clientId;
        this.amount = amount;
        this.parameters = parameters ;
        this.balance = amount;
    }

    public BigDecimal purchaseShares(boolean depositExcess , ExcelClientData excelClientData , List excessBalanceMoved ,boolean isDDAFunded){

        BigDecimal ret = amount ;

        SharesRequest sharesRequest = new SharesRequest();
        List<ShareAccount> shareAccounts = sharesRequest.clientShareAccounts(clientId);

        List<ShareAccount> shareAccountsList = shareAccounts.stream().filter(shareAccount ->{
            return shareAccount.getProductId()==ssbPayment.getShareProductId();
        }).collect(Collectors.toList());


        if(shareAccountsList.isEmpty()){
            transactionStatus = TRANSACTION_STATUS.NO_ACTIVE_SHARE_ACCOUNT;
            return ret ;
        }

        this.shareAccount = shareAccountsList.get(0) ;
        Long shareAccountId = shareAccount.getId();

        /// get money from savings account to shares instead

        /// set share account zone id

        ShareProduct shareProduct = ShareAccountHelper.shareProduct(ssbPayment.getShareProductId());
        ShareAccountHelper shareAccountHelper= new ShareAccountHelper();

        if(isDDAFunded){

            SavingsAccount ddaSavingsAccount = excelClientData.getDrawdownAccount();
            // a lot of things have to be present here to continue else we have errors
            // need to validate if amount is 0 here so that it skips
            // we need to skip these in future so that any zero balance transaction is intercepted
            if(ComparatorUtility.isBigDecimalZero(amount)){
                return BigDecimal.ZERO;
            }

            transactionStatus = shareAccountHelper.purchaseSharesViaTransfer(shareProduct ,shareAccount ,ddaSavingsAccount ,ssbPayment ,amount);
            balance = new BigDecimal(shareAccountHelper.balance());
            transactionAmount = shareAccountHelper.purchasedSharesAmount();
            this.makerCheckerEntry = shareAccountHelper.getMakerCheckerEntry();
            return balance ;
        }

        // not dada funded
        
        transactionStatus = shareAccountHelper.purchaseShares(shareProduct ,shareAccount,ssbPayment ,amount.doubleValue());
        this.makerCheckerEntry = shareAccountHelper.getMakerCheckerEntry();

        this.balance = new BigDecimal(shareAccountHelper.balance());

        transactionAmount = shareAccountHelper.purchasedSharesAmount();

        if(depositExcess){

            SavingsRequest savingsRequest = SavingsRequest.instance;
            List<SavingsAccount> savingsAccountList = savingsRequest.getClientSavingsAccount(new Client(clientId));
            List<SavingsAccount> savingsAccountListTemp = savingsAccountList.stream().filter(e->{
                return e.getSavingsProductId()==ssbPayment.getSettleSavingsProductId();
            }).collect(Collectors.toList());

            SavingsAccount savingsAccount = null ;
            if(savingsAccountListTemp.isEmpty()){
                //load local share savings account here son
                shareAccount = sharesRequest.shareAccount(shareAccount.getId());
                savingsAccount = getShareAccountSavingsAccount(savingsAccountList ,shareAccount.getSavingsAccountNumber());
            }
            else {
                savingsAccount = savingsAccountListTemp.get(0);
            }

            if(MoneyHelper.depositable(balance)){

                ProcessClientDeposits processClientDeposits = new ProcessClientDeposits(savingsAccount ,balance.doubleValue() ,parameters);
                status  = processClientDeposits.deposit(false);
                if(status){

                    MakerCheckerEntry entry = processClientDeposits.getMakerCheckerEntry();

                    excelClientData.setAmount(balance);
                    excelClientData.setStaging(STAGING.SUCCESS);

                    ///set zoning here for moving excess balance to settle account 
                    Long savingsAccountId = savingsAccount.getId();
                    
                    // where does money go here ? 
                    //Long transactionId = entry.getTransactionId();
                    Long transactionId = entry.getResourceId();

                    excelClientData.setResourceId(transactionId);
                    excelClientData.setObjectId(savingsAccountId);

                    excessBalanceMoved.add(new ExcelClientData(excelClientData));
                    balance = BigDecimal.ZERO;
                }
            }
            //return BigDecimal.ZERO ;
        }
        ///after zoning conflicts with excess balance array 
        excelClientData.setObjectId(shareAccountId);
        ///set the 10000 used to buy shares here son
        excelClientData.setAmount(new BigDecimal(transactionAmount));
        return balance;
    }

    public SavingsAccount getShareAccountSavingsAccount(List<SavingsAccount> list, String accountNumber){

        List<SavingsAccount> savingsAccountList = list.stream().filter(e->{
            return e.getAccountNumber().equalsIgnoreCase(accountNumber);
        }).collect(Collectors.toList());


        SavingsAccount savingsAccount= null ;
        if(!savingsAccountList.isEmpty()){
            savingsAccount = savingsAccountList.get(0);
        }
        return savingsAccount;
    }

    public TRANSACTION_STATUS getTransactionStatus() {
        return transactionStatus;
    }

    public boolean getStatus(){
        return this.status ;
    }

    public BigDecimal getTransactionAmount(){
        return new BigDecimal(transactionAmount);
    }

    public ShareAccount getShareAccount(){
        return this.shareAccount;
    }

    public BigDecimal balance(){
        return balance;
    }

    public MakerCheckerEntry getMakerCheckerEntry() {
        return makerCheckerEntry;
    }
}
