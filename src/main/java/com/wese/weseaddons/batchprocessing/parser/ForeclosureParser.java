/*Created by Sinatra Gunda
  At 15:11 on 9/3/2020 */

package com.wese.weseaddons.batchprocessing.parser;

import com.wese.weseaddons.batchprocessing.helper.Constants;
import com.wese.weseaddons.batchprocessing.helper.Parameters;
import com.wese.weseaddons.batchprocessing.pojo.Foreclosure;
import org.json.JSONException;
import org.json.JSONObject;

public class ForeclosureParser implements IParser {

    private JSONObject jsonObject ;
    private Foreclosure foreclosure ;

    @Override
    public JSONObject jsonObject(){

        try{
            jsonObject = new JSONObject();
            jsonObject.put("locale" ,"en");
            jsonObject.put("dateFormat","dd MMM yyyy");
            jsonObject.put("transactionDate" , foreclosure.getTransactionDate());
            jsonObject.put("note", foreclosure.getNote());

        }

        catch (JSONException jsonException){

            System.out.println(jsonObject.toString());

        }
        return jsonObject;
    }


    public ForeclosureParser(Foreclosure foreclosure){
        this.foreclosure = foreclosure ;

    }

}
