
package com.wese.weseaddons.ussd.helper ;

import com.wese.weseaddons.ussd.session.Session;

public final class MenuAttributes{

	private String item ;
	private String functionName ;
	private String treeName ;
	private boolean hasChild ;

	private MenuAttributes(Builder builder){

		this.item = builder.getItem();
		this.functionName = builder.getFunctionAttribute();
		this.treeName = builder.getTreeNameAttribute();
		this.hasChild = builder.getChildAttribute();

	}

	public String getItem(){
		return item ;
	}

	public String getFunctionAttribute(){
		return functionName;
	}

	public boolean getChildAttribute(){
		return hasChild ;
	}

	public String getTreeNameAttribute(){
		return treeName ;
	}

	public static class Builder{

	    private String treeName ;
	    private String itemName ;
	    private String functionName ;
	    private Session session ;
	    private boolean hasChild = true;

	    public MenuAttributes build(){

	        return new MenuAttributes(this);

	    }

	    public Builder item(String item){
	    	this.itemName = item ;
	    	return this ;
	    }



	    public String getItem(){

	        return itemName;

	    }

	    public Builder treeName(String arg){

	        this.treeName = arg ;
	        return this ;
	    }

	    public Builder function(String arg){

	        this.functionName = arg ;
	        return this ;

	    }

	    public Builder child(boolean arg){

	        this.hasChild = arg ;
	        return this ;

	    }

	    public boolean getChildAttribute(){

	        return hasChild;
	    }

	    public String getTreeNameAttribute(){

	        return treeName;

	    }

	    public String getFunctionAttribute(){

	        return functionName;

	    }

	}

}