package com.wese.weseaddons.bereaudechange.action;


import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser ;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.dao.FxDealDAO;
import com.wese.weseaddons.bereaudechange.dao.FxEquivalentDAO;
import com.wese.weseaddons.bereaudechange.dao.MoneyAccountTransactionsDAO;
import com.wese.weseaddons.bereaudechange.dao.TransactionChargeDAO;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.enumerations.T_ACCOUNT;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.helper.TradingAlgorithm;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.bereaudechange.session.FxReload;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.pojo.Client;

import java.util.List;

public class FxDetailedTransactionAction {


    private long fxDealId ;
    private String tenant ;
    private FxDeal fxDeal ;
    private ObjectNode objectNode ;
    private MoneyAccountTransactions moneyAccountTransactions ;

    public FxDetailedTransactionAction(String tenant ,long id) {
        this.fxDealId = id;
        this.tenant = tenant ;

        init();
    }

    public void init(){

        FxDealDAO fxDealDAO = new FxDealDAO(tenant);

        MoneyAccountTransactionsDAO moneyAccountTransactionsDAO = new MoneyAccountTransactionsDAO(tenant);
        List<MoneyAccountTransactions> moneyAccountTransactionsList = moneyAccountTransactionsDAO.findWhere(fxDealId ,"fx_deal_id");

        if(moneyAccountTransactionsList.isEmpty()){
            return ;
        }

        this.fxDeal = moneyAccountTransactionsList.get(0).getFxDeal();

        Client client = FxSession.getInstance().getTenantSession(tenant).getClient(fxDeal.getClient().getId());

        if(client==null){
            FxReload.getInstance().start();
            client = FxSession.getInstance().getTenantSession(tenant).getClient(fxDeal.getClient().getId());
        }


        AppUser appUser = FxSession.getInstance().getAppUser(tenant ,fxDeal.getAppUser().getId());


        FxEquivalentDAO fxEquivalentDAO = new FxEquivalentDAO(tenant);
        FxEquivalent fxEquivalant = fxEquivalentDAO.findWhere(fxDeal.getId() ,"fx_deal_id");

        this.objectNode = Helper.createObjectNode();

        TradingRates tradingRates = fxDeal.getTradingRates();

        objectNode.putPOJO("tradingRates" ,tradingRatesNode(tradingRates));
        if(fxDeal.isIncludeCharges()){
            objectNode.putPOJO("charges",deductedChargesNode(moneyAccountTransactionsList));

        }
        objectNode.putPOJO("moneyAccounts",moneyAccountNode(moneyAccountTransactionsList));
        objectNode.putPOJO("blotters" ,blotterNode(fxDeal.getBlotter()));
        objectNode.putPOJO("fxdeals" ,fxDealNode(fxDeal));
        objectNode.putPOJO("clients" ,clientNode(client));
        objectNode.putPOJO("appUsers" ,appUserNode(appUser));
        objectNode.putPOJO("fxEquivalants" ,equivalantNode(fxEquivalant));
        objectNode.put("reversed",fxDeal.isReversed());
        
       
    }

    public ObjectNode getObjectNode(){
        return this.objectNode ;
    }

    public ArrayNode tradingRatesNode(TradingRates tradingRates){

        TradingRatesAction tradingRatesAction = new TradingRatesAction();
        ArrayNode arrayNode = Helper.createArrayNode();
        ObjectNode objectNode = tradingRatesAction.createNode(tradingRates);

        if(fxDeal.getTradeType()==TRADE_TYPE.SELL){

            objectNode.put("rate",tradingRates.getSellRate());
            objectNode.put("tick" ,tradingRates.getCurrencyPair().getInverseTick());

        }

        arrayNode.addPOJO(objectNode);
        return arrayNode ;

    }

    public ArrayNode equivalantNode(FxEquivalent fxEquivalant){

        ArrayNode arrayNode = Helper.createArrayNode();
        ObjectNode objectNode = Helper.createObjectNode();

        if(fxEquivalant==null){
            return null;
        }

        FxDeal fxDeal = fxEquivalant.getFxDeal();

        objectNode.put("equivalant" ,fxEquivalant.getEquivalantAmount());
        objectNode.put("currency" ,fxEquivalant.getConversionTradingRates().getCurrencyPair().getBaseCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency().getName());
        objectNode.put("rate" ,fxEquivalant.getConversionTradingRates().getRate());
        objectNode.put("tick" ,fxEquivalant.getConversionTradingRates().getCurrencyPair().getTick());
        arrayNode.addPOJO(objectNode);
        return arrayNode ;
    }

    public ArrayNode clientNode(Client client){
 
        ObjectNode objectNode = Helper.createObjectNode();
        
        objectNode.put("id","Not specified");
        objectNode.put("displayName","Unregistered client");
        
        if(client !=null) {
            objectNode.put("id",client.getId());
            objectNode.put("displayName",client.getDisplayName());
        }
        
        ArrayNode arrayNode = Helper.createArrayNode();
        arrayNode.addPOJO(objectNode);

        return arrayNode ;
    }

      public ArrayNode appUserNode(AppUser appUser){
          
          if(appUser==null) {
              return null ;
          }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",appUser.getId());
        objectNode.put("displayName",appUser.getDisplayName());
        objectNode.put("userName",appUser.getUserName());
        objectNode.put("officeName",appUser.getOfficeName());
        ArrayNode arrayNode = Helper.createArrayNode();
        arrayNode.addPOJO(objectNode);

        return arrayNode ;
    }

    public ArrayNode fxDealNode(FxDeal fxDeal){

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",fxDeal.getId());
        String status = fxDeal.isReversed()==true ? "Reversed":"Closed";
        objectNode.put("isReversed",status);
        objectNode.put("reversed",fxDeal.isReversed());
        objectNode.put("dealType" ,fxDeal.getFxDealType().getCode());
        String date = TimeHelper.timestampToStringWithTimeForSecond(fxDeal.getDate());
        objectNode.put("date",date);
        objectNode.put("clientId" ,fxDeal.getClient().getId());
        objectNode.put("purpose" ,fxDeal.getPurpose().getCode());
        objectNode.put("tradeType",fxDeal.getTradeType().getCode());

        ArrayNode arrayNode = Helper.createArrayNode();
        arrayNode.addPOJO(objectNode);

        return arrayNode ;
    }


    public ArrayNode blotterNode(Blotter blotter){

        ArrayNode arrayNode = Helper.createArrayNode();

        ObjectNode objectNode1 = Helper.createObjectNode();

        objectNode1.put("baseAmount" ,blotter.getBaseAmount());
        objectNode1.put("baseCharges" ,blotter.getBaseCharges());
        objectNode1.put("netBaseAmount",blotter.getNetBaseAmount());
        objectNode1.put("quoteCharges" ,blotter.getQuoteCharges());
        objectNode1.put("quoteAmount" ,blotter.getQuoteAmount());
        objectNode1.put("netQuoteAmount" ,blotter.getNetQuoteAmount());

        arrayNode.addPOJO(objectNode1);

        return arrayNode ;
    }

    public ArrayNode moneyAccountNode(List<MoneyAccountTransactions> moneyAccountTransactionsList){

        ArrayNode arrayNode = Helper.createArrayNode();

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

            arrayNode.addPOJO(objectNode1);
        }

        return arrayNode ;
    }

    public ArrayNode deductedChargesNode(List<MoneyAccountTransactions> moneyAccountTransactionsList){

        ArrayNode arrayNode = Helper.createArrayNode();

        for(MoneyAccountTransactions m : moneyAccountTransactionsList){

            TransactionChargeDAO transactionChargeDAO = new TransactionChargeDAO(tenant);
            List<TransactionCharge> transactionChargeList = transactionChargeDAO.find(m.getId() ,"transaction_id");


            for(TransactionCharge t : transactionChargeList){

                Charges charges = t.getCharges();
                ChargesAction chargesAction = new ChargesAction();
                ObjectNode objectNode2 = chargesAction.createNode(charges);
                arrayNode.addPOJO(objectNode2);
            }
        }
        return arrayNode ;
    }

}
