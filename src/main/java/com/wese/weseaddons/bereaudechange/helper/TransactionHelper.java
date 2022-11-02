package com.wese.weseaddons.bereaudechange.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.CurrencyPairDAO;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountDAO;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountTransactionsDAO;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccount;
import com.wese.weseaddons.bereaudechange.pojo.MoneyAccountTransactions;

import java.util.List;

public class TransactionHelper {

    private String tenant ;
    private TRADE_TYPE tradeType ;
    private CurrencyPair currencyPair ;
    private MoneyAccount baseMoneyAccount ;
    private MoneyAccount quoteMoneyAccount ;

    public TransactionHelper() {
    }

    public TransactionHelper(String tenant , long id , TRADE_TYPE tradeType){

        this.tradeType = tradeType ;
        CurrencyPairDAO currencyPairDAO = new CurrencyPairDAO(tenant);
        this.currencyPair = currencyPairDAO.find(id);

    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public boolean hasSufficientAccount(double amount){

        if(currencyPair==null){
            return false ;
        }

        baseMoneyAccount = currencyPair.getBaseCurrencyMoneyAccount();

        if(tradeType == TRADE_TYPE.SELL){

            baseMoneyAccount = currencyPair.getQuoteCurrencyMoneyAccount();
        }

        double afterSell = baseMoneyAccount.getBalance()-amount ;


        if(amount > baseMoneyAccount.getBalance() || baseMoneyAccount.getLowerLimit() > afterSell ){
            return false ;
        }

        return true ;
    }

    public void depositFunds(FxDeal fxDeal , MoneyAccount moneyAccount){
        ///some heavy concurrency is needed here

        MoneyAccountTransactions moneyAccountTransactions = new MoneyAccountTransactions();
        moneyAccountTransactions.setMoneyAccount(moneyAccount);
        moneyAccountTransactions.setFxDeal(fxDeal);


        double amount = fxDeal.getBlotter().getNetQuoteAmount();
        double charges = fxDeal.getBlotter().getQuoteCharges();
        double runningBalance = amount + moneyAccount.getBalance();

        moneyAccountTransactions.setRunningBalance(runningBalance);
        moneyAccountTransactions.setAmount(amount);
        moneyAccountTransactions.setCharges(charges);
        moneyAccountTransactions.settAccount(T_ACCOUNT.DEPOSIT);

        save(moneyAccountTransactions ,tenant);
        MoneyAccountDAO moneyAccountDAO = new MoneyAccountDAO(tenant);
        moneyAccountDAO.transact(moneyAccount ,runningBalance);


    }


    public void withdrawFunds(FxDeal fxDeal ,MoneyAccount moneyAccount){

        MoneyAccountTransactions moneyAccountTransactions = new MoneyAccountTransactions();
        moneyAccountTransactions.setMoneyAccount(moneyAccount);
        moneyAccountTransactions.setFxDeal(fxDeal);

        double amount = fxDeal.getBlotter().getNetBaseAmount();
        double charges = fxDeal.getBlotter().getBaseCharges();

        double runningBalance = moneyAccount.getBalance() - amount;

        moneyAccountTransactions.setRunningBalance(runningBalance);
        moneyAccountTransactions.setAmount(amount);
        moneyAccountTransactions.setCharges(charges);
        moneyAccountTransactions.settAccount(T_ACCOUNT.WITHDRAW);
        save(moneyAccountTransactions ,tenant);

        MoneyAccountDAO moneyAccountDAO = new MoneyAccountDAO(tenant);
        moneyAccountDAO.transact(moneyAccount ,runningBalance);



    }

    public void save(MoneyAccountTransactions moneyAccountTransactions ,String tenant){

        MoneyAccountTransactionsDAO moneyAccountTransactionsDAO = new MoneyAccountTransactionsDAO(tenant);
        moneyAccountTransactionsDAO.create(moneyAccountTransactions);
    }

    public double unrealizedChargesProfit(List<MoneyAccountTransactions> moneyAccountTransactions){

        double sum = moneyAccountTransactions.stream().map(MoneyAccountTransactions::getCharges).mapToDouble(Double::doubleValue).sum();
        return sum ;

    }

    public double netFloat(MoneyAccount moneyAccount){
        double avaialable = moneyAccount.getUpperLimit() - (moneyAccount.getBalance() - moneyAccount.getLowerLimit());
        return avaialable ;
    }

    public double availableFloat(MoneyAccount moneyAccount){

        if(moneyAccount.getBalance() < moneyAccount.getLowerLimit()){
            return  0 ;
        }

        double avaialable = moneyAccount.getBalance() - moneyAccount.getLowerLimit();
        return avaialable ;
    }
}
