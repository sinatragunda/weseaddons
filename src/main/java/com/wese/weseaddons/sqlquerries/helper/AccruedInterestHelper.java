/*Created by Sinatra Gunda
  At 08:32 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.daofactory.MapperFactory;
import com.wese.weseaddons.helper.FileHelper;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.Parameters;
import com.wese.weseaddons.sqlquerries.mapper.AccruedInterestMapper;
import com.wese.weseaddons.sqlquerries.mapper.PortfolioAtRiskMapper;
import com.wese.weseaddons.sqlquerries.params.SqlParams;
import com.wese.weseaddons.sqlquerries.pojo.AccruedInterest;
import com.wese.weseaddons.sqlquerries.pojo.PortfolioAtRisk;

import java.util.List;

public class AccruedInterestHelper {

    public static String interestAccruedQuery  = null ;

    public static Double getAccruedInterest(String tenantIdentifier ,Long loanId){


        ObjectNode params = Helper.createObjectNode();
        params.put("loanId" ,loanId);

        if(interestAccruedQuery==null){
            interestAccruedQuery = FileHelper.getInstance().readFileAsResource("AccruedInterest.sql",true);
        }

        String newData = ExtractAndReplaceParam.extract(params ,interestAccruedQuery , SqlParams.accruedInterest);

        MapperFactory mapperFactory = new MapperFactory(new AccruedInterestMapper());
        List<AccruedInterest> accruedInterestList = (List<AccruedInterest>) ExecuteCustomQuery.execute(mapperFactory ,tenantIdentifier ,newData);

        if(accruedInterestList.isEmpty()){
            return null ;
        }

        Double totalAccrued =  accruedInterestList.stream().mapToDouble(AccruedInterest::getAccruedInterest).sum();
        return totalAccrued ;
    }

}
