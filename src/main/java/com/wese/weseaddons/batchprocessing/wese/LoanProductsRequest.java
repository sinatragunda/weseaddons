package com.wese.weseaddons.batchprocessing.wese;




import java.util.ArrayList;
import java.util.List;

import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.http.HttpClientBridge;
import com.wese.weseaddons.batchprocessing.client.LoanProduct;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoanProductsRequest{

    private JSONObject jsonObject ;
    private List<LoanProduct> loanProductList ;


    public LoanProductsRequest(){

    }

    public void loadLoanProducts(){

        String path = "/loanproducts";
        loanProductList = new ArrayList();


        HttpClientBridge httpClientBridge = new HttpClientBridge(REQUEST_METHOD.GET);
        String stringResponse = httpClientBridge.makeRequest(path);

        try {

            JSONArray jsonArray = new JSONArray(stringResponse);
            
            if(jsonArray.length() < 1){
                
                return ;
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

                LoanProduct loanProduct = new LoanProduct(id ,name ,minPrincipal ,maxPrincipal ,interestRatePerPeriod,repaymentsEvery  ,numberofRepayments,amortizationType ,interestType ,interestCalculationPeriodId ,transactionProcessingStrategyId ,repaymentFrequencyType);
                loanProductList.add(loanProduct);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<LoanProduct> getLoanProducts(){

        return this.loanProductList ;

    }


}
