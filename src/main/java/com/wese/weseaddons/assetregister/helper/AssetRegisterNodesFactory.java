package com.wese.weseaddons.assetregister.helper;

import com.wese.weseaddons.assetregister.enumeration.ASSET_REGISTER_NODES;
import com.wese.weseaddons.assetregister.pojo.AssetCategory;
import com.wese.weseaddons.assetregister.pojo.AssetClass;
import com.wese.weseaddons.assetregister.pojo.InputErrorCode;
import com.wese.weseaddons.assetregister.pojo.TaxCode;
import com.wese.weseaddons.interfaces.ListingFamily;

public class AssetRegisterNodesFactory {


    public static ListingFamily getAssetNodes(ASSET_REGISTER_NODES assetRegisterNodes){

        if(assetRegisterNodes==null){
            return null ;
        }

        ListingFamily listingFamily = null ;

        switch (assetRegisterNodes){
            case ASSET_CLASSES:
                listingFamily = new AssetClass();
                break;

            case ASSET_CATEGORIES:
                listingFamily = new AssetCategory();
                break;

            case INPUT_ERROR_CODE:
                listingFamily = new InputErrorCode();
                break;

            case TAX_CODE:
                listingFamily = new TaxCode();
                break;
        }

        return listingFamily ;

    }
}
