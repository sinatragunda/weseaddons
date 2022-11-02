package com.wese.weseaddons.weselicense.action;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.weselicense.dao.TarrifDAO;
import com.wese.weseaddons.weselicense.enumeration.TIME_UNIT_TYPE;
import com.wese.weseaddons.weselicense.helper.ObjectNodeBuilder;
import com.wese.weseaddons.weselicense.pojo.Tarrif;

import java.util.List;

public class TarrifAction {


    private TarrifDAO tarrifDAO ;
    private List<Tarrif> tarrifList ;


    public TarrifAction(){
        init();
    }


    public void init(){

        this.tarrifDAO = new TarrifDAO();

    }

    public ObjectNode getAllTarrifs(){

        this.tarrifList = tarrifDAO.getAllTarrifs();

        if(tarrifList.isEmpty()){
            return Helper.statusNodes(false);
        }

        ArrayNode arrayNode = Helper.createArrayNode();

        for(Tarrif tarrif : tarrifList){

            arrayNode.addPOJO(tarrifNode(tarrif));

        }

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("pageItems",arrayNode);
        return objectNode ;

    }

    public ObjectNode getTarrif(long id){


        Tarrif tarrif = tarrifDAO.getTarrif(id);

        if(tarrif==null){

            return Helper.statusNodes(false);
        }


        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.putPOJO("tarrif" ,tarrifNode(tarrif));
        return objectNode ;

    }

    public ObjectNode tarrifNode(Tarrif tarrif){
        TIME_UNIT_TYPE timeUnitType = tarrif.getTimeUnitType();

        ObjectNode objectNode = Helper.createObjectNode();
        objectNode.put("id",tarrif.getId());
        objectNode.put("name",tarrif.getName());

        objectNode.put("amount",tarrif.getAmount());
        objectNode.put("timeUnit",tarrif.getTimeUnit());
        objectNode.put("timeUnitType",timeUnitType.toString());

        return objectNode ;
    }
}
