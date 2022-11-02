package com.wese.weseaddons.daofactory;


import com.wese.weseaddons.interfaces.PojoInterface;

import java.util.Iterator;
import java.util.List;

public class DAOHelper {

    // public static boolean containsTenant(List<BDXTenantData> bdxTenantDataList , String tenantIdentifier) {

    //     Iterator iterator = bdxTenantDataList.iterator();

    //     while (iterator.hasNext()) {
    //         BDXTenantData bdxTenantData = (BDXTenantData) iterator.next();
    //         String tenant = bdxTenantData.getTenant().getTenantName();
    //         if (tenant.equalsIgnoreCase(tenantIdentifier)) {
    //             return true;
    //         }

    //     }
    //     return false ;
    // }

    public static String getClassMapperName(PojoInterface pojoInterface){

        String className = pojoInterface.getClass().getSimpleName();
        String packageName = pojoInterface.getClass().getPackage().getName();

        packageName = extractPackage(packageName);

        StringBuilder stringBuilder = new StringBuilder(packageName);
        stringBuilder.append((".mapper."));
        stringBuilder.append(className+"Mapper");
        return stringBuilder.toString();

    }

    public static String extractPackage(String arg){

        int index = arg.lastIndexOf('.');
        return arg.substring(0 ,index);

    }
    

    // public static List<? extends PojoInterface> findAll(String tenant ,String pojoName){
    //     BDXTenantData bdxTenantData = BDXEnvironment.getInstance().getBdxTenantData(tenant);
    //     return bdxTenantData.getBdxCache().getBdxCacheMap().get(pojoName);
    // }

    // public static PojoInterface find(String tenant ,String pojoName ,long id){
    //     List<? extends PojoInterface> pojoInterfaceList = findAll(tenant ,pojoName);
    //     return DAOQuery.find(pojoInterfaceList ,id);
    // }

    // public static BDXTenantData getTenant(String tenant){
    //     return BDXEnvironment.getInstance().getBdxTenantData(tenant);
    // }


}


