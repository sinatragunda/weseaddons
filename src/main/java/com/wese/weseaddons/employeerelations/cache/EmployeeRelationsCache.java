/*Created by Sinatra Gunda
  At 02:27 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.cache;

import com.wese.weseaddons.daofactory.DAOQuery;
import com.wese.weseaddons.employeerelations.dao.EmployerDAO;
import com.wese.weseaddons.employeerelations.pojo.Employer;
import javafx.beans.value.ObservableBooleanValue;

import javax.naming.SizeLimitExceededException;
import java.util.List;
import java.util.Map;

public class EmployeeRelationsCache {

    private static String tenantIdentifier ;
    private boolean writeFlag = false ;
    private boolean firstLoad = true ;

    private EmployerDAO employerDAO ;
    private List<Employer> employerList ;

    private static EmployeeRelationsCache instance ;

    public EmployeeRelationsCache(String tenantIdentifier)
    {
        this.tenantIdentifier =tenantIdentifier ;
        getInstance();
    }


    public static EmployeeRelationsCache getInstance() {
        if(instance==null){
            instance = new EmployeeRelationsCache(tenantIdentifier);
        }
        return instance;
    }

    public List<Employer> employerList(){

        if(firstLoad){
            employerDAO = new EmployerDAO(tenantIdentifier);
            this.employerList = employerDAO.findAll();
            firstLoad = false ;
        }
        return employerList ;

    }

    public void writeFlag(boolean value){

        /// some synchronization class needed here for multi users
        if(writeFlag !=value){
            writeFlag = value;
        }

        if(writeFlag){
            ///reload data then set to false
            firstLoad = true ;
            employerList();
            writeFlag = false ;
        }
    }

    public Employer find(Long id){

        while (writeFlag){

            try{
                Thread.sleep(1000);
            }
            catch (InterruptedException r){

            }
        }
        Employer employer = (Employer)DAOQuery.find(employerList ,id);
        return employer ;

    }

}
