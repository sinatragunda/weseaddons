package com.wese.weseaddons.weselicense.impl;


import com.wese.weseaddons.weselicense.enumeration.SERVICE_TYPE;
import com.wese.weseaddons.weselicense.interfaces.AbstractService;
import com.wese.weseaddons.weselicense.pojo.Service;
import com.wese.weseaddons.weselicense.pojo.Tarrif;

public class AdministrationService extends AbstractService {

    private int duration ;
    private Tarrif tarrif ;
    private SERVICE_TYPE serviceType ;

    public AdministrationService(Tarrif tarrif ,SERVICE_TYPE serviceType ,int duration){
        this.tarrif = tarrif ;
        this.duration = duration;
        this.serviceType =serviceType ;
    }


    @Override
    public Service getService(){
        return new Service(tarrif ,serviceType);
    }


    @Override
    public double getBill(){
        return 0 ;
    }

    @Override
    public SERVICE_TYPE getServiceType() {
        return serviceType;
    }
}
