package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.enumerations.INPUT_FLAG;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.IntegerUtils;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class USSDVirtualEnterMoneyMenu implements USSDMenu {


    private Session session ;
    private StringBuilder stringBuilder ;
    private List<MenuAttributes> menuAttributesList ;

    public USSDVirtualEnterMoneyMenu(Session session){
        this.session = session ;
    }

    public void init(){

        stringBuilder = new StringBuilder(Constants.connection);
        menuAttributesList = new ArrayList();
        List<String> list = getMenuAsStringList();

        for(int i =0 ; i < list.size() ;++i){

            String item = list.get(i);
            stringBuilder.append(String.format("%s\n" ,item));

        }

        stringBuilder.append("10 : Go Back");

    }


    public boolean isOptionExecutable(int option){

        return menuAttributesList.get(option).getChildAttribute();

    }

    @Override
    public String getExecutableClassName(int option){

        return menuAttributesList.get(option).getFunctionAttribute();

    }

    @Override
    public List<String> getMenuAsStringList() {

        String functionName = session.getPostRequestArg().getCustomFunctionName();
        String treeName = "VirtualEnterMoneyTree";

        int index = IntegerUtils.parseString(session.getOption());

        Constants.setInputFlag(session ,true ,index);
        String menu = session.getPostRequestArg().getCustomMenu();
        menuAttributesList.add(new MenuAttributes.Builder().item(menu).function(functionName).treeName(treeName).child(true).build());

        session.setInputFlag(INPUT_FLAG.ON);
        SessionDetails.getInstance().updateSessionObject(session);
        List<String> list = menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());
        return list ;

    }

    @Override
    public List<MenuAttributes> getMenuAttributesList(){
        return menuAttributesList ;
    }


    @Override
    public String getMenuList(){

        init();
        return stringBuilder.toString();
    }

}
