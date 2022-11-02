package com.wese.weseaddons.dashboard.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.dashboard.helper.AgingDetailsHelper;
import com.wese.weseaddons.dashboard.helper.LoansDashboardHelper;
import com.wese.weseaddons.dashboard.pojo.AgingDetailsNormalizedData;
import com.wese.weseaddons.helper.ColorGenerator;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.MathsHelper;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;
import com.wese.weseaddons.sqlquerries.pojo.LoansDistrubution;
import com.wese.weseaddons.sqlquerries.pojo.GenderDistrubution;
import com.wese.weseaddons.sqlquerries.pojo.LoansSector;
import com.wese.weseaddons.sqlquerries.pojo.PortfolioAtRisk;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LoansDashboardAction {


    public static ObjectNode portfolioAtRisk(String tenantIdentifier){

        ObjectNode objectNode = Helper.createObjectNode();
        PortfolioAtRisk portfolioAtRisk = LoansDashboardHelper.portfolioAtRisk(tenantIdentifier) ;

        if(portfolioAtRisk==null){

            objectNode.put("status" ,false);
            objectNode.put("state" ,0);

            return objectNode ;
        }

        objectNode.put("status",true);
        objectNode.put("risk",portfolioAtRisk.getRisk());
        objectNode.put("safe",(100 - portfolioAtRisk.getRisk()));
        objectNode.put("principalOutstanding",portfolioAtRisk.getPrincipalOutstanding());

        return objectNode ;

    }

    public static ObjectNode loansDistrubution(String tenantIdentifier ,long branchId){

        ObjectNode objectNode = Helper.statusNodes(false);
        List<LoansDistrubution> loansDistrubutionList = LoansDashboardHelper.loansDistrubution(tenantIdentifier ,branchId) ;

        if(loansDistrubutionList.isEmpty()){

            return objectNode ;
        }

        ArrayNode arrayNode = Helper.createArrayNode() ;

        int index  = loansDistrubutionList.size();

        objectNode.put("index" ,index);

        for(LoansDistrubution loansDistrubution : loansDistrubutionList){

            ObjectNode objectNode1 = Helper.createObjectNode() ;
            objectNode1.put("id" ,loansDistrubution.getLoanProductId());
            objectNode1.put("loanProductName",loansDistrubution.getProductName());
            objectNode1.put("principal" ,loansDistrubution.getPrincipalAmount());
            objectNode1.put("colorCode" , ColorGenerator.generateColorCode());

            arrayNode.addPOJO(objectNode1);
        }

        objectNode.put("status" ,true);
        objectNode.putPOJO("pageItems" ,arrayNode);

        return objectNode ;


    }

      public static ObjectNode genderDistrubution(String tenantIdentifier ,long branchId){

        ObjectNode objectNode = Helper.statusNodes(false);

        List<GenderDistrubution> genderDistrubutionList = LoansDashboardHelper.genderDistrubution(tenantIdentifier ,branchId) ;

        if(genderDistrubutionList.isEmpty()){

            return objectNode ;
        }

        ArrayNode arrayNode = Helper.createArrayNode() ;

        int index  = genderDistrubutionList.size();
        objectNode.put("index" ,index);

        for(GenderDistrubution genderDistrubution : genderDistrubutionList){

            ObjectNode objectNode1 = Helper.createObjectNode() ;
            objectNode1.put("gender",genderDistrubution.getGender());
            objectNode1.put("count" ,genderDistrubution.getCount());
            objectNode1.put("principal" ,genderDistrubution.getPrincipalAmount());
            objectNode1.put("colorCode" , ColorGenerator.generateColorCode());

            arrayNode.addPOJO(objectNode1);
        }

        objectNode.put("status" ,true);
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;


    }


    public static ObjectNode loansSector(String tenantIdentifier ,long branchId){

        ObjectNode objectNode = Helper.statusNodes(false);

        List<LoansSector> loansSectorList = LoansDashboardHelper.loansSector(tenantIdentifier ,branchId) ;

        if(loansSectorList.isEmpty()){

            return objectNode ;
        }

        ArrayNode arrayNode = Helper.createArrayNode() ;

        int index  = loansSectorList.size();
        objectNode.put("index" ,index);

        for(LoansSector loansSector : loansSectorList){

            ObjectNode objectNode1 = Helper.createObjectNode() ;
            objectNode1.put("loanPurpose",loansSector.getLoanPurpose());
            objectNode1.put("principal" ,loansSector.getPrincipalAmount());
            objectNode1.put("colorCode" , ColorGenerator.generateColorCode());

            arrayNode.addPOJO(objectNode1);
        }

        objectNode.put("status" ,true);
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;


    }

    public static ObjectNode agingDetails(String tenantIdentifier){

        ObjectNode objectNode = Helper.statusNodes(false);
        List<AgingDetails> agingDetailsList = LoansDashboardHelper.agingDetails(tenantIdentifier) ;

        if(agingDetailsList.isEmpty()){

            return objectNode ;
        }

        ArrayNode arrayNode = Helper.createArrayNode() ;

        Map<Integer ,AgingDetailsNormalizedData> agingDetailsNormalizedDataMap = AgingDetailsHelper.normalizeData(agingDetailsList);

        int index  = agingDetailsNormalizedDataMap.size();

        objectNode.put("index" ,index);

        for(Map.Entry entry : agingDetailsNormalizedDataMap.entrySet()){

            AgingDetailsNormalizedData agingDetailsNormalizedData = (AgingDetailsNormalizedData) entry.getValue();


            double sum = MathsHelper.simpleRound(agingDetailsNormalizedData.getSum());
            double average = MathsHelper.simpleRound(agingDetailsNormalizedData.getAverage());

            ObjectNode objectNode1 = Helper.createObjectNode() ;
            objectNode1.put("label" ,agingDetailsNormalizedData.getAgingDetailsEnum().getCode());
            objectNode1.put("average",average);
            objectNode1.put("sum" ,sum);

            arrayNode.addPOJO(objectNode1);
        }

        objectNode.put("status" ,true);
        objectNode.putPOJO("pageItems" ,arrayNode);
        return objectNode ;


    }
}
