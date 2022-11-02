package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.logger.Logger;
import com.wese.weseaddons.weselicense.action.BillAction;
import com.wese.weseaddons.weselicense.action.LicenseAction;
import com.wese.weseaddons.weselicense.action.ServiceAction;
import com.wese.weseaddons.weselicense.action.TarrifAction;
import com.wese.weseaddons.weselicense.body.BillRequestBody;
import com.wese.weseaddons.weselicense.body.TenantRequestBody;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value ="/weselicense")
public class LicenseController {


    @RequestMapping(value="/checkstatus" ,method = RequestMethod.POST)
    public ObjectNode checkLicenseStatus(@RequestBody TenantRequestBody tenantRequestBody){

        //Logger.log("We have entered check license class now\n");
        LicenseAction licenseAction = new LicenseAction(tenantRequestBody);
        return licenseAction.checkLicenseStatus();

    }

    @RequestMapping(value="/tariffs" ,method = RequestMethod.GET)
    public ObjectNode getAllTarrifs(){

        //Logger.log("We have entered check license class now\n");

        TarrifAction tarrifAction = new TarrifAction();
        return tarrifAction.getAllTarrifs();

    }

    @RequestMapping(value="/services" ,method = RequestMethod.GET)
    public ObjectNode getAllServices(){

        //Logger.log("We have entered check license class now\n");

        ServiceAction serviceAction = new ServiceAction();
        return serviceAction.getServices();
    }


    @RequestMapping(value="/createbill" ,method = RequestMethod.POST)
    public ObjectNode createBill(@RequestBody BillRequestBody billRequestBody){

        BillAction billAction = new BillAction(billRequestBody);
        return billAction.createBill();

    }

}
