package com.wese.weseaddons.ussd.menu;

import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.ussd.enumerations.*;
import com.wese.weseaddons.ussd.tree.SessionInit;
import com.wese.weseaddons.ussd.menu.submenu.subsubmenu.*;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;
import com.wese.weseaddons.ussd.exceptions.ParseDoubleException;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.helper.IntegerUtils;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.menu.submenu.USSDAirtimeBundlesMenu;
import com.wese.weseaddons.ussd.menu.submenu.USSDDepositMenu;
import com.wese.weseaddons.ussd.menu.submenu.USSDLoanMenu;
import com.wese.weseaddons.ussd.menu.submenu.USSDSavingsMenu;
import com.wese.weseaddons.ussd.tree.SessionInit;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.pojo.Client;

public class USSDMenuRouter {

    private Session session ;
    private String sessionId ;
    private String phoneNumber ;
    private String serviceCode ;
    private String tenantIdentifier;
    private String option ;
    private int intOption ;
    public static Session sessionInstance ;
    private String response;

    private TreeDataStructure<USSDMenu> loanTreeDataStructure ;
    private TreeDataStructure<USSDMenu> savingsTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdAirtimeBundlesTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdTakeLoanTreeDataStructure;
    private TreeDataStructure<USSDMenu> ussdEnterLoanMoneyTreeDataStructure ;

    private TreeDataStructure<USSDMenu> ussdRepayLoanTreeDataStructure ;

    private TreeDataStructure<USSDMenu> ussdVirtualEnterMoneySavingsDepositTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdVirtualEnterMoneyTreeDataStructure1;
    private TreeDataStructure<USSDMenu> ussdVirtualEnterMoneyTreeDataStructure2;  

    ///savings
    private TreeDataStructure<USSDMenu> ussdWithdrawMoneyTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdSavingsDepositTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdDepositTreeDataStructure ;

    /// airtime 
    private TreeDataStructure<USSDMenu> ussdTalkTimeTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdBundleTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdRecipientPhoneNumberTreeDataStructure ;

    private ClientRequest clientRequest ;

    public USSDMenuRouter(String sessionId ,String phoneNumber ,String serviceCode ,String option){
        
        this.sessionId = sessionId ;
        this.phoneNumber = phoneNumber ;
        this.serviceCode = serviceCode ;
        this.tenantIdentifier = serviceCode ;
        this.option = option ;

    }

    public String routeMenu(){

        session = null ;
        response = null ;

        USSDMenu ussdMenu = null ;
        Client client = null ;
        TreeDataStructure<USSDMenu> parentDataStructure = null ;

        
        if(option==""||option==null || option.equalsIgnoreCase("0")){
        	
        	session = new Session(sessionId ,phoneNumber , serviceCode);

            ClientRequest clientRequest = new ClientRequest(phoneNumber ,serviceCode);
            client = clientRequest.getClientWhere("mobile_no" ,phoneNumber);

            if(client==null){
                return Constants.unregisteredUser ;
            }


            USSDRootMenu ussdRootMenu = new USSDRootMenu(session);
        	parentDataStructure = new TreeDataStructure<USSDMenu>(ussdRootMenu);

            session.setClient(client);

            Runnable savingsAccountsAsync = SessionInit.getInstance().loadSavingsAccountsAsync(session);
            OffshoreThread.run(savingsAccountsAsync);

            Runnable loanProductsRequestAsync = SessionInit.getInstance().loanProductsRequestAsync(session);
            OffshoreThread.run(loanProductsRequestAsync);

            Runnable loadLoanAccountsAsync = SessionInit.getInstance().loadLoanAccountsAsync(session);
            OffshoreThread.run(loadLoanAccountsAsync);

            constructMenus(parentDataStructure);
        	session.setTreeDataStructure(parentDataStructure);
        	SessionDetails.getInstance().updateSessionObject(session);

          return parentDataStructure.getMenu();

        }
        
        session = SessionDetails.getInstance().getSessionObject(sessionId);

        if(session == null){
            return Constants.transactionTerminated ;
        }

        if(session.getClient()==null){
            return Constants.unregisteredUser ;
        }

        session.setOption(option);

        try{
            
            intOption = IntegerUtils.parseString(option);
        }

        catch (ParseDoubleException p){
        	
        	System.out.println(p.getMessage());

        }

        
        if(Constants.backOption==intOption) {

            return backOption();
        }

        return subMenus();
           
    }


    public String subMenus(){

      TreeDataStructure<USSDMenu> currentNodeTreeDataStructure = session.getTreeDataStructure();
      TreeDataStructure<USSDMenu> activeNodeTreeDataStructure = null ;

      USSDMenu ussdMenu = currentNodeTreeDataStructure.getUssdMenu();

      if(intOption==500){

          TreeDataStructure<USSDMenu> newSavingsTreeDataStructure = new TreeDataStructure<>();
          newSavingsTreeDataStructure.setClassName("com.wese.weseaddons.ussd.menuaction.CreateSavingsAccountAction");
          return newSavingsTreeDataStructure.execute(session);
      }


      String response = executeWithFlag(session ,currentNodeTreeDataStructure ,ussdMenu);

      if(response !=null){

          return response ;

      }

      int intOption = 0 ;

      try{

      	intOption = IntegerUtils.parseString(option)-1 ;

      }

      catch(Exception e){

        return Constants.invalidInput ;

      }


	  int structureSize = currentNodeTreeDataStructure.getUssdMenu().getMenuAsStringList().size();

	  if(intOption > structureSize){
	  	return Constants.invalidInput ;
	  }


        //these are rigging function calls

        if(session.getPreReasonFlag()== PREREASON_FLAG.ON){
	      System.err.println("Pre reason flag been set here");
            session.getPostRequestArg().setPreReasonId(intOption);
            session.setPreReasonFlag(PREREASON_FLAG.OFF);
        }

        //rigging function call
        if(session.getInteruptCall()!= INTERUPT_CALL.NULL){
            session.setInteruptId(intOption);
            session.setInteruptCall(INTERUPT_CALL.NULL);
        }

	  if(!ussdMenu.isOptionExecutable(intOption)){

	      System.out.println("Executable option");
	      return execute(currentNodeTreeDataStructure ,ussdMenu ,intOption);

	  }

	   String treeName = null;

	   try {
		   
		   treeName = ussdMenu.getMenuAttributesList().get(intOption).getTreeNameAttribute();
		   activeNodeTreeDataStructure = currentNodeTreeDataStructure.getChildByTreeName(treeName);

       }
	   
	   catch(NullPointerException n){
		   
		   System.out.println("Null pointer exception ,failed to get value for tree "+treeName);
		   
		   return null ;	   
	   }

	    session.setTreeDataStructure(activeNodeTreeDataStructure);

        SessionDetails.getInstance().updateSessionObject(session);

        return activeNodeTreeDataStructure.getMenu();

    }

    public String executeWithInputFlag(TreeDataStructure<USSDMenu> activeNodeTreeDataStructure,USSDMenu currentUSSDMenu){

      	double amount = 0 ;

      	System.err.println("Executing with input flag "+amount);

      	try{

      		amount = IntegerUtils.parseDouble(option);

      		System.err.println("Amount from inputt fals is "+amount);
      	
        }
      	catch(Exception e){

      	    e.printStackTrace();
      		return Constants.invalidInput ;
      		
      	}

      	/// could have been 0 for int option
        session.getMonetaryInput().setOption(0);

      	System.err.println("Executing Node is "+activeNodeTreeDataStructure.getTreeName());

        return execute(activeNodeTreeDataStructure ,currentUSSDMenu);
      	
    }


    public String executeWithExecuteFlag(TreeDataStructure<USSDMenu> activeNodeTreeDataStructure,USSDMenu currentUSSDMenu){


        int intOption = IntegerUtils.parseString(option);
        session.getMonetaryInput().setOption(intOption);
        return execute(activeNodeTreeDataStructure ,currentUSSDMenu);


    }

    public String executeWithPhoneNumberFlag(TreeDataStructure activeNodeTree ,USSDMenu ussdMenu){

        String phoneNumber = option ;
        session.getPostRequestArg().setAirtimePhoneNumber(phoneNumber);
        return execute(activeNodeTree ,ussdMenu);

    }

    public String execute(TreeDataStructure activeNodeTreeDataStructure ,USSDMenu currentUSSDMenu){

        System.err.println("Well lets do something");

        Constants.setFlagsOff(session);
        String className = currentUSSDMenu.getExecutableClassName(0);

        activeNodeTreeDataStructure.setClassName(className);
        
        System.err.print(activeNodeTreeDataStructure.getClassName());
        
        String results = activeNodeTreeDataStructure.execute(session);

        return results ;

    }


    public String execute(TreeDataStructure activeNodeTreeDataStructure ,USSDMenu currentUSSDMenu ,int intOption){


        Constants.setFlagsOff(session);

        String className = currentUSSDMenu.getExecutableClassName(intOption);
        activeNodeTreeDataStructure.setClassName(className);
        String results = activeNodeTreeDataStructure.execute(session);
        return results ;

    }


    public void constructMenus(TreeDataStructure<USSDMenu> parentTreeDataStructure) {
    	

        USSDVirtualEnterMoneyMenu ussdVirtualEnterMoneyMenu = new USSDVirtualEnterMoneyMenu(session);
    	
        USSDLoanMenu ussdLoanMenu = new USSDLoanMenu();
        loanTreeDataStructure = new TreeDataStructure<USSDMenu>(parentTreeDataStructure ,ussdLoanMenu ,option,"LoansTree");
    	
    	
        USSDSavingsMenu ussdSavingsMenu = new USSDSavingsMenu();
        savingsTreeDataStructure = new TreeDataStructure<USSDMenu>(parentTreeDataStructure ,ussdSavingsMenu ,option,"SavingsTree");


        USSDAirtimeBundlesMenu ussdAirtimeBundlesMenu = new USSDAirtimeBundlesMenu();
        ussdAirtimeBundlesTreeDataStructure  = new TreeDataStructure<USSDMenu>(parentTreeDataStructure ,ussdAirtimeBundlesMenu ,option ,"AirtimeTree");

        USSDDepositMenu ussdDepositMenu = new USSDDepositMenu(session);
        ussdDepositTreeDataStructure = new TreeDataStructure<USSDMenu>(parentTreeDataStructure ,ussdDepositMenu ,option ,"DepositsTree");
        ussdVirtualEnterMoneyTreeDataStructure2 = new TreeDataStructure<USSDMenu>(ussdDepositTreeDataStructure,ussdVirtualEnterMoneyMenu ,option ,"VirtualEnterMoneyTree");


        USSDLoanProductsMenu ussdLoanProductsMenu = new USSDLoanProductsMenu(session);
        ussdTakeLoanTreeDataStructure = new TreeDataStructure<USSDMenu>(loanTreeDataStructure ,ussdLoanProductsMenu,option ,"LoanProductsTree");

        USSDRepayLoanMenu ussdRepayLoanMenu = new USSDRepayLoanMenu(session);
        ussdRepayLoanTreeDataStructure = new TreeDataStructure<>(loanTreeDataStructure, ussdRepayLoanMenu ,option,"RepayLoanTree");


        USSDLoanMoneyMenu ussdEnterLoanMoneyMenu = new USSDLoanMoneyMenu(session);
        ussdEnterLoanMoneyTreeDataStructure = new TreeDataStructure<>(ussdTakeLoanTreeDataStructure ,ussdEnterLoanMoneyMenu ,option ,"EnterMoneyTree");

        ussdSavingsDepositTreeDataStructure = new TreeDataStructure<>(savingsTreeDataStructure ,ussdDepositMenu ,option ,"SavingsTree_Deposit");

        ussdVirtualEnterMoneySavingsDepositTreeDataStructure = new TreeDataStructure<USSDMenu>(ussdSavingsDepositTreeDataStructure,ussdVirtualEnterMoneyMenu ,option ,"VirtualEnterMoneyTree");


        USSDWithdrawMoneyMenu ussdWithdrawMoneyMenu = new USSDWithdrawMoneyMenu(session);
        ussdWithdrawMoneyTreeDataStructure = new TreeDataStructure<>(savingsTreeDataStructure,ussdWithdrawMoneyMenu ,option ,"SavingsTree_Withdraw");
        ussdVirtualEnterMoneyTreeDataStructure1 = new TreeDataStructure<USSDMenu>(ussdWithdrawMoneyTreeDataStructure,ussdVirtualEnterMoneyMenu ,option ,"VirtualEnterMoneyTree");


        USSDTalkTimeMenu ussdTalkTimeMenu =  new USSDTalkTimeMenu(session);
        ussdTalkTimeTreeDataStructure = new TreeDataStructure<>(ussdAirtimeBundlesTreeDataStructure ,ussdTalkTimeMenu,option ,"TalkTimeTree");
 

        USSDBundlesAirtimeMenu ussdBundlesMenu = new USSDBundlesAirtimeMenu();
        ussdBundleTreeDataStructure = new TreeDataStructure<>(ussdAirtimeBundlesTreeDataStructure,ussdBundlesMenu ,option ,"BundlesTree");

        USSDRecipientAirtimeMenu ussdRecipientAirtimeMenu = new USSDRecipientAirtimeMenu();
        ussdRecipientPhoneNumberTreeDataStructure = new TreeDataStructure(ussdAirtimeBundlesTreeDataStructure ,ussdRecipientAirtimeMenu ,option ,"ReceipientTree");

    }
      
    public String backOption(){
    	
    	TreeDataStructure<USSDMenu> treeDataStructure = session.getTreeDataStructure();
    	session.setInputFlag(INPUT_FLAG.OFF);
    	session.setExecuteFlag(EXECUTE_FLAG.OFF);
    
    	if(treeDataStructure.hasParent()){
    		
    		TreeDataStructure<USSDMenu> parentTreeDataStructure = treeDataStructure.getParent();
    		session.setTreeDataStructure(parentTreeDataStructure);
    		
    		return parentTreeDataStructure.getMenu();
 		  		
    	}
    	
    	return Constants.invalidBackRequest ;
    	
    }

    public String executeWithFlag(Session session ,TreeDataStructure currentNodeTreeDataStructure ,USSDMenu ussdMenu){

        /// this is a redundant call not really professional


        if(session.getInputFlag()==INPUT_FLAG.ON){

            return executeWithInputFlag(currentNodeTreeDataStructure, ussdMenu);

        }

        if(session.getExecuteFlag()==EXECUTE_FLAG.ON){

            return executeWithExecuteFlag(currentNodeTreeDataStructure , ussdMenu);

        }

        if(session.getPhoneNumberFlag()== PHONE_NUMBER_FLAG.ON){

            return executeWithPhoneNumberFlag(currentNodeTreeDataStructure ,ussdMenu);
        }

        return null ;
    }


}
