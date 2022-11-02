package com.wese.weseaddons.pojo;

public class WeseTenant {

    private long id ;
    private String timeZone ;

    public WeseTenant(){}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
