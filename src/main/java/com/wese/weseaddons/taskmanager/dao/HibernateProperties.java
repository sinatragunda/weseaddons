//package com.wese.weseaddons.taskmanager.dao;
//
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//
//public class HibernateProperties {
//
//    private Metadata metaData;
//    private StandardServiceRegistry standardServiceRegistry ;
//    private Session session ;
//    private SessionFactory sessionFactory ;
//
//
////    public static Configuration standardConfig(){
////
////        Configuration configuration = new Configuration();
////        configuration.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
////        configuration.setProperty("hibernate.connection.driver_class","com.mysql.cj.jdbc.Driver");
////        configuration.setProperty("hibernate.connection.username" ,"root");
////        configuration.setProperty("hibernate.connection.password" ,"mysql");
////        configuration.setProperty("hibernate.hbm2ddl.auto" ,"update");
////        configuration.setProperty("show_sql" ,"true");
////
////
////        ///add files here
////        configuration.addFile("src\\main\\resources\\TaskManager.hbm.xml");
////        configuration.addFile("src\\main\\resources\\TaskJob.hbm.xml");
////        configuration.addFile("src\\main\\resources\\TaskDetails.hbm.xml");
////        configuration.addFile("src\\main\\resources\\TaskAction.hbm.xml");
////        configuration.addFile("src\\main\\resources\\TaskNote.hbm.xml");
////
////        return configuration ;
////
////    }
//
//    public Session standardConfig(String tenantIdentifier){
//
//        StringBuilder stringBuilder = new StringBuilder(tenantIdentifier);
//        stringBuilder.append(".cfg.xml");
//
//        this.standardServiceRegistry = new StandardServiceRegistryBuilder().configure(stringBuilder.toString()).build();
//        this.metaData = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
//        this.sessionFactory = metaData.getSessionFactoryBuilder().build();
//        this.session = sessionFactory.openSession();
//        return session ;
//
//    }
//}
