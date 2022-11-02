package com.wese.weseaddons.bereaudechange.pojo;

public class StandardCurrency{

	private long id ;
	private String name ;
	private String country ;
	private String internationalCode ;

    public StandardCurrency() {
    }

    public StandardCurrency(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}