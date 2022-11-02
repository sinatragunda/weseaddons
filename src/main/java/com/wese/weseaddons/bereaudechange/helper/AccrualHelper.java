package com.wese.weseaddons.bereaudechange.helper;

import com.wese.weseaddons.bereaudechange.dao.AccrualDAO;
import com.wese.weseaddons.bereaudechange.pojo.FxDeal;
import com.wese.weseaddons.bereaudechange.pojo.Revaluation;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AccrualHelper {

    private FxDeal fxDeal ;
    private String tenant ;
    private AccrualDAO accrualDAO ;

    public AccrualHelper(String tenant){
        init(tenant);
    }

    public void init(String tenant){
        this.tenant = tenant ;
        this.accrualDAO = new AccrualDAO(tenant);
    }


    private Consumer<FxDeal> fxDealConsumer = (e)->{

        RevaluationHelper revaluationHelper = new RevaluationHelper(e,tenant);
        Revaluation revaluation = revaluationHelper.profitAndLoss();
        accrualDAO.create(revaluation);

    };

    public void accrue(List<FxDeal> fxDealList){

        fxDealList.stream().forEach(fxDealConsumer);
    }
}
