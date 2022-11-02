package com.wese.weseaddons.assetregister.methods;

import com.wese.weseaddons.assetregister.interfaces.DepreciationMethod;
import com.wese.weseaddons.assetregister.pojo.AssetCosts;
import com.wese.weseaddons.assetregister.pojo.StraightLine;
import com.wese.weseaddons.assetregister.pojo.UnitsOfProduction;

import java.util.ArrayList;
import java.util.List;

public class UnitsOfProductionMethod implements DepreciationMethod{

    private AssetCosts assetCosts ;


    public UnitsOfProductionMethod(AssetCosts assetCosts) {
        this.assetCosts = assetCosts;
    }


    @Override
    public List<UnitsOfProduction> depreciation(){

        int duration = assetCosts.getDuration() ;
        int index = 1 ;

        double depreciationCostPerUnit = depreciationCostPerUnit() ;
        double assetCost = assetCosts.getCostOfAsset() ;


        List<UnitsOfProduction> unitsOfProductionList = new ArrayList<>(duration);

        for(UnitsOfProduction u : unitsOfProductionList){

            double accumulatedDepreciation = 0 ;
            double bookValue = assetCost - accumulatedDepreciation ;

            u = new UnitsOfProduction();
        }


        return unitsOfProductionList ;

    }

    public double depreciationCostPerUnit(){

        double costOfFixedAssets = assetCosts.getCostOfAsset() ;
        double residualValue = assetCosts.getResidualValue();
        double estimatedProduction = assetCosts.getEstimatedTotalProduction();
        double actualProduction = assetCosts.getActualProduction();

        double expense = ((costOfFixedAssets - residualValue) /estimatedProduction) * actualProduction;

        return expense;
    }
}
