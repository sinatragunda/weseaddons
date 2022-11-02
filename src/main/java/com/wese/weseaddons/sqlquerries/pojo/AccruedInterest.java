/*Created by Sinatra Gunda
  At 08:39 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class AccruedInterest implements PojoInterface{

    private Double accruedInterest ;


    @Override
    public String getSchema() {
        return null;
    }

    @Override
    public Long getId() {
        return null;
    }


    public Double getAccruedInterest() {
        return accruedInterest;
    }

    public void setAccruedInterest(Double accruedInterest) {
        this.accruedInterest = accruedInterest;
    }
}
