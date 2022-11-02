package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.body.FxDealBody;
import com.wese.weseaddons.bereaudechange.enumerations.FX_DEAL_TYPE;
import com.wese.weseaddons.bereaudechange.enumerations.PURPOSE;
import com.wese.weseaddons.bereaudechange.enumerations.TRADE_TYPE;
import com.wese.weseaddons.bereaudechange.impl.Transaction;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.*;
import com.wese.weseaddons.taskmanager.helper.*;

public class FxDeal implements Transaction {

    private long id ;
    private long date ;
    private boolean isReversed ;
    private TradingRates tradingRates ;
    private Client client ;
    private AppUser appUser ;
    private Blotter blotter ;
    private FX_DEAL_TYPE fxDealType ;
    private TRADE_TYPE tradeType;
    private PURPOSE purpose ;
    private boolean accrued ;
    private FxCashier fxCashier ;
    private boolean includeCharges ;

    public FxDeal() {

    }


    public FxDeal(FxDealBody fxDealBody){

        long d = TimeHelper.angularStringToDate(fxDealBody.getDate());
        this.date = d ;
        this.client = fxDealBody.getClient() ;
        this.tradingRates = fxDealBody.getTradingRates();
        this.appUser = fxDealBody.getAppUser();

        setFxDealType(fxDealBody.getFxDealType());
        setPurpose(fxDealBody.getPurpose());
        setTradeType(fxDealBody.getTradeType());

    }


    public FX_DEAL_TYPE getFxDealType() {
        return fxDealType;
    }

    public void setFxDealType(int fxDealType) {

        this.fxDealType = FX_DEAL_TYPE.fromInt(fxDealType);
    }

    public boolean isIncludeCharges() {
        return includeCharges;
    }

    public void setIncludeCharges(boolean includeCharges) {
        this.includeCharges = includeCharges;
    }

    public FxDeal(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public TradingRates getTradingRates() {
        return tradingRates;
    }

    public void setTradingRates(TradingRates tradingRates) {
        this.tradingRates = tradingRates;
    }


    public boolean isReversed() {
        return isReversed;
    }

    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Blotter getBlotter() {
        return blotter;
    }

    public void setBlotter(Blotter blotter) {
        this.blotter = blotter;
    }


    public PURPOSE getPurpose() {
        return purpose;
    }

    public void setPurpose(int p) {

        this.purpose = PURPOSE.fromInt(p);

    }

    public boolean isAccrued() {
        return accrued;
    }

    public void setAccrued(boolean accrued) {
        this.accrued = accrued;
    }

    public TRADE_TYPE getTradeType() {
        return tradeType;
    }

    public void setTradeType(int trade) {
        this.tradeType = TRADE_TYPE.fromInt(trade);
    }

    public FxCashier getFxCashier() {
        return fxCashier;
    }

    public void setFxCashier(FxCashier fxCashier) {
        this.fxCashier = fxCashier;
    }
}
