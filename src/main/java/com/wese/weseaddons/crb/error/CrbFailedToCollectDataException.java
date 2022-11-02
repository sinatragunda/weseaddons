/*Created by Sinatra Gunda
  At 3:15 AM on 3/2/2020 */

package com.wese.weseaddons.crb.error;

import com.wese.weseaddons.errors.WeseErrorService;

public class CrbFailedToCollectDataException extends RuntimeException implements WeseErrorService {


    @Override
    public String getMessage(){
        return "Failed to collect sufficient data for this transaction to postit to CRB";
    }
}
