/*Created by Sinatra Gunda
  At 14:18 on 9/14/2020 */

package com.wese.weseaddons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.helper.Helper;
import org.json.JSONObject;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanApplication {

    private Double principal ;
    private int loanTermFrequency ;
    private int numberOfRepayments ;
    private Long loanProductId ;
    private boolean disburseToSavings ;
    private Long clientId ;




    public JSONObject jsonObject(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("principal" ,principal);
        jsonObject.put("productId" ,loanProductId);
        jsonObject.put("numberOfRepayments" ,numberOfRepayments);
        jsonObject.put("dateFormat" ,"DD MMM yyyy");
        jsonObject.put("clientId" ,clientId);


        return jsonObject ;

    }


}
