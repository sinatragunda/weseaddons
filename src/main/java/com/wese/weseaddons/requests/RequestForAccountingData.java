package com.wese.weseaddons.requests;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.DateComparator;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.Parameters;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.pojo.Accounting;
import com.wese.weseaddons.pojo.Loan;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RequestForAccountingData implements RequestDataService{

    private Parameters parameters ;
    private List<Loan> loanList ;

    private Predicate<Loan> disbursedToday = (c)->{

        Date dateNow = Helper.dateNow();
        Date streamDate = c.getDisbursedDate();
        DateComparator dateComparator = new DateComparator();
        int result = dateComparator.compare(streamDate ,dateNow);
        return result !=0 ? false : true ;

    };

    public RequestForAccountingData(Parameters parameters){

        this.parameters = parameters ;
        request();

    }

    public void request(){

        //LoansRequest loansRequest = new LoansRequest(parameters);
        //loansRequest.loadLoans();
        //loanList = loansRequest.getLoanList();

    }

    @Override
    public ObjectNode status(){

        if(loanList.isEmpty()){

            return Helper.statusNodes(false);
        }


        List<Loan> loansDisbursedTodayList = loanList.stream().filter(disbursedToday).collect(Collectors.toList());

        long disbursedTodayCb = loansDisbursedTodayList.stream().count() ;
        double amountDisbursedToday = loansDisbursedTodayList.stream().map(Loan::getPrincipalDisbursed).collect(Collectors.summingDouble(i->i));


        Accounting accounting = new Accounting(disbursedTodayCb ,amountDisbursedToday ,0);
        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.put("disbursedTodayCb" ,disbursedTodayCb);
        objectNode.put("amountDisbursedToday" ,accounting.getAmountDisbursedToday());
        return objectNode ;


    }
}
