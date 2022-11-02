/*

    Created by Sinatra Gunda
    At 9:21 AM on 6/22/2021

*/
package com.wese.weseaddons.pojo;

public class Currency {
    public String code;
    public String name;
    public int decimalPlaces;
    public int inMultiplesOf;
    public String displaySymbol;
    public String nameCode;
    public String displayLabel;

    public Currency(){}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public int getInMultiplesOf() {
        return inMultiplesOf;
    }

    public void setInMultiplesOf(int inMultiplesOf) {
        this.inMultiplesOf = inMultiplesOf;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    public String getNameCode() {
        return nameCode;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}
