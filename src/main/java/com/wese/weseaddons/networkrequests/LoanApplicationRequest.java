package com.wese.weseaddons.networkrequests;

import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.pojo.Loan;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LoanApplicationRequest {

    private List<Loan> loansList ;
    private Session session ; 


    private Predicate<Loan> inArreasPredicate = (e)->{

        return e.isInArrears();

    };

    public LoanApplicationRequest(Session session){
        this.session = session ;
    }

    public void init(){

        this.loansList = session.getLoansList();

    }

    public boolean isClientEligable(){
    	
      List<Loan> stream = loansList.stream()
                               .filter(inArreasPredicate)
                               .collect(Collectors.toList());
        
        if(stream.size()!= 0){
            return false;
        }

        return true ;

    }
    
    public LoanApplicationRequest() {
    	
    	init();
    }

}
