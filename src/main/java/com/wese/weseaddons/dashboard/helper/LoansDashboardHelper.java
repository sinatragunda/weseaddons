package com.wese.weseaddons.dashboard.helper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.daofactory.MapperFactory;
import com.wese.weseaddons.helper.FileHelper;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.Parameters;

import com.wese.weseaddons.sqlquerries.helper.ExecuteCustomQuery;
import com.wese.weseaddons.sqlquerries.helper.ExecuteCustomQueryHelper;
import com.wese.weseaddons.sqlquerries.helper.ExtractAndReplaceParam;
import com.wese.weseaddons.sqlquerries.mapper.AgingDetailsMapper;
import com.wese.weseaddons.sqlquerries.mapper.LoansDistrubutionMapper;
import com.wese.weseaddons.sqlquerries.pojo.LoansDistrubution;
import com.wese.weseaddons.sqlquerries.mapper.GenderDistrubutionMapper;
import com.wese.weseaddons.sqlquerries.pojo.GenderDistrubution;
import com.wese.weseaddons.sqlquerries.mapper.LoansSectorMapper;
import com.wese.weseaddons.sqlquerries.pojo.LoansSector;
import com.wese.weseaddons.sqlquerries.pojo.PortfolioAtRisk;
import com.wese.weseaddons.sqlquerries.mapper.PortfolioAtRiskMapper;
import com.wese.weseaddons.sqlquerries.params.SqlParams;
import com.wese.weseaddons.sqlquerries.pojo.AgingDetails;

import java.util.List;

public class LoansDashboardHelper {


    public static PortfolioAtRisk portfolioAtRisk(String tenantIdentifier){

        Parameters parameters = new Parameters();
        parameters.setTenantIdentifier(tenantIdentifier);

        String portfolioRiskQuery  = FileHelper.getInstance().readFileAsResource("PortfolioAtRisk.sql",true);
  
        ObjectNode params = Helper.createObjectNode();
        params.put("currencyId" ,30);
        params.put("fundId" ,-1);
        params.put("loanPurposeId" ,-1);
        params.put("branch" ,2);
        params.put("loanProductId" ,-1);
        params.put("loanOfficer" ,-1);
        params.put("parType" ,4);

        String newData = ExtractAndReplaceParam.extract(params ,portfolioRiskQuery , SqlParams.portfolioAtRisk);

        MapperFactory mapperFactory = new MapperFactory(new PortfolioAtRiskMapper());

        List<PortfolioAtRisk> portfolioAtRiskList = (List<PortfolioAtRisk>) ExecuteCustomQuery.execute(mapperFactory ,tenantIdentifier ,newData);

        if(portfolioAtRiskList.isEmpty()){

            return null ;
        }

        return portfolioAtRiskList.get(0);


    }

    public static List<AgingDetails> agingDetails(String tenantIdentifier){

        String agingDetailQuery  = FileHelper.getInstance().readFileAsResource("AgingDetail.sql",true);
  
        ObjectNode params = Helper.createObjectNode();
        params.put("branch" ,2);

        String newData = ExtractAndReplaceParam.extract(params ,agingDetailQuery , SqlParams.agingDetails);

        MapperFactory mapperFactory = new MapperFactory(new AgingDetailsMapper());

        List<AgingDetails> agingDetailList = (List<AgingDetails>) ExecuteCustomQuery.execute(mapperFactory ,tenantIdentifier ,newData);

        if(agingDetailList.isEmpty()){

            return null ;
        }

        return agingDetailList;

    }

    public static List<AgingDetails> agingDetailsClient(String tenantIdentifier ,long loanId){

        String agingDetailQuery  = FileHelper.getInstance().readFileAsResource("AgingDetailClient.sql",true);
  
        ObjectNode params = Helper.createObjectNode();
        params.put("loanId" ,loanId);

        String newData = ExtractAndReplaceParam.extract(params ,agingDetailQuery , SqlParams.agingDetails);

        MapperFactory mapperFactory = new MapperFactory(new AgingDetailsMapper());

        List<AgingDetails> agingDetailList = (List<AgingDetails>) ExecuteCustomQuery.execute(mapperFactory ,tenantIdentifier ,newData);

        if(agingDetailList.isEmpty()){

            return null ;
        }

        return agingDetailList;

    }

    public static List<LoansDistrubution> loansDistrubution(String tenantIdentifier ,long branchId){


        String filename = "LoansDistrubution.sql";

        ObjectNode params = Helper.createObjectNode();
        params.put("branch" ,branchId);

        MapperFactory mapperFactory = new MapperFactory(new LoansDistrubutionMapper());

        List<LoansDistrubution> loansDistrubutionList = (List<LoansDistrubution>) ExecuteCustomQueryHelper.execute(mapperFactory , params ,filename ,SqlParams.loansDistrubution, tenantIdentifier);

        if(loansDistrubutionList.isEmpty()){

            return null ;
        }

        return loansDistrubutionList;

    }


    public static List<LoansSector> loansSector(String tenantIdentifier ,long branchId){

        String filename = "LoansSector.sql";
        ObjectNode params = Helper.createObjectNode();
        params.put("branch" ,branchId);

        MapperFactory mapperFactory = new MapperFactory(new LoansSectorMapper());

        List<LoansSector> loansSectorList = (List<LoansSector>) ExecuteCustomQueryHelper.execute(mapperFactory , params ,filename ,SqlParams.loansDistrubution, tenantIdentifier);

        if(loansSectorList.isEmpty()){

            return null ;
        }

        return loansSectorList;

    }


    public static List<GenderDistrubution> genderDistrubution(String tenantIdentifier ,long branchId){

        String filename = "GenderDistrubution.sql";
        ObjectNode params = Helper.createObjectNode();
        params.put("branch" ,branchId);

        MapperFactory mapperFactory = new MapperFactory(new GenderDistrubutionMapper());

        List<GenderDistrubution> genderDistrubutionList = (List<GenderDistrubution>) ExecuteCustomQueryHelper.execute(mapperFactory , params ,filename ,SqlParams.loansDistrubution, tenantIdentifier);

        if(genderDistrubutionList.isEmpty()){

            return null ;
        }

        return genderDistrubutionList;

    }

}
