/*

    Created by Sinatra Gunda
    At 11:48 AM on 6/22/2021

*/
package com.wese.weseaddons.helper;

import com.wese.weseaddons.batchprocessing.enumerations.TRANSACTION_STATUS;
import com.wese.weseaddons.batchprocessing.pojo.SsbPayment;
import com.wese.weseaddons.networkrequests.SharesRequest;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.pojo.ShareAccount;
import com.wese.weseaddons.pojo.ShareProduct;

import java.math.BigDecimal;

public class ShareAccountHelper {

    private Double balance = 0.0;
    private Double purchasedSharesAmount = 0.0;
    private MakerCheckerEntry makerCheckerEntry ;

    public static ShareProduct shareProduct(Long shareProductId){
        SharesRequest sharesRequest = new SharesRequest();
        ShareProduct shareProduct = sharesRequest.shareProduct(shareProductId);
        return shareProduct ;
    }

    public TRANSACTION_STATUS purchaseShares(ShareProduct shareProduct, ShareAccount shareAccount , SsbPayment ssbPayment , Double amount){

        Long approvedShares = shareAccount.getTotalApprovedShares();
        Long maximumShares = shareProduct.getMaximumShares();
        String date = ssbPayment.getPostedDate();
        boolean approveAdditionalShares = ssbPayment.getOverrideMakerCheckerTasks();

        SharesRequest sharesRequest = new SharesRequest();

        if(approvedShares < maximumShares){
            
            // buy shares here
            int numberOfShares = numberOfShares(shareProduct ,shareAccount ,amount);

            /// purchasedSharesAmount is the total number of shares purchased 

            /// then we transfer equivalent funds only  
            
            this.makerCheckerEntry = sharesRequest.applyAdditionalShares(shareAccount.getId() ,numberOfShares ,date);
            
            if(makerCheckerEntry.hasErrors()){
                // when internal error then balance is equal to amount 
                setBalance(amount);
                return TRANSACTION_STATUS.SHARES_INTERNAL_ERROR ;
            }

            if(makerCheckerEntry !=null){
                ///status is pending here
                if(approveAdditionalShares){
                    int resourceId = makerCheckerEntry.getChanges().getAdditionalshares();
                    return approveAdditionalShares(sharesRequest,shareAccount.getId(), resourceId ,date);
                }
                return TRANSACTION_STATUS.SHARE_TRANSACTION_PENDING;
            }

            // problems regarding getting actual balance after all transactions are done 
            //setBalance(amount);
            return TRANSACTION_STATUS.SHARES_BOUGHT_SUCCESSFULLY ;
        }

        /// at this stage shares amount should be 0.0
        /// that could have been wrong if share purchase is reached then amount should be the same as orignal amount 
        setBalance(amount);
        return TRANSACTION_STATUS.MAXIMUM_SHARES_REACHED ;
    }


    public TRANSACTION_STATUS purchaseSharesViaTransfer(ShareProduct shareProduct, ShareAccount shareAccount , SavingsAccount savingsAccount, SsbPayment ssbPayment ,BigDecimal amount){

        Long approvedShares = shareAccount.getTotalApprovedShares();
        Long maximumShares = shareProduct.getMaximumShares();
        boolean approveAdditionalShares = ssbPayment.getOverrideMakerCheckerTasks();
        String date = ssbPayment.getPostedDate();
        balance = amount.doubleValue() ;

        SharesRequest sharesRequest = new SharesRequest();

        /**
         * 28/09/2022 at 1551
         * Update account balance always so that account always has the latest balance
         */
        //System.err.println("-----------balance before update -------"+savingsAccount.getAccountBalance());

        savingsAccount = savingsAccount.update();

        //System.err.println("-----------balance before after -------"+savingsAccount.getAccountBalance());

        if(approvedShares < maximumShares){
            //// buy shares here
            int numberOfShares = numberOfSharesViaTransfer(shareProduct ,shareAccount ,savingsAccount,amount);

            /// purchasedSharesAmount is the total number of shares purchased
            /// then we transfer equivalent funds only

            this.makerCheckerEntry = sharesRequest.applyAdditionalSharesViaTransfer(savingsAccount ,shareAccount , new BigDecimal(purchasedSharesAmount) ,date);
            
            if(makerCheckerEntry.hasErrors()){
                balance = amount.doubleValue();        
                return TRANSACTION_STATUS.SHARES_INTERNAL_ERROR ;
            }

            // maker checker for purchase via savings should not use changes object from maker checker entry but use resource id or transaction id
            if(makerCheckerEntry !=null){
                ///status is pending here
                if(approveAdditionalShares){
                    Long resourceId = makerCheckerEntry.getTransactionId();
                    return approveAdditionalShares(sharesRequest,shareAccount.getId(), resourceId.intValue() ,date);
                }
                return TRANSACTION_STATUS.SHARE_TRANSACTION_PENDING;
            }

            return TRANSACTION_STATUS.SHARES_BOUGHT_SUCCESSFULLY ;
        }

        /// at this stage shares amount should be 0.0 ? .Maximum shares reached so leftover amount should be equal to amount  
        setBalance(amount.doubleValue());
        return TRANSACTION_STATUS.MAXIMUM_SHARES_REACHED ;
    }


    public int numberOfSharesViaTransfer(ShareProduct shareProduct , ShareAccount shareAccount , SavingsAccount savingsAccount , BigDecimal amount){

        // we need to figure out balance in savings account
        BigDecimal savingsAccountBalance = savingsAccount.getAccountBalance();

        if(savingsAccountBalance.compareTo(amount) < 0){
            amount = savingsAccountBalance ;
        }
        return numberOfShares(shareProduct ,shareAccount ,amount.doubleValue());
    }


    public int numberOfShares(ShareProduct shareProduct ,ShareAccount shareAccount,Double amount){

        /// if max is 10000
        Long maxShares = shareProduct.getMaximumShares();
        Long approved = shareAccount.getTotalApprovedShares();
        Long shortfall = maxShares - approved ;
        //10000 -1  = 9999

        Double unitPrice = shareProduct.getUnitPrice();

        Double valueOfShortfall = shortfall * shareProduct.getUnitPrice();

        ///if value of shortfall is 5000
        /// if amount is 4000 ,lets buy the ones for 4000 so we say 4000 / unit price
        /// 9999 > 15000
        Double numberOfShares = null ;
        if(valueOfShortfall > amount){
            purchasedSharesAmount = amount;
            numberOfShares = amount/unitPrice;
            leftOverAmount(0.0 ,numberOfShares);
            return numberOfShares.intValue();
        }
        ///value of shortfall 9999
        ///value of amount  15000
        purchasedSharesAmount = valueOfShortfall;
        numberOfShares = valueOfShortfall/unitPrice ;
        /// difference value of amount and shortfall
        Double diff = amount - valueOfShortfall;
        leftOverAmount(diff ,numberOfShares);
        return numberOfShares.intValue();

    }

    public void leftOverAmount(Double diff ,Double value){
        int shares = value.intValue();
        Double balance = diff  + (value - shares);
        setBalance(balance);
        
    }

    public Double purchasedSharesAmount(){
        return this.purchasedSharesAmount ;
    }

    public TRANSACTION_STATUS approveAdditionalShares(SharesRequest sharesRequest ,Long shareAccountId, int resourceId ,String date){

        boolean status = sharesRequest.approveAdditionalShares(shareAccountId ,resourceId ,date);
        TRANSACTION_STATUS transactionStatus = TRANSACTION_STATUS.SHARES_BOUGHT_SUCCESSFULLY;
        if(!status){
            transactionStatus = TRANSACTION_STATUS.SHARE_TRANSACTION_PENDING;
        }
        return transactionStatus ;
    }

    public Double balance(){
        return this.balance ;
    }

    // added 16/02/2022
    public void setBalance(Double balance){
        this.balance = balance;
    }

    // added 27/03/2022
    public MakerCheckerEntry getMakerCheckerEntry(){
        return this.makerCheckerEntry ;
    }
}