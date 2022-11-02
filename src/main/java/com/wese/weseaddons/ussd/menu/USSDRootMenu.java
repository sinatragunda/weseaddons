package com.wese.weseaddons.ussd.menu;

import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.ussd.helper.MenuAttributes;
import com.wese.weseaddons.ussd.interfaces.AutoInitializable;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class USSDRootMenu implements USSDMenu,AutoInitializable {
	
	private StringBuilder stringBuilder = null ;
	private MenuAttributes menuAttributes ;
	private List<MenuAttributes> menuAttributesList ;
	private String con = "CON ";
	
	public USSDRootMenu(Session session) {
		
		init();
		SessionDetails.getInstance().push(session);

	}
	
	@Override
	public void init() {
		
		stringBuilder = new StringBuilder();
		
		stringBuilder.append(con);
		stringBuilder.append("\n Welcome To Loan Generator\n");

		List<String> list = getMenuAsStringList();

		for(int i = 0 ; i < list.size() ;i++){

			int position = i+1 ;
			String menuItem = String.format("%d : %s\n",position ,list.get(i));
			stringBuilder.append(menuItem);

		}

	}
	
	@Override
	public String getMenuList() {
		
		return stringBuilder.toString() ;
		
	}

	@Override
	public String getExecutableClassName(int option){
		return menuAttributesList.get(option).getFunctionAttribute();
	}
	

	@Override
	public boolean isOptionExecutable(int option){
		return menuAttributesList.get(option).getChildAttribute() ;
	}

	@Override
	public List<String> getMenuAsStringList(){

		menuAttributesList = new ArrayList();

		String functionName = "com.wese.weseaddons.ussd.menuaction.LoanBalanceAction";

		menuAttributesList.add(new MenuAttributes.Builder().item("Loans").treeName("LoansTree").build());
		menuAttributesList.add(new MenuAttributes.Builder().item("Savings").treeName("SavingsTree").build());
		menuAttributesList.add(new MenuAttributes.Builder().item("Deposit").treeName("DepositsTree").build());
		menuAttributesList.add(new MenuAttributes.Builder().item("Balance").function(functionName).child(false).build());
		menuAttributesList.add(new MenuAttributes.Builder().item("Airtime").treeName("AirtimeTree").build());

		List<String> list = menuAttributesList.stream().map(MenuAttributes::getItem).collect(Collectors.toList());

		return list ;

	}

	@Override
	public List<MenuAttributes> getMenuAttributesList() {
		return menuAttributesList;
	}

}
