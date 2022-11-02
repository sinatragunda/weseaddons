package com.wese.weseaddons.ussd.menu.submenu;

import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDAirtimeBundlesMenu implements USSDMenu ,AutoInitializable{


    private StringBuilder stringBuilder ;
    private List<MenuAttributes> menuAttributesList ;

    @Override
    public void init(){

        stringBuilder = new StringBuilder();
        List<String> list = getMenuAsStringList();

        for(int i =0 ; i < list.size();++i){

            int count = i+1 ;
            stringBuilder.append(String.format("%d : %s\n",count ,list.get(i)));

        }

        stringBuilder.append("10 : Back\n");

    }

    @Override
    public boolean isOptionExecutable(int option){

        return menuAttributesList.get(option).getChildAttribute();

    }

    @Override
    public List<String> getMenuAsStringList(){

        String talkTimeTree = "TalkTimeTree";
        String bundlesTree = "BundlesTree";

        menuAttributesList = new ArrayList<>();
        menuAttributesList.add(new MenuAttributes.Builder().item("Talk time").treeName(talkTimeTree).build());
        menuAttributesList.add(new MenuAttributes.Builder().item("Bundles").treeName(bundlesTree).build());

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
    
    public USSDAirtimeBundlesMenu() {

        System.out.println("Does class reach here ");
    	
    	init();
    }
}
