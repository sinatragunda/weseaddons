/*Created by Sinatra Gunda
  At 1:51 PM on 2/24/2020 */

package com.wese.weseaddons.crb.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.crb.error.CrbException;
import com.wese.weseaddons.crb.pojo.CrbSubmissionError;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.helper.ObjectNodeHelper;

public class CrbSubmissionErrorHelper {

    public static ObjectNode errorCode(String response){

        CrbSubmissionError crbSubmissionError = JsonHelper.stringToObject(response ,new CrbSubmissionError());
        // need some error handling capability here for all kinds of errors

        System.err.println(crbSubmissionError.getRecordErrors().size());

        if(crbSubmissionError.getResponseCode() != 200){
            throw new CrbException(crbSubmissionError.getMessage());
        }

        return Helper.statusNodes(true);
    }


}
