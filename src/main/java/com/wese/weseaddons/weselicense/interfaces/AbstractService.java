package com.wese.weseaddons.weselicense.interfaces;

import com.wese.weseaddons.weselicense.enumeration.SERVICE_TYPE;
import com.wese.weseaddons.weselicense.pojo.Service;
import com.wese.weseaddons.weselicense.pojo.Tarrif;

import com.wese.weseaddons.weselicense.pojo.Tarrif;


public abstract class AbstractService {

    private long id ;
    private Tarrif tarrif ;
    private Service service ;
    private SERVICE_TYPE serviceType ;


    public abstract SERVICE_TYPE getServiceType();
    public abstract double getBill();
    public abstract  Service getService();


}