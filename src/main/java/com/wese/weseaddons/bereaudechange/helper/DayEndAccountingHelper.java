package com.wese.weseaddons.bereaudechange.helper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.DayEndAccountBody;
import com.wese.weseaddons.bereaudechange.dao.FxDealDAO;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser;

import java.time.Instant;
import java.util.*;

public class DayEndAccountingHelper {

    private String tenant ;
    private long officerId ;


    public DayEndAccountingHelper(String tenant, long officerId){

        this.tenant = tenant;
        this.officerId = officerId;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public ObjectNode summary(DayEndAccountBody dayEndAccountBody){

        List<FxDeal> fxDealList = new ArrayList<>();
        FxDealDAO fxDealDAO = new FxDealDAO(tenant);

        if(dayEndAccountBody!=null){

            officerId = dayEndAccountBody.getOfficerId();
            long fromDate = dayEndAccountBody.getFromDate();
            long toDate = dayEndAccountBody.getToDate();
            
            if(toDate < fromDate) {
         
                return Helper.statusNodes(false).put("message", Constants.invalidDate);
            }

            fxDealList = fxDealDAO.findWhereDates(officerId ,"officer_id" ,fromDate ,toDate);

        }
        else{


            fxDealList = fxDealDAO.findWhere(officerId ,"officer_id");
            
            
            for(int i =0 ; i < fxDealList.size() ;++i){

                FxDeal fxDeal = fxDealList.get(i);
                if(!isDealToday(fxDeal)) {
                    fxDealList.remove(i);
                    --i ;
                }
            }

        }

        Map<Long,ObjectNode> fxBaseSummary = new HashMap<>();
        Map<Long ,ObjectNode> fxQuoteSummary = new HashMap<>();

        Map<Long ,ObjectNode> summaryNode = new HashMap<>();

        for(FxDeal fxDeal : fxDealList){

            CurrencyPair currencyPair = fxDeal.getTradingRates().getCurrencyPair();
            long id = currencyPair.getId();

            FinancialInstrument financialInstrument = currencyPair.getBaseCurrencyMoneyAccount().getTradingCurrency().getFinancialInstrument();
            StandardCurrency standardCurrency = currencyPair.getBaseCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency();

            double baseAmount = fxDeal.getBlotter().getNetBaseAmount();

            long baseAccountId = currencyPair.getBaseCurrencyMoneyAccount().getId();

            DayEndAccount dayEndAccount = new DayEndAccount(standardCurrency ,financialInstrument ,baseAmount);


            if(fxBaseSummary.containsKey(baseAccountId)){

                ObjectNode objectNode = fxBaseSummary.get(baseAccountId);
                double temp = objectNode.get("amount").asDouble() + dayEndAccount.getAmount();
                objectNode.remove("amount");
                objectNode.put("amount" ,TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL,temp));
                fxBaseSummary.replace(baseAccountId ,objectNode);
            }

            fxBaseSummary.putIfAbsent(baseAccountId ,createNode(dayEndAccount ,"Withdraw" ,currencyPair.getBaseCurrencyMoneyAccount()));


            long quouteAccountId = currencyPair.getQuoteCurrencyMoneyAccount().getId();
            double quoteAmount = fxDeal.getBlotter().getQuoteAmount();

            FinancialInstrument financialInstrument1 = currencyPair.getQuoteCurrencyMoneyAccount().getTradingCurrency().getFinancialInstrument();
            StandardCurrency standardCurrency1 = currencyPair.getQuoteCurrencyMoneyAccount().getTradingCurrency().getStandardCurrency();

            DayEndAccount dayEndAccount1 = new DayEndAccount(standardCurrency1 ,financialInstrument1 ,quoteAmount);


            if(fxQuoteSummary.containsKey(quouteAccountId)){

                ObjectNode objectNode = fxQuoteSummary.get(quouteAccountId);
                double temp = objectNode.get("amount").asDouble() + dayEndAccount1.getAmount();
                objectNode.remove("amount");
                objectNode.put("amount" ,TradingAlgorithm.roundValue(ROUNDING_RULE.NATURAL, temp));

                fxQuoteSummary.replace(quouteAccountId ,objectNode);
            }

            fxQuoteSummary.putIfAbsent(quouteAccountId ,createNode(dayEndAccount1 ,"Deposit" ,currencyPair.getQuoteCurrencyMoneyAccount()));
        }

        ArrayNode baseNode = Helper.createArrayNode();

        for(Long m : fxBaseSummary.keySet()){

            baseNode.add(fxBaseSummary.get(m));

        }

        ArrayNode quoteNode = Helper.createArrayNode();

        for(Long m : fxQuoteSummary.keySet()){

            quoteNode.add(fxQuoteSummary.get(m));
        }

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.putPOJO("base",baseNode);
        objectNode.putPOJO("quote",quoteNode);

        AppUser appUser = FxSession.getInstance().getAppUser(tenant ,officerId);

        ObjectNode objectNode1 = Helper.createObjectNode();

        objectNode1.put("displayName",appUser.getDisplayName());
        objectNode1.put("userName",appUser.getUserName());
        objectNode1.put("officeName",appUser.getOfficeName());

        ArrayNode arrayNode = Helper.createArrayNode();
        arrayNode.add(objectNode1);

        objectNode.putPOJO("appUsers",arrayNode);

        return objectNode ;

    }

    public ObjectNode createNode(DayEndAccount dayEndAccount ,String tAccount ,MoneyAccount moneyAccount){

        ObjectNode objectNode = Helper.createObjectNode();

        objectNode.put("id" ,moneyAccount.getId());
        objectNode.put("tAccount",tAccount);
        objectNode.put("instrumentType",dayEndAccount.getFinancialInstrument().getFinancialInstrumentType().toString());
        objectNode.put("amount",dayEndAccount.getAmount());
        objectNode.put("baseCurrency",dayEndAccount.getStandardCurrency().getName());
        objectNode.put("accountName",moneyAccount.getName());

        return objectNode ;

    }


    public boolean isDealToday(FxDeal fxDeal){

        long time = fxDeal.getDate();
        Date date = Date.from(Instant.ofEpochSecond(time));
        Date dateNow = TimeHelper.dateNow("Africa/Harare");
        
        System.out.println("Transaction date is "+date);
        System.out.println("Date now is "+dateNow.getDate());
        
        if(dateNow.getYear()==date.getYear()){
            
            
            if(date.getMonth()==dateNow.getMonth()){
                if(date.getDate()==dateNow.getDate()){
                    return true ;
                }
            }
        }
        return false ;
    }
}
