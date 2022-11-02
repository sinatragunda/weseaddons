/*

    Created by Sinatra Gunda
    At 6:28 PM on 8/17/2021

*/
package com.wese.weseaddons.pojo;

public class LoanType {

    private Long id ;
    private String value ;

    public LoanType(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
