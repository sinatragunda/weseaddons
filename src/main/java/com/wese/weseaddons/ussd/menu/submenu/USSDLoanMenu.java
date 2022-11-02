package com.wese.weseaddons.ussd.menu.submenu;

import com.wese.weseaddons.ussd.helper.*;
import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDLoanMenu implements USSDMenu ,AutoInitializable{

    private StringBuilder stringBuilder;
    private List<MenuAttributes> menuAttributesList;
  
    @Override
    public String getMenuList(){

        return stringBuilder.toString();

    }

    @Override
    public void init(){

        stringBuilder = new StringBuilder(Constants.connection);

        List<String> list = getMenuAsStringList();

        for(int i =0 ; i< list.size() ;++i){

            int count = i+1;
            String item = list.get(i);
            stringBuilder.append(String.format("%d : %s\n",count ,item));

        }

        stringBuilder.append("10 : Back\n");


    }

    @Override
    public List<MenuAttributes> getMenuAttributesList() {
        return menuAttributesList;
    }


    @Override
    public List<String> getMenuAsStringList(){

        String functionNameLoanBalance = "com.wese.weseaddons.ussd.menuaction.LoanBalanceAction";
        menuAttributesList = new ArrayList<>();
 
        menuAttributesList.add(
                 new MenuAttributes.Builder().item("Apply Loan").treeName("LoanProductsTree").build());


        menuAttributesList.add(
                new MenuAttributes.Builder().item("Repay Loan").treeName("RepayLoanTree").build());

        menuAttributesList.add(new MenuAttributes.Builder().item("Repayment Schedule").treeName("RepaymentScheduleTree").build());

        menuAttributesList.add(new MenuAttributes.Builder().item("Pending Loans").treeName("PendingLoans").build());


        menuAttributesList.add(
                new MenuAttributes.Builder().item("Loan Balance").child(false).function(functionNameLoanBalance).build());


       List<String> list = menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());
   
       return list ;

    }

    @Override
    public String getExecutableClassName(int option){

        return menuAttributesList.get(option).getFunctionAttribute();

    }


    @Override
    public boolean isOptionExecutable(int option){

        return menuAttributesList.get(option).getChildAttribute();

    }

    public USSDLoanMenu(){
    	
    	
        init();
    }
}
