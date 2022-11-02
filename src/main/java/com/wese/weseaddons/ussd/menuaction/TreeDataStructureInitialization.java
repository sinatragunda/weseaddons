package com.wese.weseaddons.ussd.menuaction;

import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;

import java.util.ArrayList;
import java.util.List;

public class TreeDataStructureInitialization {

    private TreeDataStructure<USSDMenu> loanTreeDataStructure ;
    private TreeDataStructure<USSDMenu> savingsTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdAirtimeBundlesTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdTakeLoanTreeDataStructure;
    private TreeDataStructure<USSDMenu> ussdEnterLoanMoneyTreeDataStructure ;
    private TreeDataStructure<USSDMenu> ussdDepositTreeDataStructure ;


    private List<TreeDataStructure> asynchronousTreeDataStructuresList ;

    public static TreeDataStructureInitialization instance ;

    public static TreeDataStructureInitialization getInstance(){

        if(instance==null){

            instance = new TreeDataStructureInitialization();
        }

        return instance ;
    }

    public void init(){

        asynchronousTreeDataStructuresList = new ArrayList<>();
        asynchronousTreeDataStructuresList.add(loanTreeDataStructure);
        asynchronousTreeDataStructuresList.add(savingsTreeDataStructure);
        asynchronousTreeDataStructuresList.add(ussdAirtimeBundlesTreeDataStructure);
        asynchronousTreeDataStructuresList.add(ussdTakeLoanTreeDataStructure);
        asynchronousTreeDataStructuresList.add(ussdDepositTreeDataStructure);

    }

    public TreeDataStructureInitialization(){

        init();
    }

    public void asyncRequest(){


    }

}
