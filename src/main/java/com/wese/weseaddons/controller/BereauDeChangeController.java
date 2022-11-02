/// Written by Sinatra Gunda
/// 05/07/2019

package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.action.*;
import com.wese.weseaddons.bereaudechange.body.*;
import com.wese.weseaddons.bereaudechange.enumerations.ACCRUAL_CRITERIA;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.helper.DayEndAccountingHelper;
import com.wese.weseaddons.bereaudechange.pojo.*;
import com.wese.weseaddons.helper.Helper;

import com.wese.weseaddons.helper.TimeHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/bereaudechange")
public class BereauDeChangeController {


    @RequestMapping(value = "/fxaccrue",method = RequestMethod.GET)
    public ObjectNode fxAccrue(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("tillDate")String date){

        FxProfitAndLossAction fxProfitAndLossAction = new FxProfitAndLossAction(tenant);
        return fxProfitAndLossAction.accrue(date);

    }

     @RequestMapping(value = "/fxdealreverse",method = RequestMethod.GET)
    public ObjectNode fxAccrue(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FxDealAction fxDealAction = new FxDealAction(tenant);
        return fxDealAction.reverse(id);

    }

    @RequestMapping(value = "/approvefxdealreverse",method = RequestMethod.GET)
    public ObjectNode approveFxDealReversal(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FxDealAction fxDealAction = new FxDealAction(tenant);
        return fxDealAction.approveFxDealReversal(id);

    }


    @RequestMapping(value = "/fxrevaluation",method = RequestMethod.GET)
    public ObjectNode fxRevaluation(@RequestParam("tenantIdentifier")String tenant){

        FxProfitAndLossAction fxProfitAndLossAction = new FxProfitAndLossAction(tenant);
        return fxProfitAndLossAction.findAll();

    }

    @RequestMapping(value = "/fxcashiers",method = RequestMethod.POST)
    public ObjectNode fxCreateFxCashier(@RequestParam("tenantIdentifier")String tenant ,@RequestBody FxCashierBody fxCashierBody){

        //FxCashier fxCashier = new FxCashier(fxCashierBody);
        FxCashierAction fxCashierAction = new FxCashierAction(tenant);
        return fxCashierAction.create(fxCashierBody);

    }


    @RequestMapping(value = "/fxcashiers",method = RequestMethod.GET)
    public ObjectNode fxCashier(@RequestParam("tenantIdentifier")String tenant){

        FxCashierAction fxCashierAction = new FxCashierAction(tenant);
        return fxCashierAction.findAll();

    }

    @RequestMapping(value = "/fxdealreceipt",method = RequestMethod.GET)
    public String fxDealReceipt(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long fxDealId){

        FxDealReceiptAction fxDealReceipt = new FxDealReceiptAction(fxDealId ,tenant);
        return fxDealReceipt.find().toString();

    }

    @RequestMapping(value = "/fxcashiers",method = RequestMethod.GET ,params = {"id","tenantIdentifier"})
    public ObjectNode fxCashier(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FxCashierAction fxCashierAction = new FxCashierAction(tenant);
        return fxCashierAction.find(id);

    }


    @RequestMapping(value = "/fxmoneyaccountrevaluation",method = RequestMethod.GET)
    public ObjectNode fxRevaluation(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id) {

        FxProfitAndLossAction fxProfitAndLossAction = new FxProfitAndLossAction(tenant);
        return fxProfitAndLossAction.findWhereMoneyAccount(id);

    }

    @RequestMapping(value = "/fxmoneyaccountrevaluationbydate",method = RequestMethod.POST)
    public ObjectNode fxAccountRevaluationByDates(@RequestParam("tenantIdentifier")String tenant ,@RequestBody FxAccountRevaluationBody fxAccountRevaluationBody) {

        FxProfitAndLossAction fxProfitAndLossAction = new FxProfitAndLossAction(tenant);

        long startDay = TimeHelper.angularStringToDate(fxAccountRevaluationBody.getStartDate());
        long endDay = TimeHelper.angularStringToDate(fxAccountRevaluationBody.getEndDate());
        int filterBy = fxAccountRevaluationBody.getFilterBy();
        long id = fxAccountRevaluationBody.getId();

        return fxProfitAndLossAction.findWhereDates(id, startDay, endDay, ACCRUAL_CRITERIA.fromInt(filterBy));

    }

    @RequestMapping(value = "/fxprofitandloss",method = RequestMethod.GET)
    public ObjectNode fxProfitAndLoss(@RequestParam("tenantIdentifier")String tenant){

        FxProfitAndLossAction fxProfitAndLossAction = new FxProfitAndLossAction(tenant);
        return fxProfitAndLossAction.findAll();

    }
    

    @RequestMapping(value = "/fxprofitandloss",method = RequestMethod.GET ,params={"tenantIdentifier" ,"id"})
    public ObjectNode viewFxRevaluation(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FxProfitAndLossAction fxProfitAndLossAction = new FxProfitAndLossAction(tenant);
        return fxProfitAndLossAction.find(id);

    }

    @RequestMapping(value = "/fxrevaluationjob",method = RequestMethod.GET)
    public String fxRevaluationJob(@RequestParam("tenantIdentifier")String tenant){

        return String.format("Hello Kuziwa from %s",tenant);

    }


    @RequestMapping(value = "/fxdayendaccounting",method = RequestMethod.GET)
    public ObjectNode fxDayEndAccounting(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        DayEndAccountingHelper dayEndAccountingHelper = new DayEndAccountingHelper(tenant ,id);
        return dayEndAccountingHelper.summary(null);
    }

    @RequestMapping(value = "/fxdayendaccounting",method = RequestMethod.POST)
    public ObjectNode fxDayEndAccounting(@RequestParam("tenantIdentifier")String tenant , @RequestBody DayEndAccountBody dayEndAccountBody){

        DayEndAccountingHelper dayEndAccountingHelper = new DayEndAccountingHelper(tenant ,0);
        return dayEndAccountingHelper.summary(dayEndAccountBody);
    }

    @RequestMapping(value = "/fxorganisationsettings",method = RequestMethod.POST)
    public ObjectNode fxOrganisationSettings(@RequestParam("tenantIdentifier")String tenant , @RequestBody Settings settings){

        FxSettingsAction fxSettingsAction = new FxSettingsAction(tenant);
        return fxSettingsAction.create(settings);
    }

    @RequestMapping(value ="/fxorganisationsettings",method = RequestMethod.PUT)
    public ObjectNode updateFxOrganisationSettings(@RequestParam("tenantIdentifier")String tenant , @RequestBody Settings settings){

        FxSettingsAction fxSettingsAction = new FxSettingsAction(tenant);
        return fxSettingsAction.update(settings);
    }

    @RequestMapping(value = "/fxorganisationsettings",method = RequestMethod.GET)
    public ObjectNode fxOrganisationSettings(@RequestParam("tenantIdentifier")String tenant){

        FxSettingsAction fxSettingsAction = new FxSettingsAction(tenant);
        return fxSettingsAction.find();
    }

    @RequestMapping(value = "/fxtransactions",method = RequestMethod.GET ,params={"tenantIdentifier" ,"id"})
    public ObjectNode fxDetailedTransaction(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FxDetailedTransactionAction fxDetailedTransactionAction = new FxDetailedTransactionAction(tenant ,id);
        return fxDetailedTransactionAction.getObjectNode();
    }

    @RequestMapping(value = "/fxdetailedtransactionbulk",method = RequestMethod.GET)
    public ObjectNode fxDetailedTransactionBulk(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FxDetailedTransactionActionBulk fxDetailedTransactionActionBulk = new FxDetailedTransactionActionBulk(tenant ,id);
        return fxDetailedTransactionActionBulk.getObjectNode();
    }

    @RequestMapping(value = "/fxdeal",method = RequestMethod.POST)
    public ObjectNode fxDeal(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("includeCharges")boolean includeCharges ,@RequestBody FxDealBody fxDealBody){

        FxDealAction fxDealAction = new FxDealAction(fxDealBody);
        return fxDealAction.create(includeCharges);
    }
    
    @RequestMapping(value = "/fxweightedaverage",method = RequestMethod.GET)
    public ObjectNode myPojo(@RequestParam("tenantIdentifier")String tenant, @RequestParam("id")long fxDealId){

    	FxWeightedAverageAction fxWeightedAverageAction = new FxWeightedAverageAction(tenant);
        return fxWeightedAverageAction.profit(fxDealId);
    }
    
    @RequestMapping(value = "/fxweightedaverage" ,method = RequestMethod.POST)
    public ObjectNode myPojo(@RequestBody FxWeightedAverage fxWeightedAverage ,@RequestParam("tenantIdentifier")String tenantIdentifer){

    	FxWeightedAverageAction fxWeightedAverageAction = new FxWeightedAverageAction(tenantIdentifer);
        return fxWeightedAverageAction.update(fxWeightedAverage);

    }


    @RequestMapping(value="/fxbulkdeal",method = RequestMethod.POST)
    public ObjectNode fxBulkDeal(@RequestParam("tenantIdentifier")String tenant , @RequestBody FxBulkDealBody fxBulkDealBody){

        FxBulkDealAction fxBulkDealAction = new FxBulkDealAction(tenant);
        return fxBulkDealAction.create(fxBulkDealBody);

    }

    @RequestMapping(value = "/fxdealclient",method = RequestMethod.GET)
    public ObjectNode fxDealClient(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FxDealAction fxDealAction = new FxDealAction(tenant);
        return fxDealAction.findWhere(id ,"client_id");

    }

    @RequestMapping(value = "/fxtransactions",method = RequestMethod.GET)
    public ObjectNode fxTransactions(@RequestParam("tenantIdentifier")String tenant){

        FxDealAction fxDealAction = new FxDealAction(tenant);
        return fxDealAction.findAll();

    }

    @RequestMapping(value = "/fxtransactionsbyofficer",method = RequestMethod.POST)
    public ObjectNode fxDealByOfficer(@RequestParam("tenantIdentifier")String tenant ,@RequestBody DayEndAccountBody dayEndAccountBody){

        FxDealAction fxDealAction = new FxDealAction(tenant);
        return fxDealAction.findWhereByOfficer(dayEndAccountBody);

    }

    @RequestMapping(value = "/fxstandardcurrency",method = RequestMethod.GET)
    public ObjectNode standardCurrency(@RequestParam("tenantIdentifier")String tenant){

        StandardCurrencyAction s = new StandardCurrencyAction(tenant);
        return s.findAll();

    }

    @RequestMapping(value = "/fxmoneyaccounttransactions",method = RequestMethod.GET)
    public ObjectNode fxMoneyAccountTransactions(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        MoneyAccountTransactionsAction moneyAccountTransactionsAction = new MoneyAccountTransactionsAction(tenant);
        return moneyAccountTransactionsAction.find(id ,"money_account_id");

    }

    @RequestMapping(value = "/fxfundsaction",method = RequestMethod.GET ,params={"tenantIdentifier" ,"id"})
    public ObjectNode viewFundsAction(@RequestParam("tenantIdentifier")String tenant ,@RequestParam("id")long id){

        FundsActionAction fundsActionAction = new FundsActionAction(tenant);
        return fundsActionAction.find(id);

    }

    @RequestMapping(value ="/financialinstruments" ,method = RequestMethod.GET ,params = {"tenantIdentifier"})
    public ObjectNode financialInstruments(@RequestParam("tenantIdentifier") String tenantIdentifier){

      FinancialInstrumentsAction financialInstrumentsAction = new FinancialInstrumentsAction(tenantIdentifier);
      return financialInstrumentsAction.findAll();
    }

    @RequestMapping(value = "/financialinstruments" ,params = {"tenantIdentifier","id"} ,method = RequestMethod.GET)
    public ObjectNode viewFinancialInstruments(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

      FinancialInstrumentsAction financialInstrumentsAction = new FinancialInstrumentsAction(tenantIdentifier);
      return financialInstrumentsAction.find(id);
    }

    @RequestMapping(value = "/financialinstruments" ,method = RequestMethod.PUT)
    public ObjectNode editFinancialInstruments(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestBody FinancialInstrument financialInstrument){

      FinancialInstrumentsAction financialInstrumentsAction = new FinancialInstrumentsAction(tenantIdentifier);
      return financialInstrumentsAction.edit(financialInstrument);
    }

    @RequestMapping(value ="/financialinstruments" ,method = RequestMethod.POST)
    public ObjectNode financialInstruments(@RequestBody FinancialInstrumentBody financialInstrumentBody){

        FinancialInstrumentsAction financialInstrumentsAction = new FinancialInstrumentsAction(financialInstrumentBody.getTenant());
        return financialInstrumentsAction.create(financialInstrumentBody);

    }

    @RequestMapping(value = "/tradingcurrency" ,method = RequestMethod.POST)
    public ObjectNode createTradingCurrency(@RequestBody TradingCurrency tradingCurrency ,@RequestParam("tenantIdentifier")String tenantIdentifer){

        TradingCurrencyAction tradingCurrencyAction = new TradingCurrencyAction(tenantIdentifer);
        return tradingCurrencyAction.create(tradingCurrency);

    }

    @RequestMapping(value = "/tradingcurrency" ,method = RequestMethod.GET ,params={"tenantIdentifier"})
    public ObjectNode tradingCurrency(@RequestParam("tenantIdentifier") String tenantIdentifier){

        TradingCurrencyAction tradingCurrencyAction = new TradingCurrencyAction(tenantIdentifier);
        return tradingCurrencyAction.findAll();

    }


    @RequestMapping(value = "/tradingcurrency" ,method = RequestMethod.PUT)
    public ObjectNode editFxtradingCurrency(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestBody TradingCurrency tradingCurrency){

        TradingCurrencyAction tradingCurrencyAction = new TradingCurrencyAction(tenantIdentifier);
        return tradingCurrencyAction.edit(tradingCurrency);

    }

    @RequestMapping(value = "/tradingcurrency" ,method = RequestMethod.GET ,params = {"tenantIdentifier","id"})
    public ObjectNode viewTradingCurrency(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

        TradingCurrencyAction tradingCurrencyAction = new TradingCurrencyAction(tenantIdentifier);
        return tradingCurrencyAction.find(id);

    }


    @RequestMapping(value = "/fxtradingtrends" ,method = RequestMethod.GET)
    public ObjectNode fxTradingTrends(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id") long id){

        TradingRatesAction tradingRatesAction = new TradingRatesAction(tenantIdentifier);
        return tradingRatesAction.findTrend(id);

    }

    @RequestMapping(value = "/fxvolumestrade" ,method = RequestMethod.GET)
    public ObjectNode fxVolumesTrade(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id") long id){

        TradingRatesAction tradingRatesAction = new TradingRatesAction(tenantIdentifier);
        return tradingRatesAction.volumesTrade(id);

    }

    @RequestMapping(value = "/fxfundsaction" ,method = RequestMethod.POST)
    public ObjectNode fundsAction(@RequestBody FundsAction fundsAction , @RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("includeCharges") boolean includeCharges){

        FundsActionAction fundsActionAction = new FundsActionAction(tenantIdentifier ,includeCharges);
        return fundsActionAction.create(fundsAction);

    }

    @RequestMapping(value = "/fxtradingcurrency" ,params = {"tenantIdentifier","id"} ,method = RequestMethod.GET)
    public ObjectNode tradingCurrency(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

        TradingCurrencyAction tradingCurrencyAction = new TradingCurrencyAction(tenantIdentifier);
        return tradingCurrencyAction.find(id);

    }

    @RequestMapping(value = "/fxtradingcurrency" ,method = RequestMethod.PUT)
    public ObjectNode editFxTradingCurrency(@RequestBody TradingCurrency tradingCurrency, @RequestParam("tenantIdentifier") String tenantIdentifier){

        TradingCurrencyAction tradingCurrencyAction = new TradingCurrencyAction(tenantIdentifier);
        return tradingCurrencyAction.edit(tradingCurrency);

    }

    @RequestMapping(value = "/fxcharges" ,method = RequestMethod.GET)
    public ObjectNode charges(@RequestParam("tenantIdentifier") String tenantIdentifier){

        ChargesAction chargesAction = new ChargesAction(tenantIdentifier);
        return chargesAction.findAll();


    }

    @RequestMapping(value = "/fxcharges" ,method = RequestMethod.POST)
    public ObjectNode charges(@RequestBody Charges charges ,@RequestParam("tenantIdentifier") String tenant){

        ChargesAction chargesAction = new ChargesAction(tenant);
        return chargesAction.createCharge(charges);


    }

    @RequestMapping(value = "/fxpaymentmethods" ,params = {"tenantIdentifier"} ,method = RequestMethod.GET)
    public ObjectNode paymentMethod(@RequestParam("tenantIdentifier") String tenantIdentifier){

    	PaymentMethodsAction paymentMethodsAction = new PaymentMethodsAction(tenantIdentifier);
        return paymentMethodsAction.findAll();


    }
	

    @RequestMapping(value = "/fxpaymentmethods" ,method = RequestMethod.POST)
    public ObjectNode paymentMethod(@RequestBody PaymentMethods paymentMethod ,@RequestParam("tenantIdentifier") String tenant){

    	PaymentMethodsAction paymentMethodsAction = new PaymentMethodsAction(tenant);
        return paymentMethodsAction.createPaymentMethod(paymentMethod);


    }


    @RequestMapping(value = "/fxpaymentmethods" ,params = {"tenantIdentifier","id"} ,method = RequestMethod.GET)
    public ObjectNode findPaymentMethod(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

    	PaymentMethodsAction paymentMethodsAction = new PaymentMethodsAction(tenantIdentifier);
        return paymentMethodsAction.find(id);

    }

    @RequestMapping(value ="/fxcharges" ,params = {"tenantIdentifier","id"},method = RequestMethod.GET)
    public ObjectNode charges(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

        ChargesAction chargesAction = new ChargesAction(tenantIdentifier);
        return chargesAction.find(id);

    }

    @RequestMapping(value = "/fxcharges" ,method = RequestMethod.PUT)
    public ObjectNode editCharges(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestBody Charges charges){

        ChargesAction chargesAction = new ChargesAction(tenantIdentifier);
        return chargesAction.edit(charges);

    }

    @RequestMapping(value = "/deactivatefxcharges" ,method = RequestMethod.POST)
    public ObjectNode editCharges(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestBody MoneyAccountChargesRM m){

        long moneyAccountId = m.getMoneyAccount().getId();
        long chargeId = m.getCharges().getId();
        boolean active = m.isActive();

        MoneyAccountAction moneyAccountAction = new MoneyAccountAction(tenantIdentifier);
        return moneyAccountAction.deactivateCharge(moneyAccountId ,chargeId ,active);

    }

    @RequestMapping(value ="/fxmoneyaccount" ,method = RequestMethod.POST)
    public ObjectNode createMoneyAccount(@RequestParam("tenantIdentifier") String tenantIdentifier , @RequestBody MoneyAccount moneyAccount){

        MoneyAccountAction moneyAccountAction = new MoneyAccountAction(tenantIdentifier);
        return moneyAccountAction.create(moneyAccount);

    }

    @RequestMapping(value = "/fxmoneyaccount" ,method = RequestMethod.GET)
    public ObjectNode moneyAccountFindAll(@RequestParam("tenantIdentifier") String tenantIdentifier){

        MoneyAccountAction moneyAccountAction = new MoneyAccountAction(tenantIdentifier);
        return moneyAccountAction.findAll();

    }

    @RequestMapping(value = "/fxmoneyaccount" ,method = RequestMethod.GET ,params = {"id","tenantIdentifier"})
    public ObjectNode moneyAccountFindOne(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

        MoneyAccountAction moneyAccountAction = new MoneyAccountAction(tenantIdentifier);
        return moneyAccountAction.find(id);

    }

    @RequestMapping(value = "/fxmoneyaccount" ,method = RequestMethod.PUT)
    public ObjectNode editMoneyAccount(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestBody MoneyAccount moneyAccount){

        MoneyAccountAction moneyAccountAction = new MoneyAccountAction(tenantIdentifier);
        return moneyAccountAction.update(moneyAccount);

    }


    @RequestMapping(value = "/deactivatefxmoneyaccount" ,method = RequestMethod.GET)
    public ObjectNode deleteMoneyAccount(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id ,@RequestParam("value")boolean value){

        MoneyAccountAction moneyAccountAction = new MoneyAccountAction(tenantIdentifier);
        return moneyAccountAction.deactivate(id,value);

    }

    @RequestMapping(value ="/fxdifferentialmethod" ,method = RequestMethod.GET)
    public ObjectNode fxDifferentialMethod(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long fxDealId){

        FxProfitAndLossAction fxProfitAndLossAction = new FxProfitAndLossAction(tenantIdentifier);
        return fxProfitAndLossAction.fxDifferentialRatesMethod(fxDealId);

    }

    @RequestMapping(value = "/fxcurrencypair" ,method = RequestMethod.GET)
    public ObjectNode currencyPair(@RequestParam("tenantIdentifier") String tenantIdentifier){

        CurrencyPairAction currencyPairAction = new CurrencyPairAction(tenantIdentifier);
        return currencyPairAction.findAll();

    }

    @RequestMapping(value = "/fxcurrencypair", params = {"tenantIdentifier","id"}, method = RequestMethod.GET)
    public ObjectNode CurrencyPair(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id ){

        CurrencyPairAction currencyPairAction = new CurrencyPairAction(tenantIdentifier);
        return currencyPairAction.find(id);

    }

    @RequestMapping(value = "/fxcurrencypair" ,method = RequestMethod.PUT)
    public ObjectNode editFxCurrencyPair(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestBody CurrencyPair currencyPair){

        CurrencyPairAction currencyPairAction = new CurrencyPairAction(tenantIdentifier);
        return currencyPairAction.update(currencyPair);

    }

    @RequestMapping(value = "/deletefxcurrencypair" ,method = RequestMethod.GET)
    public ObjectNode deleteCurrencyPair(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

        CurrencyPairAction currencyPairAction = new CurrencyPairAction(tenantIdentifier);
        return currencyPairAction.delete(id);

    }

    @RequestMapping(value = "/fxcurrencypair" ,method = RequestMethod.POST)
    public ObjectNode currencyPair(@RequestParam("tenantIdentifier") String tenantIdentifier , @RequestBody CurrencyPair currencyPair){

        CurrencyPairAction currencyPairAction = new CurrencyPairAction(tenantIdentifier);
        return currencyPairAction.create(currencyPair);
    }


    @RequestMapping(value ="/fxlivetradingratesextended" ,method = RequestMethod.GET)
    public ObjectNode liveTradingRatesEx(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("baseCurrencyId")long baseCurrencyId ,@RequestParam("quoteCurrencyId")long quoteCurrencyId){

        TradingRatesAction tradingRatesAction = new TradingRatesAction(tenantIdentifier);
        return tradingRatesAction.findRatesWherePairMatch(baseCurrencyId ,quoteCurrencyId);

    }

    @RequestMapping(value ="/fxlivetradingrates" ,method = RequestMethod.GET)
    public ObjectNode liveTradingRates(@RequestParam("tenantIdentifier") String tenantIdentifier){

        LiveRatesAction liveRatesAction = new LiveRatesAction(tenantIdentifier);
        return liveRatesAction.findAll();
    }

    // @RequestMapping(value ="/fxclearworkingset" ,method = RequestMethod.POST)
    // public ObjectNode fxClearWorkingSet(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("username") String username,@RequestParam("password")String password){

    //     FxSettingsAction settingsAction = new FxSettingsAction(tenantIdentifier);
    //     return settingsAction.clearWorkingSet(username ,password);

    // }

     @RequestMapping(value ="/fxclearworkingset" ,method = RequestMethod.POST)
    public ObjectNode fxClearWorkingSet(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("authoriser")long authoriserId){

        FxSettingsAction fxSettingsAction = new FxSettingsAction(tenantIdentifier);
        //return fxSettingsAction.clearWorkingSet();
        return Helper.statusNodes(true);

    }

    @RequestMapping(value ="/fxaudittrail" ,method = RequestMethod.GET)
    public ObjectNode fxClearWorkingSet(@RequestParam("tenantIdentifier") String tenantIdentifier){

        FxHistoryAction fxHistoryAction = new FxHistoryAction(tenantIdentifier);
        return fxHistoryAction.findAll();

    }


    @RequestMapping(value ="/fxratesanalyzer" ,method = RequestMethod.GET)
    public ObjectNode tradingRatesAnalyzer(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id){

        CurrencyPairAction currencyPairAction = new CurrencyPairAction(tenantIdentifier);
        return currencyPairAction.rateHistory(id);

    }

    @RequestMapping(value ="/fxupdatetradingrates" ,method = RequestMethod.POST)
    public ObjectNode liveTradingRates(@RequestParam("tenantIdentifier") String tenantIdentifier ,@RequestParam("id")long id, @RequestBody TradingRates tradingRates){

        TradingRatesAction tradingRatesAction = new TradingRatesAction(tenantIdentifier);
        return tradingRatesAction.update(tradingRates,id);

    }

    @RequestMapping(value = "/fxcalculator" ,method = RequestMethod.POST)
    public ObjectNode currencyPair(@RequestBody FxDealBody fxDealBody ,@RequestParam("includeCharges") boolean includeCharges){

        FxDealAction fxDealAction = new FxDealAction(fxDealBody);
        TRADE_TYPE tradeType = TRADE_TYPE.fromInt(fxDealBody.getTradeType());

        return fxDealAction.calculateForex(tradeType , includeCharges);
    }

}

