package com.wese.weseaddons.weselicense.dao;

import com.wese.weseaddons.helper.Constants;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.transaction.Transaction;

public class DaoSession{

    private SessionFactory sessionFactory ;
    private StandardServiceRegistry ssr ;
    private Session session ;
    private Metadata metaData ;
    private String tenantIdentifier ;


    public DaoSession(){

        init();
    }

    public void init(){
        
 	this.ssr = new StandardServiceRegistryBuilder().configure("weselicense_hibernate.cfg.xml").build();
	this.metaData = new MetadataSources(ssr).getMetadataBuilder().build();
	this.sessionFactory = metaData.getSessionFactoryBuilder().build();
        this.session = sessionFactory.openSession();
    }

    public <T> void saveDDL(T object) {

        try{

            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();

        }
        catch (Exception exception){
            try{
                session.getTransaction().rollback();
            }

            catch (Exception exception1){
                System.out.println("Failed to roll back system "+exception1.getMessage());
            }
        }
    }

    public <T> void update(T object){

        try{

            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        }

        catch (Exception exception){
            try{
                session.getTransaction().rollback();
            }

            catch (Exception exception1){
                System.out.println("Failed to roll back system "+exception1.getMessage());
            }
        }

    }

    public Session getSession() {
        return session ;
    }

    public void close() {

        sessionFactory.close();
        session.close();
    }
}
