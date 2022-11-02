package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDTalkTimeMenu implements USSDMenu ,AutoInitializable{

    private StringBuilder stringBuilder ;
    private List<MenuAttributes> menuAttributesList ;
    private Session session ;

    public USSDTalkTimeMenu(Session session){
        this.session = session ;
        init();
    }

    @Override
    public void init(){

        stringBuilder = new StringBuilder();
        List<String> list = getMenuAsStringList();


        stringBuilder.append("Chose the recipient\n");

        for(int i =0 ; i < list.size();++i){

            int count = i+1 ;
            stringBuilder.append(String.format("%d : %s\n",count ,list.get(i)));

        }

        stringBuilder.append("10 : Go Back\n");
        String customMenu = String.format("\n");
        session.getPostRequestArg().setCustomMenu(customMenu);
        session.getPostRequestArg().setCustomFunctionName("com.wese.weseaddons.ussd.menuaction.WithdrawMoneyAction");
        SessionDetails.getInstance().updateSessionObject(session);

    }

    @Override
    public boolean isOptionExecutable(int option){

        return menuAttributesList.get(option).getChildAttribute();

    }

    @Override
    public List<String> getMenuAsStringList(){

        String someone = "AirtimeForSomeoneTree";
        String virtualEnterMoneyTree = "VirtualEnterMoneyTree";

        menuAttributesList = new ArrayList<>();
        menuAttributesList.add(new MenuAttributes.Builder().item("This mobile").treeName(virtualEnterMoneyTree).build());
        menuAttributesList.add(new MenuAttributes.Builder().item("Someone else").treeName(someone).build());

        List<String> list= menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());

        return list ;
    }



    @Override
    public String getExecutableClassName(int option){

        return menuAttributesList.get(option).getFunctionAttribute();

    }

    @Override
    public List<MenuAttributes> getMenuAttributesList() {
        return menuAttributesList;
    }


    public String getMenuList(){

        return stringBuilder.toString();

    }
    
    public USSDTalkTimeMenu(){
    	
    	init();
    }
}
