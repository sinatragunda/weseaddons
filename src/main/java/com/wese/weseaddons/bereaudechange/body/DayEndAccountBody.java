package com.wese.weseaddons.bereaudechange.body;


import com.wese.weseaddons.helper.TimeHelper;

public class DayEndAccountBody {

    private String fromDate ;
    private String toDate ;
    private long officerId ;

    public DayEndAccountBody() {
    }

    public long getFromDate(){

        System.out.println("Is this now or "+fromDate);
        return TimeHelper.angularStringToDate(fromDate);

    }

    public void setFromDate(String fromDate){

        this.fromDate = fromDate;
    }

    public long getToDate(){

        return TimeHelper.angularStringToDate(toDate);

    }

    public void setToDate(String toDate){

        this.toDate = toDate;
    }

    public long getOfficerId() {
        return officerId;
    }

    public void setOfficerId(long officerId) {
        this.officerId = officerId;
    }
}
