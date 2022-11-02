package com.wese.weseaddons.weselicense.dao;


import com.wese.weseaddons.weselicense.dao.DaoSession ;
import com.wese.weseaddons.taskmanager.interfaces.DAOService;
import com.wese.weseaddons.weselicense.pojo.LicenseDuration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class LicenseDurationDao implements DAOService{

    private DaoSession daoSession;

    public LicenseDurationDao(DaoSession daoSession){
        this.daoSession = daoSession ;
    }

    public List findAll(){
        return null;
    }

    public List<LicenseDuration> findAllWithCustomCriteria(long clientId){


        String hql = "FROM LicenseDuration where tenant_id=:value";
        List<LicenseDuration> licenseDurationList = new ArrayList<>();
        Query<LicenseDuration> licenseDurationQuery  = daoSession.getSession().createQuery(hql ,LicenseDuration.class);
        licenseDurationQuery.setParameter("value" ,clientId);
        licenseDurationList = licenseDurationQuery.getResultList();

        if(licenseDurationList.isEmpty()){

            return new ArrayList<>() ;
        }

        return licenseDurationList;

    }
}
