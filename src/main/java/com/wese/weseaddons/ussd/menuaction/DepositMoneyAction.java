package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.ussd.tree.SessionInit;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;
import org.json.JSONException;
import org.json.JSONObject;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.ussd.interactive.DepositParser;
import com.wese.weseaddons.networkrequests.RequestBuilder;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.menu.USSDMenuRouter;
import com.wese.weseaddons.ussd.tree.ExecuteMenuAction;


import java.math.BigDecimal;
import java.util.List;

public class DepositMoneyAction implements ExecuteMenuAction{


	@Override
	public String execute(Session session){

		List<SavingsAccount> savingsAccountList = session.getSavingsAccountList();

		String stringAmount = session.getOption();
		
		BigDecimal transactionAmount = new BigDecimal(stringAmount) ;
		
		int executeIndex = session.getExecuteIndex()-1;

		SavingsAccount savingsAccount = savingsAccountList.get(executeIndex);

		DepositParser depositParser = new DepositParser(session ,transactionAmount);
		JSONObject httpRequestJsonObject = depositParser.getJsonObject();

		System.out.println(httpRequestJsonObject.toString());

		Long savingsAccountId = savingsAccount.getId();

		StringBuilder stringBuilder = new StringBuilder("savingsaccounts/");
		stringBuilder.append(savingsAccountId);
		stringBuilder.append("/transactions?command=deposit");
		String restUrl = stringBuilder.toString();


		HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.POST);

		httpClientBridge.setPostObject(httpRequestJsonObject);

		String httpResponse = httpClientBridge.makeRequest(restUrl);

		System.out.println(httpResponse);

		return response(httpResponse,transactionAmount ,session);

    }

    public String response(String arg ,BigDecimal transactionAmount ,Session session){

		try{

			JSONObject jsonObject = new JSONObject(arg);
			long savingsId = jsonObject.getLong("savingsId");
			Runnable runnable = SessionInit.getInstance().loadSavingsAccountsAsync(session);
			OffshoreThread.run(runnable);
			return String.format("Deposit of %.2f made successfully to account with id %d\n%s\n",transactionAmount ,savingsId , Constants.backRequest);

		}

		catch (JSONException j){
			j.printStackTrace();
		}

		return Constants.failedUssdTransaction ;
	}
}
