package com.wese.weseaddons.batchprocessing.wese;


public class Office {

    private long id ;
    private String name ;

    public Office(long id ,String name){
        this.id = id ;
        this.name = name ;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
