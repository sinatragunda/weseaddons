/*

    Created by Sinatra Gunda
    At 6:24 AM on 8/23/2021

*/
package com.wese.weseaddons.helper;

import com.wese.weseaddons.batchprocessing.processor.ProcessClientDeposits;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.SavingsAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import com.wese.weseaddons.batchprocessing.helper.Parameters;

public class SavingsAccountHelper {

    private SavingsAccount savingsAccount = null ;

    private MakerCheckerEntry makerCheckerEntry = null ;

    public boolean deposit(Long clientId , Long savingsProductId , BigDecimal amount ,Parameters parameters){

        SavingsRequest savingsRequest = new SavingsRequest();
        List<SavingsAccount> savingsAccountList = savingsRequest.getClientSavingsAccount(new Client(clientId));
        List<SavingsAccount> savingsAccountListTemp = savingsAccountList.stream().filter(e->{
            return e.getSavingsProductId()==savingsProductId;
        }).collect(Collectors.toList());

        if(savingsAccountListTemp.isEmpty()){
            //load local share savings account here son
            return false;
        }
        this.savingsAccount = savingsAccountListTemp.get(0);
        boolean status = false ;
        int cmp = amount.compareTo(BigDecimal.ZERO);

        if(cmp >= 1){
            ProcessClientDeposits processClientDeposits = new ProcessClientDeposits(savingsAccount ,amount.doubleValue() ,parameters);
            makerCheckerEntry = processClientDeposits.deposit();
        }
        return makerCheckerEntry.success();
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public MakerCheckerEntry getMakerCheckerEntry(){
        return this.makerCheckerEntry ;
    }
}
