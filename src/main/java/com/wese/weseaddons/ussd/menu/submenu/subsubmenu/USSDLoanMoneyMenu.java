package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.ussd.enumerations.INPUT_FLAG;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.IntegerUtils;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.ussd.session.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDLoanMoneyMenu implements USSDMenu{

    private Session session ;
    private StringBuilder stringBuilder ;
    private MenuAttributes menuAttributes ;
    private List<MenuAttributes> menuAttributesList ;

    public USSDLoanMoneyMenu(Session session){
        this.session = session ;
     //   init();
    }
  

    public void init(){

        stringBuilder = new StringBuilder(Constants.connection);
        menuAttributesList = new ArrayList();
        List<String> list = getMenuAsStringList();

        System.out.println("The last menu list is "+list.size());

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

        String functionName ="com.wese.weseaddons.ussd.menuaction.CreateLoanAction";
        
        int index = IntegerUtils.parseString(session.getOption())-1;

        List<LoanProducts> loanProductsList = session.getLoanProductsList() ;

        if(index > loanProductsList.size()){

            ///return some error here
            return null;
        }

        LoanProducts loanProducts =null;

        try{
            loanProducts = loanProductsList.get(index);
        }


        catch(Exception exception){

            /// do something here maybe exist application or raise some error then exit
            return new ArrayList();
        }

        Constants.setInputFlag(session ,true ,index);

        String loanProductName = loanProducts.getName();
        double maxPrincipal = loanProducts.getMaxPrincipal();
        double minPrincipal = loanProducts.getMinPrincipal();

        String menu = String.format("\n%s\nEnter desired money between %.2f and %.2f Kwacha\n",loanProductName ,minPrincipal ,maxPrincipal);
        
        menuAttributesList.add(new MenuAttributes.Builder().item(menu).function(functionName).child(true).build());

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
