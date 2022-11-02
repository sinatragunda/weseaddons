
package com.wese.weseaddons.bereaudechange.session;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;


@Component
public class FxInit{


    public static FxInit instance ;


    public static FxInit getInstance(){

        if(instance==null){
            instance = new FxInit();
        }

        return instance ;
    }

    public FxInit(){
        init();
    }


    @PostConstruct
    public void init(){

        instance = this ;

       // loadSessionData();

    }

    public void loadSessionData(){

        FxSession.getInstance();
        System.out.println("Done loading clients data now");

    }

    public void initSchema(){

    	//FxSchema.setInstance();
    }




}
