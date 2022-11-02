

package com.wese.weseaddons.assetregister.methods ;

import com.wese.weseaddons.assetregister.interfaces.DepreciationMethod;
import com.wese.weseaddons.assetregister.pojo.AssetCosts;
import com.wese.weseaddons.assetregister.pojo.StraightLine;

import java.util.ArrayList;
import java.util.List;

public class StraightLineMethod implements DepreciationMethod{


    private AssetCosts assetCosts ;


    public StraightLineMethod(AssetCosts assetCosts){
        this.assetCosts = assetCosts ;
    }


    @Override
    public List<StraightLine> depreciation(){

        int duration = assetCosts.getDuration() ;
        int index = 1 ;

        double annualExpense = annualExpense();
        double assetCost = assetCosts.getCostOfAsset() ;

        List<StraightLine> straightLineList = new ArrayList<>(duration);

        for(StraightLine straightLine : straightLineList){

            double accumulatedDepreciation = annualExpense * index ;
            double bookValue = assetCost - accumulatedDepreciation ;
            straightLine = new StraightLine(annualExpense ,accumulatedDepreciation ,bookValue);
            ++index ;
        }

        return  straightLineList ;

    }


    public double annualExpense(){

        double costOfFixedAssets = assetCosts.getCostOfAsset() ;
        double residualValue = assetCosts.getResidualValue();
        int duration = assetCosts.getDuration();

        double expense = (costOfFixedAssets - residualValue) /duration ;

        return expense ;
    }
}