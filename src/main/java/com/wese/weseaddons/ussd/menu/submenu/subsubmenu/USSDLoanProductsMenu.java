package com.wese.weseaddons.ussd.menu.submenu.subsubmenu;

import com.wese.weseaddons.networkrequests.LoanProductsRequest;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.MenuAttributes;

import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class USSDLoanProductsMenu implements USSDMenu ,AutoInitializable{

    private String tenantIdentifier ;
    private StringBuilder stringBuilder ;
    private List<LoanProducts> productList ;
    public static USSDLoanProductsMenu instance ;
    private Session session ;

    private MenuAttributes menuAttributes ;
    private List<MenuAttributes> menuAttributesList ;


    public USSDLoanProductsMenu(Session session){
        this.session = session ;
        init();
    }


    @Override
    public void init(){

        stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.connection);
        stringBuilder.append("\nSelect loan type\n");

        List<String> list = getMenuAsStringList();

        for(int i=0 ; i < list.size() ;i++){

            int count = i+1 ;
            String product = list.get(i);
            stringBuilder.append(String.format("%d : %s\n" ,count ,product));
         
        }

        instance = this ;

    }

    public List<LoanProducts> getProductsList(){
        return productList ;
    }


    @Override
    public List<String> getMenuAsStringList(){

        LoanProductsRequest loanProductsRequest = new LoanProductsRequest(session.getTenantIdentifier());
        productList = loanProductsRequest.getLoanGeneratorProducts();

        session.setLoanProductsList(productList);
        SessionDetails.getInstance().updateSessionObject(session);

        menuAttributesList = new ArrayList();

        for(LoanProducts loanProducts : productList){

            menuAttributesList.add(new MenuAttributes.Builder().item(loanProducts.getName()).treeName("EnterMoneyTree").build());

        }

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
        return stringBuilder.toString();
    }

    public USSDLoanProductsMenu(){
        init();
    }

	@Override
	public List<MenuAttributes> getMenuAttributesList() {
		return menuAttributesList;
	}

}
