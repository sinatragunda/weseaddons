package com.wese.weseaddons.ussd.tree;

import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Consumer;

public class TreeDataStructure<USSDMenu> implements ExecuteMenuAction{

	private  String treeName;
	private String className ;
	private TreeDataStructure<USSDMenu> parentTreeDataStructure = null ;
	private USSDMenu ussdMenu;
	private List<TreeDataStructure<USSDMenu>> childMenuList = new ArrayList<>();
	
	
	@Override
	public String execute(Session session) {

		ExecuteMenuAction executeMenuAction= null;
		Class<?> executeClass = null ;
		String results = null ;

		Session updatedSession = SessionDetails.getInstance().getSessionObject(session.getSessionId());
		session = updatedSession ;

		/// the we should execute what ever function is here
		try {

			executeClass = Class.forName(className);
			executeMenuAction = (ExecuteMenuAction)executeClass.newInstance() ;
			results = executeMenuAction.execute(updatedSession);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IllegalAccessException e) {

			e.printStackTrace();

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			// might get this error here
			e.printStackTrace();

		}

		return results ;
		
	}
	
	private Consumer<TreeDataStructure> setToParent = (e)->{
			
		setParent(e);
		
	};

	public TreeDataStructure(){}
	
	public TreeDataStructure(USSDMenu ussdMenu){
		
		this.ussdMenu = ussdMenu ;
	
	}
	
	public TreeDataStructure(TreeDataStructure<USSDMenu> parentTreeDataStructure ,USSDMenu ussdMenu,String className ,String treeName) {
		
		this.className = className ;
		this.ussdMenu = ussdMenu ;
		this.treeName = treeName ;
		this.parentTreeDataStructure = parentTreeDataStructure ;
		this.parentTreeDataStructure.addDataNode(this);
	
		
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public USSDMenu getUssdMenu(){
		return ussdMenu ;
	}
		
	public void addDataNode(TreeDataStructure<USSDMenu> treeDataStructure){
		
		childMenuList.add(treeDataStructure);
	    childMenuList.forEach(setToParent);
	
	}

	public TreeDataStructure<USSDMenu> getChildByTreeName(String treeName){

		Iterator<TreeDataStructure<USSDMenu>> iterator = childMenuList.iterator();
		
		while(iterator.hasNext()){

			TreeDataStructure currrentTreeDataStructure = iterator.next();

			String currentTreeName = currrentTreeDataStructure.getTreeName() ;

			if(currentTreeName.equalsIgnoreCase(treeName)){
				return  currrentTreeDataStructure;
			}

		}

		return null ;
	}

	public List<TreeDataStructure<USSDMenu>> getChildMenuList() {
		
		return childMenuList ;
		
	}
	
	public TreeDataStructure<USSDMenu> getParent() {
		return parentTreeDataStructure ;
	}
	
	public void setParent(TreeDataStructure<USSDMenu> treeDataStructure) {
		
		if(null==getParent()) {
			
			parentTreeDataStructure = this ; 
			return ;
		}
				
	}
	
	public boolean hasParent() {		
		
		if(null==getParent()) {
			return false ;
		}
		
		return true ;
	}
	
	public boolean hasChilden() {
		
		if(childMenuList.size()==0) {
			
			/// has no children hence execute 
			return false ;
			
		}
		
		return true ;
	}
	
	public String getMenu() {

		String menu = ((com.wese.weseaddons.ussd.interfaces.USSDMenu) ussdMenu).getMenuList();
		return menu ;
		
	}
	
	
	

}
