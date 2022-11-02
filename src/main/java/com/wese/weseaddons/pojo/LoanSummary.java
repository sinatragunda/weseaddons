/*

    Created by Sinatra Gunda
    At 6:39 PM on 8/17/2021

*/
package com.wese.weseaddons.pojo;

import java.math.BigDecimal;

public class LoanSummary {

    private BigDecimal totalOutstanding;

    public LoanSummary(){}

    public BigDecimal getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(BigDecimal totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }
}
