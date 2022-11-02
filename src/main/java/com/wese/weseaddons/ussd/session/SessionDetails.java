package com.wese.weseaddons.ussd.session;

import com.wese.weseaddons.ussd.helper.IntegerUtils;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;

import java.util.ArrayList;
import java.util.Iterator;

public class SessionDetails extends ArrayList{

    public static SessionDetails instance ;

    public static SessionDetails getInstance(){

        if(instance==null){
            instance = new SessionDetails();
        }
        return instance ;
    }

    public SessionDetails(){}

    public SessionDetails(Session session){

        push(session);

    }


    public boolean push(Session currentSession){

        if(size()==0){

            add(currentSession);
            return true;

        }

        Iterator<Session> iterator = this.iterator();
        while(iterator.hasNext()){

            Session iteratorSession = iterator.next();

            boolean hasSessionObject = IntegerUtils.compareSessionId(currentSession ,iteratorSession);

            if(!hasSessionObject){

                add(currentSession);

                return true ;

            }
        }

        return false ;
    }

    public Session getSessionObject(String sessionId){

        Iterator<Session> sessionIterator = this.iterator();

        while(sessionIterator.hasNext()){

            Session currentSession = sessionIterator.next();
            String currentSessionId = currentSession.getSessionId();

            boolean equals = sessionId.equalsIgnoreCase(currentSessionId);

            if(equals){

                return currentSession ;
            }

        }

        return null ;
    }

    public void updateSessionObject(Session session ,TreeDataStructure<USSDMenu> treeDataStructure){

        String sessionId = session.getSessionId();
        getSessionObject(sessionId).setTreeDataStructure(treeDataStructure);

    }

    public void updateSessionObject(Session session){


        Iterator<Session> sessionIterator = this.iterator();

        while(sessionIterator.hasNext()){

            Session currentSession = sessionIterator.next();
            String currentSessionId = currentSession.getSessionId();
            String sessionId = session.getSessionId();

            boolean equals = sessionId.equalsIgnoreCase(currentSessionId);

            if(equals){
                
                currentSession = session;
            }

        }

        return ;
    }

}
