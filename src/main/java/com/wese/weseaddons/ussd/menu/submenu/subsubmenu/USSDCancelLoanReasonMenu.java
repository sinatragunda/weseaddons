package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.enumerations.PREREASON_FLAG;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.session.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDCancelLoanReasonMenu implements USSDMenu{

    private StringBuilder stringBuilder ;
    private List<MenuAttributes> menuAttributesList ;
    private Session session ;

    public void init(){

        session = USSDMenuRouter.sessionInstance;
        stringBuilder = new StringBuilder(Constants.connection);
        menuAttributesList = new ArrayList();
        List<String> list = getMenuAsStringList();

        stringBuilder.append("Choose reason for canceling loan application\n");

        for(int i =0 ; i < list.size() ;++i){

            int count = i+1 ;
            String item = list.get(i);

            String formattedData = String.format("%d : %s\n",count ,item);
            stringBuilder.append(formattedData);

        }


        stringBuilder.append("10 : Go Back");
        session.setPreReasonFlag(PREREASON_FLAG.ON);
        SessionDetails.getInstance().updateSessionObject(session);

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

        String functionName ="com.wese.weseaddons.ussd.menuaction.CancelLoanApplicationAction";

        List<String> cancelReasons = Constants.getCancelLoanReasons();

        for(String reason : cancelReasons){


            System.out.println("Cancel reasons explain");

            menuAttributesList.add(new MenuAttributes.Builder().item(reason).function(functionName).child(false).build());

        }

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
