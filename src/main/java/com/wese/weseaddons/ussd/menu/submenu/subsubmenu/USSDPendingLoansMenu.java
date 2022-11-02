package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.pojo.Loan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDPendingLoansMenu implements USSDMenu {

    private List<MenuAttributes> menuAttributesList ;
    private StringBuilder stringBuilder ;
    private Session session ;

    public USSDPendingLoansMenu(Session session){
        this.session = session ;
        init();
    }

    public void init(){

        stringBuilder = new StringBuilder(Constants.connection);
        List<String> list = getMenuAsStringList();

        if(list.isEmpty()){

            stringBuilder.append("You have no loans pending approval");
            return ;
        }

        for(int i =0 ; i < list.size() ;++i){

            int count = i+1;
            String item = list.get(i);
            stringBuilder.append(String.format("%d : %s\n",count ,item));
        }

        stringBuilder.append(Constants.backRequest);

    }


    @Override
    public String getMenuList() {

        init();
        return stringBuilder.toString();

    }

    @Override
    public List<String> getMenuAsStringList() {

        String phoneNumber = session.getPhoneNumber();
        List<Loan> loansList = session.getLoansList();

    
        if(loansList.isEmpty()) {

            return new ArrayList<>();
        }


        menuAttributesList = new ArrayList<>();

        for(Loan loans : loansList){

            if(!loans.isPendingApproval()){

                long loanId = loans.getId();
                double balance = loans.getPrincipal();

                String item = String.format("Loan Id : %d\nPending Amount :%.2f\n",loanId ,balance);
                menuAttributesList.add(new MenuAttributes.Builder().item(item).treeName("CancelLoanTree").build());

            }


        }

        List<String> list = menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());
        return list ;

    }

    @Override
    public boolean isOptionExecutable(int option) {

        return menuAttributesList.get(option).getChildAttribute();

    }

    @Override
    public String getExecutableClassName(int option) {

        return menuAttributesList.get(option).getFunctionAttribute();

    }

    @Override
    public List<MenuAttributes> getMenuAttributesList() {
        return menuAttributesList;
    }
}
