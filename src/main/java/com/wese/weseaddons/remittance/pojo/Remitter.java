
package com.wese.weseaddons.remittance.pojo ;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Remitter{
	private String name ;
	private String phoneNumber ;


	public Remitter(){}

	public Remitter(String fullDetailsString){

		StringTokenizer stringTokenizer = new StringTokenizer(fullDetailsString ,"#");
		List<String> stringList = new ArrayList<>();

		while (stringTokenizer.hasMoreTokens()){

			stringList.add(stringTokenizer.nextToken());
		}

		this.name = stringList.get(0);
		this.phoneNumber = stringList.get(1);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Override
	public String toString(){

		return String.format("%s#%s\n",name ,phoneNumber);
	}
}