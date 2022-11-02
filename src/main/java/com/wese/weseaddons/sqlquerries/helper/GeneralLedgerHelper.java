/*Created by Sinatra Gunda
  At 03:56 on 2/17/2021 */

package com.wese.weseaddons.sqlquerries.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.daofactory.MapperFactory;
import com.wese.weseaddons.errors.springexceptions.SqlQueryEmptyResultsException;
import com.wese.weseaddons.errors.springexceptions.SqlQueryParameterNotFound;
import com.wese.weseaddons.helper.FileHelper;
import com.wese.weseaddons.sqlquerries.mapper.ActiveLoansMapper;
import com.wese.weseaddons.sqlquerries.mapper.GeneralLedgerMapper;
import com.wese.weseaddons.sqlquerries.params.SqlParams;
import com.wese.weseaddons.sqlquerries.pojo.ActiveLoans;
import com.wese.weseaddons.sqlquerries.pojo.GeneralLedger;
import com.wese.weseaddons.helper.TimeHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GeneralLedgerHelper {


    public List<GeneralLedger> getGeneralLedger(ObjectNode params){

        String tenantIdentifier = params.get("tenantIdentifier").asText();
        String generalLedgerQuery  = FileHelper.getInstance().readFileAsResource("GeneralLedger.sql",true);

        String newData = ExtractAndReplaceParam.extract(params ,generalLedgerQuery , SqlParams.generalLedger);

        MapperFactory mapperFactory = new MapperFactory(new GeneralLedgerMapper());
        List<GeneralLedger> generalLedgerList = (List<GeneralLedger>) ExecuteCustomQuery.execute(mapperFactory ,tenantIdentifier ,newData);

        if(generalLedgerList.isEmpty()){
            return new ArrayList<>();
        }
        return generalLedgerList ;
    }


    public static Map<Integer ,Object[]> xssfRows(List<GeneralLedger> generalLedgerList,ObjectNode params){

        Map<Integer ,Object[]> map = new TreeMap<>();

        String startDate = params.get("R_startDate").asText();
        String endDate = params.get("R_endDate").asText();

        GeneralLedger generalLedger = null ;

        if(generalLedgerList.isEmpty()) throw new SqlQueryEmptyResultsException();

        generalLedger = generalLedgerList.get(0);

        int count = 0 ;
        map.put(count++ ,new Object[]{"General Ledger Report"});
        map.put(count++ ,new Object[]{"Report Run From ",String.format("%s to %s",startDate ,endDate)});
        map.put(count++ ,new Object[]{"Office :" ,generalLedger.getBranchName()});
        map.put(count++ ,new Object[]{"Run Date :" ,TimeHelper.dateNow()});

        map.put(count++ ,new Object[]{"Balance Brought Forwad",generalLedger.getOpeningBalance()});
        map.put(count++ ,new Object[]{"Date" ,"Account Description","Transaction Id","Description","Debits", "Credits","Balance"});

        for(GeneralLedger g : generalLedgerList){
            map.put(++count ,new Object[]{g.getEntryDate() ,g.getAccountName() ,g.getTransactionId() ,g.getDescription() ,g.getDebitAmount() ,g.getCreditAmount() ,g.getAfterTxn()});
        }

        return map ;

    }
}
