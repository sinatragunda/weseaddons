package com.wese.weseaddons.bereaudechange.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.FxDealBody;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountDAO;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.helper.Helper;

import java.util.List;


public class TransactFunds {

    private String tenant ;
    private double amount ;
    private boolean includeCharges ;
    private TradingRates tradingRates ;
    private FundsAction fundsAction ;
    private MoneyAccount sourceMoneyAccount ;
    private MoneyAccount destinationMoneyAccount ;
    private MoneyAccountDAO moneyAccountDAO ;

    public TransactFunds(){

    }

    public TransactFunds(FundsAction fundsAction ,String tenant ,boolean includeCharges) {
        this.tenant = tenant ;
        this.includeCharges = includeCharges ;
        this.fundsAction = fundsAction ;
        this.amount = fundsAction.getAmount();
        this.moneyAccountDAO = new MoneyAccountDAO(tenant);
        this.sourceMoneyAccount = fundsAction.getSourceMoneyAccount();
        this.destinationMoneyAccount = fundsAction.getDestinationMoneyAccount();
       // this.tradingCurrency = fxDealBody.getTradingCurrency();

    }


    public ObjectNode transact(){

        switch (fundsAction.getFundsActionType()){
            case DEPOSIT:
                return deposit();
//            case TRANSFER:
//                return transfer();
            case WITHDRAW:
                return withdraw();

        }

        return Helper.statusNodes(false);

    }

    public ObjectNode transfer(){

        if(amount > sourceMoneyAccount.getBalance()){
            return Helper.statusNodes(false).put("message" ,"Insufficient Balance");
        }

        StandardCurrency sourceCurrency = sourceMoneyAccount.getTradingCurrency().getStandardCurrency();
        StandardCurrency destinationCurrency = destinationMoneyAccount.getTradingCurrency().getStandardCurrency();

        if(sourceCurrency.getId() == destinationCurrency.getId()){
            
            withdraw();
            deposit();
            return Helper.statusNodes(true);
        }

        return Helper.statusNodes(true);

       // FxDealBody fxDealBody = new FxDealBody();
       // FxDealAction fxDealAction = new fxDealAction(tenant);

    }

    public ObjectNode withdraw(){

        MoneyAccount transientAccount = getTransientAccount(sourceMoneyAccount);
        double balance = transientAccount.getBalance();

        if(amount > balance){

            double s = amount-balance ;
            return Helper.statusNodes(false).put("message" ,String.format("Insufficient Balance ,you are %.2f short to make this transaction\n",s));

        }

        double charges = 0 ;

        if(includeCharges){

            charges = deductCharges(transientAccount);
        }

        fundsAction.setCharges(charges);

        balance -= amount ;
        moneyAccountDAO.transact(sourceMoneyAccount ,balance);
        return Helper.statusNodes(true);

    }

    public ObjectNode deposit(){

        MoneyAccount transientAccount = getTransientAccount(destinationMoneyAccount);
        double balance = transientAccount.getBalance();
        double charges = 0 ;

        if(includeCharges){
            charges = deductCharges(transientAccount);
        }

        fundsAction.setCharges(charges);
        balance += amount ;


        moneyAccountDAO.transact(transientAccount,balance);
        return Helper.statusNodes(true);

    }

    public MoneyAccount getTransientAccount(MoneyAccount moneyAccount){

        MoneyAccount transientAccount = moneyAccountDAO.find(moneyAccount.getId());
        return transientAccount ;

    }

    public double deductCharges(MoneyAccount moneyAccount){

        double amount = 0 ;

        for(Charges charges : moneyAccount.getChargesList()){

            amount += charges.getAmount();
        }

        return amount ;

    }
}
