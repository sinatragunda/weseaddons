package com.wese.weseaddons.weselicense.logic;


import com.wese.weseaddons.weselicense.enumeration.SERVICE_TYPE;
import com.wese.weseaddons.weselicense.impl.AdministrationService;
import com.wese.weseaddons.weselicense.impl.ConsultationService;
import com.wese.weseaddons.weselicense.impl.TrainingService;
import com.wese.weseaddons.weselicense.interfaces.AbstractService;
import com.wese.weseaddons.weselicense.pojo.Tarrif;

public class ServiceBuilder {

    private Tarrif tarrif ;
    private SERVICE_TYPE serviceType ;
    private int duration ;


    public ServiceBuilder(Tarrif tarrif , SERVICE_TYPE serviceType ,int duration){
        this.serviceType = serviceType ;
        this.duration = duration ;
        this.tarrif = tarrif ;
    }

    public AbstractService makeService(){

        AbstractService services = null ;

        switch(serviceType){
            case ADMINISTRATION:
                services = new AdministrationService(tarrif ,serviceType ,duration);
                break;
            case CONSUTATION:
                services = new ConsultationService(tarrif ,serviceType ,duration);
                break;
            case TRAINING:
                services = new TrainingService(tarrif,serviceType ,duration);
                break;
        }

        return services ;

    }
}
