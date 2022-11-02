/*Created by Sinatra Gunda
  At 7:31 PM on 2/27/2020 */

package com.wese.weseaddons.crb.error;

import com.wese.weseaddons.errors.WeseErrorService;

public class NotSubscribedToCrbException extends RuntimeException implements WeseErrorService {

    @Override
    public String getMessage(){
        return "Tenant not subscribed to any credit bereaue organisation .Please you may try contacting transunion at transunion";
    }

}
