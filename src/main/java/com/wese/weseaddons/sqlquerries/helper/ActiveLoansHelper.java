/*Created by Sinatra Gunda
  At 08:25 on 2/16/2021 */

package com.wese.weseaddons.sqlquerries.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.daofactory.MapperFactory;
import com.wese.weseaddons.helper.FileHelper;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.sqlquerries.mapper.AccruedInterestMapper;
import com.wese.weseaddons.sqlquerries.mapper.ActiveLoansMapper;
import com.wese.weseaddons.sqlquerries.params.SqlParams;
import com.wese.weseaddons.sqlquerries.pojo.AccruedInterest;
import com.wese.weseaddons.sqlquerries.pojo.ActiveLoans;
import javafx.beans.binding.ObjectExpression;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActiveLoansHelper {


    private String tenantIdentifier ;


    private Consumer<ActiveLoans> interestRecievableConsumer = (e)->{

        Long loanId = e.getLoanId();

        Double accruedInterest  = AccruedInterestHelper.getAccruedInterest(tenantIdentifier ,loanId);
        Double interestRecievable = accruedInterest - e.getInterestPaid() ;
        e.setAccruedInterest(accruedInterest);
        e.setInterestRecievable(interestRecievable);

    };

    public List<ActiveLoans> getActiveLoans(ObjectNode params){

        this.tenantIdentifier = params.get("tenantIdentifier").asText();
        String activeLoansQuery  = FileHelper.getInstance().readFileAsResource("ActiveLoans.sql",true);
        String newData = ExtractAndReplaceParam.extract(params ,activeLoansQuery , SqlParams.activeLoans);
        MapperFactory mapperFactory = new MapperFactory(new ActiveLoansMapper());

        List<ActiveLoans> activeLoansList = (List<ActiveLoans>) ExecuteCustomQuery.execute(mapperFactory ,tenantIdentifier ,newData);

        if(activeLoansList.isEmpty()){
            return null ;
        }

        activeLoansList.stream().forEach(interestRecievableConsumer);
        return activeLoansList ;
    }

    public static Map<Integer ,Object[]> xssfRows(List<ActiveLoans> activeLoansList){

        Map<Integer ,Object[]> map = new TreeMap<>();

        int count = 1 ;
        map.put(count ,new Object[]{"Office Name","Employer Name","Loan Officer","Customer Name","Currency","Loan Account Number","Disbursed On","Mature On","Principal","Annual Nominal Interest Rate","Interest Accrued","Interest Paid", "Interest Recievable" ,"Interest Outstanding","Interest Overdue","Principal Repaid","Principal Outstanding","Principal Overdue","Fees Repaid","Fees Outstanding","Fees Overdue","Penalties Repaid", "Penalties Outstanding","Penalties Overdue","Total Due","Aging Brack"});

        for(ActiveLoans a : activeLoansList){
            map.put(++count ,new Object[]{a.getOfficeName() ,a.getEmployerName() ,a.getLoanOfficer() ,a.getName() ,  a.getCurrency() ,a.getLoanAccountNumber() ,a.getDisbursedOn().toString(),a.getMaturedOn().toString(),a.getPrincipalAmount(),a.getInterestRate() , a.getAccruedInterest() ,a.getInterestPaid() ,a.getInterestRecievable() ,a.getInterestOutstanding(),a.getInterestOverdue(), a.getPrincipalPaid(),a.getPrincipalOutstanding() ,a.getPrincpalOverdue() ,a.getPrincpalOverdue() ,a.getFeesRepaid() ,a.getFeesOutstanding() ,a.getFeesOverdue(),a.getPenaltiesRepaid() ,a.getPenaltiesOutstanding() ,a.getPenaltiesDue() ,a.getTotalDue() ,a.getAgingBrack()});
        }

        return map ;

    }



}
