package com.wese.weseaddons.networkrequests;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.pojo.LoanProducts;

public class LoanProductsRequest{

	private String tenantIdentifier;
	private JSONObject jsonObject ;


	public LoanProductsRequest(String tenantIdentifier){
		this.tenantIdentifier = tenantIdentifier;
	}

	public List<LoanProducts> getLoanGeneratorProducts(){

		String url = "/loanproducts";

		List<LoanProducts> list = new ArrayList();

		String stringResponse = RequestBuilder.build(REQUEST_METHOD.GET ,url).makeRequest();

		try {
			
			JSONArray jsonArray = new JSONArray(stringResponse);

			if(jsonArray.length() < 1){

				return list ;
			}

			for(int i =0 ; i < jsonArray.length() ;++i){

				long id = jsonArray.getJSONObject(i).getLong("id");
				String name = jsonArray.getJSONObject(i).getString("name");
				double minPrincipal = jsonArray.getJSONObject(i).getDouble("minPrincipal");
				double maxPrincipal = jsonArray.getJSONObject(i).getDouble("maxPrincipal");
				int numberofRepayments = jsonArray.getJSONObject(i).getInt("numberOfRepayments");

				int repaymentsEvery = jsonArray.getJSONObject(i).getInt("repaymentEvery");
				double interestRatePerPeriod = jsonArray.getJSONObject(i).getDouble("interestRatePerPeriod");

				JSONObject amortizationObject = jsonArray.getJSONObject(i).getJSONObject("amortizationType");
				int amortizationType = amortizationObject.getInt("id");

				JSONObject interestObject = jsonArray.getJSONObject(i).getJSONObject("interestType");
				int interestType = interestObject.getInt("id");

				JSONObject interestCalculationPeriodObject = jsonArray.getJSONObject(i).getJSONObject("interestCalculationPeriodType");
			    int interestCalculationPeriodId = interestCalculationPeriodObject.getInt("id");
				int transactionProcessingStrategyId = jsonArray.getJSONObject(i).getInt("transactionProcessingStrategyId");

				JSONObject repaymentFrequencyTypeJsonObject = jsonArray.getJSONObject(i).getJSONObject("repaymentFrequencyType");
				int repaymentFrequencyType =repaymentFrequencyTypeJsonObject.getInt("id");

				LoanProducts loanProducts = new LoanProducts(id ,name ,minPrincipal ,maxPrincipal ,interestRatePerPeriod,repaymentsEvery  ,numberofRepayments,amortizationType ,interestType ,interestCalculationPeriodId ,transactionProcessingStrategyId ,repaymentFrequencyType);
				list.add(loanProducts);

			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list ;
	}
	

}