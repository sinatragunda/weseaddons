package com.wese.weseaddons.bereaudechange.pojo;

public class Spread {

    private long id ;
    private double margin ;

    public Spread(){}

    public Spread(double margin) {
        this.margin = margin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMargin() {
        return margin;
    }

    public void setMargin(double margin) {
        this.margin = margin;
    }
}
