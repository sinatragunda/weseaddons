package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.enumerations.PHONE_NUMBER_FLAG;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.pojo.LoanProducts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDRecipientAirtimeMenu implements USSDMenu ,AutoInitializable{

    private StringBuilder stringBuilder ;
    private List<MenuAttributes> menuAttributesList ;


    @Override
    public void init(){

        Session session = USSDMenuRouter.sessionInstance ;
        session.setPhoneNumberFlag(PHONE_NUMBER_FLAG.ON);
        SessionDetails.getInstance().updateSessionObject(session);

        stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.connection);
        List<String> list = getMenuAsStringList();

        for(int i=0 ; i < list.size() ;i++){

            String item = list.get(i);
            stringBuilder.append(String.format("%s\n" ,item));

        }

    }


    @Override
    public List<String> getMenuAsStringList(){

        String item = "Enter recipient phone number";
        menuAttributesList = new ArrayList();

        menuAttributesList.add(new MenuAttributes.Builder().item(item).child(false).build());

        List<String> list = menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());
        return list ;

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
    public String getMenuList(){
        init();
        return stringBuilder.toString();
    }

    public USSDRecipientAirtimeMenu(){

    }

    @Override
    public List<MenuAttributes> getMenuAttributesList() {
        return menuAttributesList;
    }

}
