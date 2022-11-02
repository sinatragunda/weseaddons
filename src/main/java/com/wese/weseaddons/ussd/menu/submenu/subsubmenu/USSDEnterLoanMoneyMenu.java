package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;


import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.ussd.tree.SessionInit;
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

public class USSDEnterLoanMoneyMenu implements USSDMenu {

    private Session session;
    private StringBuilder stringBuilder ;
    private List<MenuAttributes> menuAttributesList ;

    public USSDEnterLoanMoneyMenu(Session session){
        this.session = session ;
    }


    public void init(){

        stringBuilder = new StringBuilder(Constants.connection);
        List<String> list = getMenuAsStringList();

        for(int i =0 ; i< list.size();++i) {

            String item = list.get(i);
            stringBuilder.append(item);
        }


        String functionName = "com.wese.weseaddons.menuaction.submenuaction.TakeLoanAction";

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
        session.setInputFlag(INPUT_FLAG.ON);
        SessionDetails.getInstance().updateSessionObject(session);
        return stringBuilder.toString();

    }

    @Override
    public List<String> getMenuAsStringList(){

        menuAttributesList = new ArrayList<>(0);
        List<String> list = new ArrayList<>(0);

        int index = IntegerUtils.parseString(session.getOption());

        List<LoanProducts> loanProductList = session.getLoanProductsList();

        if(index > loanProductList.size()){

            ///return some error here
            return list;
        }

        LoanProducts loanProduct = loanProductList.get(index);

        double maxPrincipal = loanProduct.getMaxPrincipal();
        double minPrincipal = loanProduct.getMinPrincipal();

        String functionName = "com.wese.weseaddons.menuaction.submenuaction.TakeLoanAction";

        StringBuilder stringBuilder = new StringBuilder(Constants.connection+"\n");

        String item = String.format("\n Enter desired money between %.2f and %.2f Kwacha\n",minPrincipal ,maxPrincipal);
        stringBuilder.append(item);

        menuAttributesList.add(new MenuAttributes.Builder().child(false).function(functionName).item(stringBuilder.toString()).build());

        list = menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());

        return list ;


    }

    @Override
    public List<MenuAttributes> getMenuAttributesList() {

        return menuAttributesList;
    }

}
