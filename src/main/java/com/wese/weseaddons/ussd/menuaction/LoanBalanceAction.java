package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanBalanceAction  implements ExecuteMenuAction{


    @Override
    public String execute(Session session){
    	
    	System.out.println("Do we reach here in LoanBalance class");

        List<Loan> list = getMenuAsList(session);

        if(list.isEmpty()){

            return new StringBuilder("You have no active loans").append(Constants.backRequest).toString();

        }

        StringBuilder stringBuilder = new StringBuilder(Constants.connection);
        stringBuilder.append("\nLoan Balances\n");

        for(int i =0 ; i < list.size() ;++i){

            Loan loans = list.get(i);

            if(!loans.isPendingApproval()){

                int count =i+1 ;
                double principal = loans.getPrincipal();
                double outstanding = loans.getPrincipalOutstanding();
                String output= String.format("%d Principal : %.3f.\n Outstanding : %.3f\n" ,count ,principal ,outstanding);
                stringBuilder.append(output);

            }

        }

        stringBuilder.append(Constants.backRequest);

        return stringBuilder.toString();

    }


    public List<Loan> getMenuAsList(Session session) {

        if(session==null){
            System.err.println("Session is so much null son");
        }

        String phoneNumber = session.getPhoneNumber();
        
        List<Loan> loansList = session.getLoansList();

        if(loansList.isEmpty()) {

            return new ArrayList<>();
        }

        return loansList ;

    }

}
