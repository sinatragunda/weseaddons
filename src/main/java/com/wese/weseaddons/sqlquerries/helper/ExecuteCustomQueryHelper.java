package com.wese.weseaddons.sqlquerries.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.daofactory.MapperFactory;
import com.wese.weseaddons.helper.FileHelper;
import com.wese.weseaddons.interfaces.PojoInterface;

import java.util.List;

public class ExecuteCustomQueryHelper {

    public static List<? extends PojoInterface> execute(MapperFactory mapperFactory , ObjectNode objectNodeParams , String sqlFilename , String [] params , String tenantIdentifier){

        String sqlQuery = FileHelper.getInstance().readFileAsResource(sqlFilename,true);
        String newData = ExtractAndReplaceParam.extract(objectNodeParams ,sqlQuery  , params);
        List<? extends PojoInterface> pojoInterfaceList = ExecuteCustomQuery.execute(mapperFactory ,tenantIdentifier ,newData);

        return pojoInterfaceList ;

    }
}
