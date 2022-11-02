/*Created by Sinatra Gunda
  At 3:06 AM on 3/2/2020 */

package com.wese.weseaddons.crb.pojo;

import com.wese.weseaddons.helper.JsonHelper;

public class CreditInformationRecord {

    public String getJsonString(){

        return JsonHelper.objectToJson(this);

    }
}
