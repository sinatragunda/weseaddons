package com.wese.weseaddons.weselicense.pojo;

import com.wese.weseaddons.weselicense.enumeration.SERVICE_TYPE;

public class Service {

    private long id ;
    private Tarrif tarrif ;
    private SERVICE_TYPE serviceType ;


    public Service(){}

    public Service(Tarrif tarrif ,SERVICE_TYPE serviceType){
        this.tarrif = tarrif ;
        this.serviceType = serviceType ;
    }

    public SERVICE_TYPE getServiceType() {
        return serviceType;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Tarrif getTarrif() {
        return tarrif;
    }

    public void setServiceType(SERVICE_TYPE serviceType) {
        this.serviceType = serviceType;
    }

    public void setTarrif(Tarrif tarrif) {
        this.tarrif = tarrif;
    }
}
