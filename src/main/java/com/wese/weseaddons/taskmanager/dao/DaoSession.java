//package com.wese.weseaddons.taskmanager.dao;
//
//import com.wese.weseaddons.helper.Constants;
//import org.hibernate.Session;
//import javax.transaction.Transaction;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.*;
//import org.hibernate.validator.internal.metadata.aggregated.MetaDataBuilder;
//
//public class DaoSession{
//
//    private SessionFactory sessionFactory ;
//    private StandardServiceRegistry ssr ;
//    private Session session ;
//    private Metadata metaData ;
//    private String tenantIdentifier ;
//    private Configuration configuration ;
//
//    public DaoSession(){
//        init();
//    }
//
//    public DaoSession(String tenantIdentifier){
//
//        this.tenantIdentifier = tenantIdentifier ;
////        StringBuilder stringBuilder = new StringBuilder(Constants.database);
////        stringBuilder.append(tenantIdentifier);
////        String databaseConnectionProperty = stringBuilder.toString() ;
//
////        configuration = HibernateProperties.standardConfig(tenantIdentifier);
////        configuration.setProperty("hibernate.connection.url",databaseConnectionProperty);
//
//        System.out.println("Tenant identifier is "+tenantIdentifier);
//
//        HibernateProperties hibernateProperties = new HibernateProperties();
//        this.session = hibernateProperties.standardConfig(tenantIdentifier);
//        init();
//
//
//    }
//
//    public void init(){}
//
//    public <T> void saveDDL(T object) {
//
//        Transaction transaction = null ;
//        try{
//
//            session.beginTransaction();
//            session.save(object);
//            session.getTransaction().commit();
//
//        }
//        catch (Exception exception){
//            try{
//                session.getTransaction().rollback();
//            }
//
//            catch (Exception exception1){
//                System.out.println("Failed to roll back system "+exception1.getMessage());
//            }
//        }
//    }
//
//    public <T> void update(T object) {
//
//        try{
//            session.beginTransaction();
//            session.update(object);
//            session.getTransaction().commit();
//        }
//
//        catch (Exception exception){
//            try{
//
//                exception.printStackTrace();
//                session.getTransaction().rollback();
//            }
//
//            catch (Exception exception1){
//                System.out.println("Failed to roll back system "+exception1.getMessage());
//            }
//        }
//    }
//
//    public Session getSession() {
//        return session ;
//    }
//
//    public void close() {
//        session.close();
//    }
//}
