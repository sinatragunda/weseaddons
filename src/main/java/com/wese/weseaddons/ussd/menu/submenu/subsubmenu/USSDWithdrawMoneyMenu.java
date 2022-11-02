package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.SavingsAccount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDWithdrawMoneyMenu implements USSDMenu {

    private List<MenuAttributes> menuAttributesList ;
    private StringBuilder stringBuilder ;
    private Session session ;


    public USSDWithdrawMoneyMenu(Session session){
        this.session = session ;
    }


    public void init(){

        stringBuilder = new StringBuilder(Constants.connection);
        List<String> list = getMenuAsStringList();

        if(list.isEmpty()){

            stringBuilder.append("You have no active loans");
            return ;
        }

        for(int i =0 ; i < list.size() ;++i){

            int count = i+1;
            String item = list.get(i);
            stringBuilder.append(String.format("%d : %s\n",count ,item));
        }

        stringBuilder.append("10 : Go Back\n");

        String customMenu = String.format("Enter the amount you would like to withdraw\n");
        session.getPostRequestArg().setCustomMenu(customMenu);
        session.getPostRequestArg().setCustomFunctionName("com.wese.weseaddons.ussd.menuaction.WithdrawSavingsAction");
        SessionDetails.getInstance().updateSessionObject(session);


    }


    @Override
    public String getMenuList() {

        init();
        return stringBuilder.toString();

    }

    @Override
    public List<String> getMenuAsStringList() {

        List<SavingsAccount> savingsAccountList = session.getSavingsAccountList();

        if(savingsAccountList.isEmpty()){

            return new ArrayList();
        }

        menuAttributesList = new ArrayList<>();

        for(SavingsAccount savingsAccount : savingsAccountList){

            BigDecimal balance = savingsAccount.getAccountBalance();
            String accountNumber = savingsAccount.getAccountNumber();
            String currencyCode = savingsAccount.getCurrencyCode();
            String productName = savingsAccount.getSavingsProductName();

            String item = String.format("%s\nAccount No : %s\nBalance : %s%.2f",productName ,accountNumber ,currencyCode ,balance);
            menuAttributesList.add(new MenuAttributes.Builder().item(item).treeName("VirtualEnterMoneyTree").build());

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
