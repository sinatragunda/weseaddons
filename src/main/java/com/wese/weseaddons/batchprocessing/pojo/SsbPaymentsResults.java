/*

    Created by Sinatra Gunda
    At 4:08 PM on 6/19/2021

*/
package com.wese.weseaddons.batchprocessing.pojo;

import com.wese.weseaddons.batchprocessing.enumerations.PORTFOLIO_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.SSB_REPORT_TYPE;
import com.wese.weseaddons.batchprocessing.init.SSBPaymentsInit;
import com.wese.weseaddons.helper.ComparatorUtility;

import java.util.*;

public class SsbPaymentsResults {

    private Map<SSB_REPORT_TYPE ,List<ExcelClientData>> ssbReportTypeListMap ;

    public SsbPaymentsResults(){
        ssbReportTypeListMap = new HashMap<>();
    }

    public void add(SSB_REPORT_TYPE ssbReportType ,List list){

        boolean isPresent = ssbReportTypeListMap.containsKey(ssbReportType);

        if(isPresent){

            List data  = new ArrayList(ssbReportTypeListMap.get(ssbReportType));
            list = new ArrayList(list);
            list.addAll(data);
            ssbReportTypeListMap.remove(ssbReportType);
        }

        ssbReportTypeListMap.putIfAbsent(ssbReportType ,list);
    }

    /**
     * Added 17/10/2022 at 0359
     * Dont know why we get this function now
     * @return
     */

    public void add(SSB_REPORT_TYPE ssbReportType ,ExcelClientData excelClientData){
        List list = Arrays.asList(excelClientData);
        add(ssbReportType ,list);
    }

    public Map getSSbResults(){
        return ssbReportTypeListMap ;
    }

    public boolean update(SSB_REPORT_TYPE ssbReportType ,ExcelClientData excelClientData ,PORTFOLIO_TYPE portfolioType){

        List<ExcelClientData> list = new ArrayList<>(ssbReportTypeListMap.get(ssbReportType));
        
        int index = 0 ;

        for (ExcelClientData clientData: list) {

            PORTFOLIO_TYPE curPortfolioType = clientData.getPortfolioType();

            if(curPortfolioType==portfolioType){

                Long curResourceId = clientData.getResourceId();
                boolean isTransaction = ComparatorUtility.compareLong(curResourceId ,excelClientData.getResourceId());
                if(isTransaction){
                    // then update list
                    list.add(index ,clientData);
                    // then update current map
                    ssbReportTypeListMap.replace(ssbReportType ,list);
                    //SSBPaymentsInit.getInstance().
                    return true;
                }
            }
            ++index ;
        }
        return false ;
    }

}
