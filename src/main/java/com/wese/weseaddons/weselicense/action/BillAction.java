package com.wese.weseaddons.weselicense.action;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.weselicense.body.BillRequestBody;
import com.wese.weseaddons.weselicense.dao.BillDAO;
import com.wese.weseaddons.weselicense.dao.ServiceDAO;
import com.wese.weseaddons.weselicense.enumeration.BILL_STATE;
import com.wese.weseaddons.weselicense.logic.ServiceBuilder;
import com.wese.weseaddons.weselicense.pojo.Bill;
import com.wese.weseaddons.weselicense.pojo.Service;
import com.wese.weseaddons.weselicense.pojo.Tarrif;
import com.wese.weseaddons.weselicense.pojo.Tenant;

public class BillAction {

    private BillRequestBody billRequestBody ;

    public BillAction(BillRequestBody billRequestBody){
        this.billRequestBody = billRequestBody ;
    }

    public ObjectNode createBill(){

        int duration = billRequestBody.getDuration();

        Tenant tenant = billRequestBody.getTenant();

        Service service = billRequestBody.getService();
        Tarrif tarrif = billRequestBody.getTarrif();

        ServiceBuilder serviceBuilder = new ServiceBuilder(tarrif ,service.getServiceType() ,duration);
        double amount = serviceBuilder.makeService().getBill();

        long timestampDate = TimeHelper.stringToTimestamp(billRequestBody.getDate());

        ServiceDAO serviceDAO = new ServiceDAO();
        long serviceId = serviceDAO.createService(service);

        if(serviceId == 0){

            return Helper.statusNodes(false);
        }

        service.setId(serviceId);

        Bill bill = new Bill(tenant, service, amount, BILL_STATE.RECEIPTED, timestampDate ,duration);

        BillDAO billDAO = new BillDAO();
        long billId = billDAO.createBill(bill);


        if(billId !=0){

            bill.setId(billId);
            return billNode(bill);
        }

        return Helper.statusNodes(false);
    }


    public ObjectNode billNode(Bill bill){

        ObjectNode objectNode = Helper.statusNodes(true);
        objectNode.put("id",bill.getId());
        objectNode.put("amount",bill.getAmount());
        objectNode.put("service",bill.getService().getServiceType().toString());
        objectNode.put("tenant",bill.getTenant().getTenantName());
        objectNode.put("state",bill.getBillState().toString());
       // objectNode.put("serviceDescription",bill.getService().getDescription());

        String date = TimeHelper.timestampToString(bill.getDate());

        objectNode.put("date",date);
        return objectNode ;
    }
}
