package com.wese.weseaddons.ussd.menu.submenu;

import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDSavingsMenu implements USSDMenu ,AutoInitializable{


    private StringBuilder stringBuilder =null;
    private MenuAttributes menuAttributes ;
    private List<MenuAttributes> menuAttributesList ;


    @Override
    public String getMenuList(){

        return stringBuilder.toString();

    }

    @Override
    public void init(){

        stringBuilder = new StringBuilder(Constants.connection);
        stringBuilder.append("\n Altus Savings Accounts\n");

        List<String> list = getMenuAsStringList();

        for(int i = 0 ; i < list.size() ;i++){

            int count = i+1 ;
            String menuItem = list.get(i);
            stringBuilder.append(String.format("%d : %s\n",count ,menuItem));

        }

        stringBuilder.append("10 : Back\n");

    }

    @Override
    public boolean isOptionExecutable(int option){

        return menuAttributesList.get(option).getChildAttribute();

    }

    @Override
    public String getExecutableClassName(int option){

        return menuAttributesList.get(option).getFunctionAttribute();

    }

    @Override
    public List<MenuAttributes> getMenuAttributesList() {
        return menuAttributesList;
    }

    @Override
    public List<String> getMenuAsStringList(){

        String balanceFunctionName = "com.wese.weseaddons.ussd.menuaction.SavingsBalanceAction";
        String createSavingsAccountFunction = "com.wese.weseaddons.ussd.menuaction.CreateSavingsAccountAction";
        
        menuAttributesList = new ArrayList();
        menuAttributesList.add(new MenuAttributes.Builder().item("Create Savings Account").child(false).function(createSavingsAccountFunction).build());
        menuAttributesList.add(new MenuAttributes.Builder().item("Withdraw").treeName("SavingsTree_Withdraw").build());
        menuAttributesList.add(new MenuAttributes.Builder().item("Deposit").treeName("SavingsTree_Deposit").build());
        menuAttributesList.add(new MenuAttributes.Builder().item("Balance").function(balanceFunctionName).child(false).build());
        List<String> list = menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());

        return list ;

    }

    public USSDSavingsMenu(){
        init();
    }


}
