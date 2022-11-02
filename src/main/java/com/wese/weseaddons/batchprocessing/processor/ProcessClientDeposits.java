package com.wese.weseaddons.batchprocessing.processor;


import com.wese.weseaddons.pojo.MakerCheckerEntry;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.batchprocessing.helper.Parameters;
import com.wese.weseaddons.batchprocessing.wese.SavingsDeposit;
import com.wese.weseaddons.utility.ThreadContext;

public class ProcessClientDeposits {

    private SavingsAccount savingsAccount ;
    private double amount;
    private Parameters parameters ;
    private MakerCheckerEntry makerCheckerEntry;

    public ProcessClientDeposits(SavingsAccount savingsAccount ,double amount ,Parameters parameters){
        this.savingsAccount = savingsAccount ;
        this.amount = amount ;
        this.parameters = parameters ;

    }

    public MakerCheckerEntry deposit(){
        SavingsDeposit savingsDeposit = new SavingsDeposit(savingsAccount ,amount ,parameters);
        makerCheckerEntry = savingsDeposit.deposit();
        return makerCheckerEntry;
    }

    
    public boolean deposit(boolean status){
        deposit();
        status = makerCheckerEntry.success();
        return status;
    }

    public MakerCheckerEntry getMakerCheckerEntry(){
        return makerCheckerEntry ;
    }
}
