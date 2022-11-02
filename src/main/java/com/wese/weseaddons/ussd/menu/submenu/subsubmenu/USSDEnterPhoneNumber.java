package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDEnterPhoneNumber implements USSDMenu ,AutoInitializable{

    private StringBuilder stringBuilder ;
    private List<MenuAttributes> menuAttributesList ;

    @Override
    public void init(){

        stringBuilder = new StringBuilder();
        List<String> list = getMenuAsStringList();

        stringBuilder.append(list.get(0));
        stringBuilder.append("10 : Back\n");

    }

    @Override
    public boolean isOptionExecutable(int option){

        return menuAttributesList.get(option).getChildAttribute();

    }

    @Override
    public List<String> getMenuAsStringList(){

        String treeName = "VirtualEnterMoneyTree";
        menuAttributesList = new ArrayList<>();
        menuAttributesList.add(new MenuAttributes.Builder().item("Enter phone number").treeName(treeName).build());
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
    
    public USSDEnterPhoneNumber (){
    	
    	init();
    }
}
