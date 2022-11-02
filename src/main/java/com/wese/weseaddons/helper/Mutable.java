/*

    Created by Sinatra Gunda
    At 11:14 AM on 7/5/2021

*/
package com.wese.weseaddons.helper;

public class Mutable<T> {

    T value = null ;

    public void setValue(T value){
        this.value = value;
    }

    public T getValue(){
        return  this.value ;
    }
}
