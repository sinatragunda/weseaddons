package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.ussd.tree.SessionInit;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.menu.submenu.subsubmenu.USSDLoanProductsMenu;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;
import com.wese.weseaddons.networkrequests.LoanApplicationRequest;
import com.wese.weseaddons.networkrequests.RequestBuilder;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.ussd.interactive.*;
import com.wese.weseaddons.ussd.session.*;
import com.wese.weseaddons.ussd.helper.*;

public class CreateLoanAction implements ExecuteMenuAction{

    private USSDLoanProductsMenu ussdTakeLoanMenu;

    @Override
    public String execute(Session session){


    	/// class still under construction 

    	Constants.setFlagsOff(session);
    	String stringAmount = session.getOption();
    	double principal = Double.parseDouble(stringAmount) ;
    	int executeIndex = session.getExecuteIndex();

    	Client client = session.getClient();

    	LoanProducts loanProduct = session.getLoanProductsList().get(executeIndex);

    	if(null!=Constants.isLoanAmountWithinLimits(loanProduct ,principal)){

    		return Constants.amountIsAboveMax ;

		}

		LoanApplicationRequest loanApplicationRequest = new LoanApplicationRequest(session);

    	if(!loanApplicationRequest.isClientEligable()){

    		return Constants.inElligibleClient ;
		}

    	CreateLoanParser createLoanParser = new CreateLoanParser(client ,loanProduct ,principal);
    	JSONObject httpRequestJsonObject = createLoanParser.getJsonObject();

    	String restUrl = "/loans";

		HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);
		httpClientBridge.setPostObject(httpRequestJsonObject);

    	String httpResponse = httpClientBridge.makeRequest(restUrl);

    	return response(httpResponse);


    }

    public String response(String response){


    	try{
    		JSONObject jsonObject = new JSONObject(response);
    		long loanId = jsonObject.getLong("loanId");
			String result = String.format("Loan created successfully ,your loan id is %d.\nPlease wait for approval\n%s\n" ,loanId ,Constants.backRequest);
    		return result ;
    	}

		catch (JSONException j){
		}


		/// this is a pending error NullPointer excepted here
		return null ;
	}

}
