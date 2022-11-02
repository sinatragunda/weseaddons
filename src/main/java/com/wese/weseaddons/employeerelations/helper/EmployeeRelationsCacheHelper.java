/*Created by Sinatra Gunda
  At 04:04 on 9/26/2020 */

package com.wese.weseaddons.employeerelations.helper;

import com.wese.weseaddons.employeerelations.cache.EmployeeRelationsCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRelationsCacheHelper{


    private Map<String ,EmployeeRelationsCache> employeeRelationsCacheMap ;


    public EmployeeRelationsCacheHelper(){
        employeeRelationsCacheMap = new HashMap<>();
    }

    public EmployeeRelationsCache employeeRelationsCache(String key ,EmployeeRelationsCache employeeRelationsCache ,boolean add){

        EmployeeRelationsCache employeeRelationsCacheOut = employeeRelationsCacheMap.get(key) ;

        if(add){
            if(employeeRelationsCacheOut!=null){
                employeeRelationsCacheMap.replace(key ,employeeRelationsCache);
                return employeeRelationsCache ;
            }

            employeeRelationsCacheMap.putIfAbsent(key ,new EmployeeRelationsCache(key));
            return employeeRelationsCache;
        }

        return employeeRelationsCacheOut ;
    }
}
