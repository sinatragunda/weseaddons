package com.wese.weseaddons.assetregister.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.assetregister.enumeration.ASSET_REGISTER_NODES;
import com.wese.weseaddons.assetregister.helper.AssetRegisterNodesFactory;
import com.wese.weseaddons.assetregister.pojo.AssetClass;
import com.wese.weseaddons.daofactory.DAOQuery;
import com.wese.weseaddons.helper.ObjectNodeHelper;
import com.wese.weseaddons.interfaces.ExecutableClass;
import com.wese.weseaddons.interfaces.ListingFamily;
import com.wese.weseaddons.interfaces.PojoInterface;

import java.util.List;

public class NodeListingAction {

    public ObjectNode getList(int type){

        ASSET_REGISTER_NODES assetRegisterNodes = ASSET_REGISTER_NODES.fromInt(type);
        ListingFamily listingFamily = AssetRegisterNodesFactory.getAssetNodes(assetRegisterNodes);

        List<? extends ExecutableClass> executableClassList = DAOQuery.findAll((PojoInterface)listingFamily ,"altus");
        return ObjectNodeHelper.array((List<ExecutableClass>)executableClassList ,listingFamily);

    }


}
