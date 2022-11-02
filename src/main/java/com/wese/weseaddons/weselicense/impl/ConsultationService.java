package com.wese.weseaddons.weselicense.impl;


import com.wese.weseaddons.weselicense.enumeration.SERVICE_TYPE;
import com.wese.weseaddons.weselicense.enumeration.TIME_UNIT_TYPE;
import com.wese.weseaddons.weselicense.interfaces.AbstractService;
import com.wese.weseaddons.weselicense.pojo.Service;
import com.wese.weseaddons.weselicense.pojo.Tarrif;

public class ConsultationService extends AbstractService {

    private int duration ;
    private Tarrif tarrif ;
    private SERVICE_TYPE serviceType ;
    private final double submissionFee = 1.0 ;

    public ConsultationService(Tarrif tarrif ,SERVICE_TYPE serviceType ,int duration){
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

        double amountPerUnit = duration / tarrif.getTimeUnit() ;
        double bill = (tarrif.getAmount() * amountPerUnit) + submissionFee;

        String f = String.format("%.2f" ,bill);
        Double formatedBill = Double.parseDouble(f);

        return formatedBill.doubleValue() ;
    }

    @Override
    public SERVICE_TYPE getServiceType() {
        return serviceType;
    }
}
