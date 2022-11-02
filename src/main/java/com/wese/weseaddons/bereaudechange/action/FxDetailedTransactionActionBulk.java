package com.wese.weseaddons.bereaudechange.action;


import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser ;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxBulkDealDAO;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountTransactionsDAO;
import com.wese.weseaddons.bereaudechange.dao.TransactionChargeDAO;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.pojo.Client;

import java.util.List;



public class FxDetailedTransactionActionBulk {


    private long fxDealId ;
    private String tenant ;
    private ObjectNode objectNode ;
    private MoneyAccountTransactions moneyAccountTransactions ;
    private ArrayNode chargesArrayNode ;
    private ArrayNode moneyAccountArrayNode ;

    public FxDetailedTransactionActionBulk(String tenant ,long id) {

        this.fxDealId = id;
        this.tenant = tenant ;

        init();
    }

    public void init(){

        FxBulkDealDAO fxBulkDealDAO = new FxBulkDealDAO(tenant);

        List<FxBulkDeal> fxBulkDealList = fxBulkDealDAO.find(fxDealId);

        if(fxBulkDealList.isEmpty()){
            return;
        }

        ArrayNode clientArrayNode = Helper.createArrayNode() ;
        this.chargesArrayNode = Helper.createArrayNode() ;
        ArrayNode appUserArrayNode = Helper.createArrayNode() ;
        ArrayNode tradingRatesArrayNode = Helper.createArrayNode();
        ArrayNode fxDealArrayNode = Helper.createArrayNode();
        ArrayNode blotterArrayNode = Helper.createArrayNode() ;
        this.moneyAccountArrayNode = Helper.createArrayNode();

        for(FxBulkDeal fxBulkDeal : fxBulkDealList){

            MoneyAccountTransactionsDAO moneyAccountTransactionsDAO = new MoneyAccountTransactionsDAO(tenant);
            List<MoneyAccountTransactions> moneyAccountTransactionsList = moneyAccountTransactionsDAO.findWhere(fxBulkDeal.getFxDeal().getId() ,"fx_deal_id");

            if(moneyAccountTransactionsList.isEmpty()){
                return ;
            }


            FxDeal fxDeal = moneyAccountTransactionsList.get(0).getFxDeal();

            Client client = FxSession.getInstance().getTenantSession(tenant).getClient(fxDeal.getClient().getId());
            AppUser appUser = FxSession.getInstance().getAppUser(tenant ,fxDeal.getAppUser().getId());


            this.objectNode = Helper.createObjectNode();

            TradingRates tradingRates = fxDeal.getTradingRates();
            Blotter blotter = fxDeal.getBlotter();

            deductedChargesNode(moneyAccountTransactionsList);
            moneyAccountNode(moneyAccountTransactionsList);

            clientArrayNode.addPOJO(clientNode(client));
            appUserArrayNode.addPOJO(appUserNode(appUser));
            tradingRatesArrayNode.addPOJO(tradingRatesNode(tradingRates));
            fxDealArrayNode.addPOJO(fxDealNode(fxDeal));
            blotterArrayNode.addPOJO(blotterNode(blotter));


        }

        objectNode.putPOJO("tradingRates" ,tradingRatesArrayNode);
        objectNode.putPOJO("charges",chargesArrayNode);
        objectNode.putPOJO("moneyAccounts",moneyAccountArrayNode);
        objectNode.putPOJO("blotters" ,blotterArrayNode);
        objectNode.putPOJO("fxdeals" ,fxDealArrayNode);
        objectNode.putPOJO("clients" ,clientArrayNode);
        objectNode.putPOJO("appUsers" ,appUserArrayNode);

    }

    public ObjectNode getObjectNode(){
        return this.objectNode ;
    }

    public ObjectNode tradingRatesNode(TradingRates tradingRates){

        TradingRatesAction tradingRatesAction = new TradingRatesAction();
        ArrayNode arrayNode = Helper.createArrayNode();
        return tradingRatesAction.createNode(tradingRates);

    }

    public ObjectNode clientNode(Client client){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",client.getId());
        objectNode.put("displayName",client.getDisplayName());
        return objectNode ;
    }

    public ObjectNode appUserNode(AppUser appUser){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",appUser.getId());
        objectNode.put("displayName",appUser.getDisplayName());
        objectNode.put("userName",appUser.getUserName());
        objectNode.put("officeName",appUser.getOfficeName());

        return objectNode ;
    }

    public ObjectNode fxDealNode(FxDeal fxDeal){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",fxDeal.getId());
        String status = fxDeal.isReversed()==true ? "Reversed":"Closed";
        objectNode.put("isReversed",status);
        objectNode.put("dealType" ,fxDeal.getFxDealType().getCode());
        String date = TimeHelper.timestampToStringWithTimeForSecond(fxDeal.getDate());
        objectNode.put("date",date);
        objectNode.put("clientId" ,fxDeal.getClient().getId());

        return objectNode ;
    }


    public ObjectNode blotterNode(Blotter blotter){

        ObjectNode objectNode1 = Helper.createObjectNode();

        objectNode1.put("baseAmount" ,blotter.getBaseAmount());
        objectNode1.put("baseCharges" ,blotter.getBaseCharges());
        objectNode1.put("netBaseAmount",blotter.getNetBaseAmount());
        objectNode1.put("quoteCharges" ,blotter.getQuoteCharges());
        objectNode1.put("quoteAmount" ,blotter.getQuoteAmount());
        objectNode1.put("netQuoteAmount" ,blotter.getNetQuoteAmount());

        return objectNode1 ;
    }

    public void moneyAccountNode(List<MoneyAccountTransactions> moneyAccountTransactionsList){


        for(MoneyAccountTransactions m : moneyAccountTransactionsList){

            ObjectNode objectNode1 = Helper.createObjectNode();

            double entryBalance = 0 ;

            ROUNDING_RULE roundingRule = m.getFxDeal().getTradingRates().getCurrencyPair().getRoundingRule() ;

            if(m.gettAccount()== T_ACCOUNT.DEPOSIT){
                double temp = m.getRunningBalance() - m.getAmount() ;
                entryBalance = TradingAlgorithm.roundValue(roundingRule ,temp) ;


            }
            if(m.gettAccount()==T_ACCOUNT.WITHDRAW){
                double temp = m.getRunningBalance() + m.getAmount() ;
                entryBalance = TradingAlgorithm.roundValue(roundingRule ,temp);
            }


            objectNode1.put("moneyAccountId",m.getMoneyAccount().getId());
            objectNode1.put("entryBalance" ,entryBalance);

            double temp = TradingAlgorithm.roundValue(roundingRule ,m.getRunningBalance());
            objectNode1.put("runningBalance" ,temp);

            objectNode1.put("tAccount",m.gettAccount().getCode());
            objectNode1.put("charges" ,m.getCharges());
            objectNode1.put("name" ,m.getMoneyAccount().getName());
            objectNode1.put("currencySymbol" ,m.getMoneyAccount().getTradingCurrency().getSymbol());

            moneyAccountArrayNode.addPOJO(objectNode1);
        }


    }

    public void deductedChargesNode(List<MoneyAccountTransactions> moneyAccountTransactionsList){

        for(MoneyAccountTransactions m : moneyAccountTransactionsList) {

            TransactionChargeDAO transactionChargeDAO = new TransactionChargeDAO(tenant);
            List<TransactionCharge> transactionChargeList = transactionChargeDAO.find(m.getId(), "transaction_id");


            for (TransactionCharge t : transactionChargeList) {

                Charges charges = t.getCharges();
                ChargesAction chargesAction = new ChargesAction();
                ObjectNode objectNode2 = chargesAction.createNode(charges);
                chargesArrayNode.addPOJO(objectNode2);
            }
        }
    }

}
