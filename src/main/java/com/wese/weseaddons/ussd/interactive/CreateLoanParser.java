package com.wese.weseaddons.ussd.interactive;

import org.json.JSONObject;
import org.json.JSONArray ;
import org.json.JSONException ;

import com.wese.weseaddons.ussd.interfaces.*;
import com.wese.weseaddons.ussd.helper.Constants;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.pojo.*;

public class CreateLoanParser implements JsonParser{


	private JSONObject jsonObject ;
	private Client client ;
	private LoanProducts loanProduct ;
	private double principal ;
	private boolean errorRaised = false;


	@Override
	public JSONObject getJsonObject(){

		return jsonObject;

	}

	public void init(){

		try{
			
			jsonObject = new JSONObject();

			jsonObject.put("dateFormat" ,"dd MMMM yyyy");
			jsonObject.put("locale","en_ZW");
			jsonObject.put("principal",principal);
			jsonObject.put("submittedOnDate",Constants.getTodayDate());
			jsonObject.put("expectedDisbursementDate",Constants.getTodayDate());
			jsonObject.put("clientId",client.getId());
			jsonObject.put("productId" ,loanProduct.getId());

			///values static
			jsonObject.put("loanTermFrequencyType" ,2);
			jsonObject.put("loanTermFrequency" ,loanProduct.getNumberOfRepayments());
			jsonObject.put("interestRatePerPeriod" ,loanProduct.getInterestRatePerPeriod());
			jsonObject.put("repaymentEvery",loanProduct.getRepaymentsEvery()) ;
			jsonObject.put("numberOfRepayments",loanProduct.getNumberOfRepayments());
			jsonObject.put("amortizationType",loanProduct.getAmortizationType());
			jsonObject.put("interestType",loanProduct.getInterestType());
			jsonObject.put("interestCalculationPeriodType" ,loanProduct.getInterestCalculationPeriodType());
			jsonObject.put("loanType","individual");
			jsonObject.put("transactionProcessingStrategyId" ,loanProduct.getTransactionProcessingStrategyId());
			jsonObject.put("repaymentFrequencyType" ,loanProduct.getRepaymentFrequencyType());

			JSONArray jsonArray = new JSONArray();

	        JSONObject subJsonObject= new JSONObject();
	        subJsonObject.put("expectedDisbursementDate" ,Constants.getTodayDate());
	        subJsonObject.put("expectedDisbursementDate" ,Constants.getTodayDate());
	        subJsonObject.put("principal",principal);
	        subJsonObject.put("approvedPrincipal",principal);

	        jsonArray.put(subJsonObject);
	        jsonObject.put("disbursementData",jsonArray);


		}

		catch(JSONException jsonException){
			errorRaised = true ;
		}


	}

	public CreateLoanParser(Client client ,LoanProducts loanProduct ,double principal){

		this.client = client ;
		this.loanProduct = loanProduct;
		this.principal = principal ;
		init();

	}




}