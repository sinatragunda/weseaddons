/*Created by Sinatra Gunda
  At 2:53 AM on 2/25/2020 */

package com.wese.weseaddons.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.errors.WeseErrorService ;

public class ErrorMessageParser {

    public static ObjectNode parse(WeseErrorService weseCoreErrorService){

        ObjectNode objectNode = Helper.createObjectNode();

        objectNode.put("weseErrorStatus" ,false);
        objectNode.put("status" ,false);
        objectNode.put("weseErrorMessage",weseCoreErrorService.getMessage());

        return objectNode ;

    }
}
