package com.wese.weseaddons.bereaudechange.body;


public class FxAccountRevaluationBody{

	private long id ;
	private String startDate ;
	private String endDate ;
	private int filterBy ;


	public FxAccountRevaluationBody(){}

	public long getId(){
		return id ;
	}

	public String getStartDate(){
	      return startDate ;
	}

	public void setStartDate(String s){
		this.startDate = s ;
	}

	public int getFilterBy(){
		return filterBy ;
	}

	public void setFilterBy(int s){
		this.filterBy = s ;
	}

	public void setEndDate(String s){
		this.endDate = s ;
	}

	public String getEndDate(){
		return endDate ;
	}
}