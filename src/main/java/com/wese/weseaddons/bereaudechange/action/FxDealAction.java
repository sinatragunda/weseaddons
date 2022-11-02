package com.wese.weseaddons.bereaudechange.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.body.DayEndAccountBody;
import com.wese.weseaddons.bereaudechange.body.FxDealBody;
import com.wese.weseaddons.bereaudechange.dao.BlotterDAO;
import com.wese.weseaddons.bereaudechange.dao.CurrencyPairDAO;
import com.wese.weseaddons.bereaudechange.dao.FxDealDAO;
import com.wese.weseaddons.bereaudechange.enumerations.ROUNDING_RULE;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.exceptions.FailedFxDealException;
import com.wese.weseaddons.bereaudechange.exceptions.FxDealSumsException;
import com.wese.weseaddons.bereaudechange.helper.*;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.AppUser;
import com.wese.weseaddons.bereaudechange.pojo.Blotter;
import com.wese.weseaddons.bereaudechange.pojo.CurrencyPair;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.session.FxSession;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.taskmanager.actions.NotificationAction;
import com.wese.weseaddons.taskmanager.body.NotificationRequestBody;

import java.util.List;


public class FxDealAction {

    private FxDeal fxDeal ;
    private String tenant ;
    private FxDealDAO fxDealDAO ;
    private FxDealBody fxDealBody;
    private Blotter blotter ;
    private ForexDealSums forexDealSums ;


    public FxDealAction(String tenant){

        this.tenant = tenant;
        this.fxDealDAO = new FxDealDAO(tenant);
    }

    public FxDealAction(FxDealBody fxDealBody) {

        this.fxDealBody = fxDealBody;
        this.tenant = fxDealBody.getTenant() ;
        this.fxDealDAO = new FxDealDAO(tenant);
    }

    public ObjectNode find(long id){

        FxDeal fxDeal = fxDealDAO.find(id);
        if(fxDeal==null){
            return Helper.statusNodes(false);
        }

        ObjectNode objectNode = createNode(fxDeal);
        objectNode.put("status" ,true);
        return objectNode ;

    }

    public ObjectNode reverse(long id){


        NotificationRequestBody notificationRequestBody = new NotificationRequestBody();
        notificationRequestBody.setTenantIdentifier(tenant);
        notificationRequestBody.setObjectIdentifier(id);
        notificationRequestBody.setUserId(1);
        notificationRequestBody.setCode("Reverse Fx Deal");


        NotificationAction notificationAction = new NotificationAction(notificationRequestBody);
        notificationAction.createNotification();

        return Helper.statusNodes(true).put("message" ,"Fx Deal is waiting approval for reversal");
    }

    public ObjectNode approveFxDealReversal(long id){

        boolean reverse = fxDealDAO.reverse(id);
        return Helper.statusNodes(true).put("message","Fx Deal has been reversed successfully");
    }


    public ObjectNode findWhere(long id ,String column){

        List<FxDeal> fxDealList = fxDealDAO.findWhere(id ,column);

        if(fxDealList.isEmpty()){
            return Helper.statusNodes(false);
        }

        ArrayNode arrayNode = Helper.createArrayNode();

        for(FxDeal fxDeal : fxDealList){

            ObjectNode objectNode = createNode(fxDeal);
            if(objectNode==null){
                continue;
            }
            arrayNode.addPOJO(objectNode);
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;

    }

     public ObjectNode findWhereByOfficer(DayEndAccountBody dayEndAccountBody){

        long officerId = dayEndAccountBody.getOfficerId();
        long fromDate = dayEndAccountBody.getFromDate();
        long toDate = dayEndAccountBody.getToDate();

        List<FxDeal> fxDealList = fxDealDAO.findWhereDates(officerId ,"officer_id" ,fromDate ,toDate);

        if(fxDealList.isEmpty()){
            
            return Helper.statusNodes(false);
        }

        ArrayNode arrayNode = Helper.createArrayNode();

        for(FxDeal fxDeal : fxDealList){

            ObjectNode objectNode = createNode(fxDeal);
            if(objectNode==null){
                continue;
            }
            arrayNode.addPOJO(objectNode);
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;

    }


    public ObjectNode findAll(){

        List<FxDeal> fxDealList = fxDealDAO.findAll();

        if(fxDealList.isEmpty()){
            return Helper.statusNodes(false);
        }

        ArrayNode arrayNode = Helper.createArrayNode();

        for(FxDeal fxDeal : fxDealList){

            ObjectNode objectNode = createNode(fxDeal);
            if(objectNode ==null){
                continue;
            }
            arrayNode.addPOJO(objectNode);
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;

    }

    public ObjectNode calculateForex(TRADE_TYPE tradeType , boolean includeCharges){

        long id = fxDealBody.getCurrencyPairId();
        CurrencyPairDAO currencyPairDAO = new CurrencyPairDAO(fxDealBody.getTenant());
        CurrencyPair currencyPair = currencyPairDAO.find(id);

        if(currencyPair==null){
            return Helper.statusNodes(false);
        }
        
   
        ForexCalculator forexCalculator = new ForexCalculator(currencyPair,tenant);

        try {
            
            this.forexDealSums = forexCalculator.calculateForex(fxDealBody.getQuoteAmount() , fxDealBody.getRate() ,tradeType , includeCharges);

        }
        
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        
        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.put("baseAmount" ,forexDealSums.getBaseAmount());
        objectNode.put("baseCharges",forexDealSums.getBaseCharges());
        objectNode.put("quoteAmount",forexDealSums.getQuoteAmount());
        objectNode.put("quoteCharges" ,forexDealSums.getQuoteCharges());
        objectNode.put("netBaseAmount",forexDealSums.getNetBaseAmount());
        objectNode.put("netQuoteAmount",forexDealSums.getNetQuoteAmount());

        return objectNode ;

    }

    public ObjectNode create(boolean includeCharges){

        // boolean isTradingHours = SettingsHelper.isTradingHours(tenant);

        // if(!isTradingHours){

        //     return Helper.statusNodes(false).put("message",Constants.nonTradingHours);
        // }

        TRADE_TYPE tradeType = TRADE_TYPE.fromInt(fxDealBody.getTradeType());

        calculateForex(tradeType ,includeCharges);
        
        if(forexDealSums==null){

            throw new FxDealSumsException();

        }

        TransactionHelper transactionHelper = new TransactionHelper(tenant , fxDealBody.getCurrencyPairId() ,tradeType);

        if(!transactionHelper.hasSufficientAccount(forexDealSums.getBaseAmount())){

            return Helper.statusNodes(false).put("message","Insufficient funds in corresponding account .Please try again").put("raiseFlag" ,true);

        }

        this.fxDeal = new FxDeal(fxDealBody);

        // switch (fxDeal.getTradeType()){
        //     case SELL:
        //         blotter = new Blotter(forexDealSums.getQuoteAmount() ,forexDealSums.getQuoteCharges() ,forexDealSums.getNetQuoteAmount(), forexDealSums.getBaseAmount() ,forexDealSums.getBaseCharges() ,forexDealSums.getNetBaseAmount());
        //         break;

        //     case BUY:
        //         blotter = new Blotter(forexDealSums.getBaseAmount() ,forexDealSums.getBaseCharges() ,forexDealSums.getNetBaseAmount(), forexDealSums.getQuoteAmount() ,forexDealSums.getQuoteCharges() ,forexDealSums.getNetQuoteAmount());
        //         break;
        // }


        blotter = new Blotter(forexDealSums.getBaseAmount() ,forexDealSums.getBaseCharges() ,forexDealSums.getNetBaseAmount(), forexDealSums.getQuoteAmount() ,forexDealSums.getQuoteCharges() ,forexDealSums.getNetQuoteAmount());

        BlotterDAO blotterDAO = new BlotterDAO(tenant);
        long blotterId = blotterDAO.create(blotter);


        if(blotterId==0){
              
            throw new FailedFxDealException("Failed to set required blotter details");
        }


        blotter.setId(blotterId);

        fxDeal.setBlotter(blotter);
        fxDeal.setIncludeCharges(includeCharges);

        ObjectNode objectNode = fxDealDAO.create(fxDeal);

        boolean status = objectNode.get("status").asBoolean();

        if(!status){

            FxReverseDeal.reverse(blotterId ,new BlotterDAO(tenant));

        }
        return objectNode ;

    }

    public FxDeal getFxDeal(){

        return this.fxDeal ;
    }

    public ObjectNode createNode(FxDeal fxDeal){
        
        ObjectNode objectNode = null;
        
        try {
            objectNode =Helper.createObjectNode();
            objectNode.put("id",fxDeal.getId());

            objectNode.put("tick" ,fxDeal.getTradingRates().getCurrencyPair().getTick());

            if(fxDeal.getTradeType()==TRADE_TYPE.SELL){

                objectNode.put("tick" ,fxDeal.getTradingRates().getCurrencyPair().getInverseTick());

            }

            long clientId = fxDeal.getClient().getId();

            objectNode.put("clientId","Not specified");
            objectNode.put("clientName" , "Non registered client");

            if(clientId != 0){

                objectNode.put("clientId",clientId);
                objectNode.put("clientName" , FxSession.getInstance().getClient(fxDeal ,tenant).getDisplayName());

            }

            objectNode.put("dealType" ,fxDeal.getFxDealType().getCode());
            objectNode.put("officerId",fxDeal.getAppUser().getId());
            objectNode.put("reversed" ,fxDeal.isReversed());

            AppUser appUser = FxSession.getInstance().getAppUser(tenant ,fxDeal.getAppUser().getId());
            if(appUser != null){
                objectNode.put("officer" ,appUser.getDisplayName());

            }

            ROUNDING_RULE r = fxDeal.getTradingRates().getCurrencyPair().getRoundingRule() ;

            double temp = TradingAlgorithm.roundValue(r ,fxDeal.getBlotter().getNetBaseAmount());
            objectNode.put("netBaseAmount" ,temp);

            temp = TradingAlgorithm.roundValue(r , fxDeal.getBlotter().getNetQuoteAmount());
            objectNode.put("netQuoteAmount",temp);


            String date = TimeHelper.timestampToStringWithTimeForSecond(fxDeal.getDate());
            objectNode.put("date",date);
            objectNode.put("purpose",fxDeal.getPurpose().getCode());
            objectNode.put("tradeType",fxDeal.getTradeType().getCode());

            //System.err.println(objectNode);

        }
        
        catch (Exception e) {
            // TODO: handle exception
        }

        return objectNode ;

    }

}
