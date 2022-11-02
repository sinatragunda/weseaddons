/*Created by Sinatra Gunda
  At 15:49 on 9/3/2020 */

package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.enumerations.PROCESS_STATUS;
import com.wese.weseaddons.batchprocessing.helper.ForeclosureHelper;
import com.wese.weseaddons.batchprocessing.helper.ResponseBuilder;
import com.wese.weseaddons.batchprocessing.init.SSBPaymentsInit;
import com.wese.weseaddons.batchprocessing.pojo.Foreclosure;
import com.wese.weseaddons.batchprocessing.workbook.ForeclosureWorkbook;
import com.wese.weseaddons.helper.Helper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value ="/batchprocess")
public class BatchProcessorExController {

    @RequestMapping(value ="/foreclosure" ,method = RequestMethod.POST ,consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ObjectNode foreclose(MultipartFile file , @RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("note")String note ,@RequestParam("transactionDate")String transactionDate){

        Foreclosure foreclosure = new Foreclosure(note ,transactionDate);

//        int validateDate = Constants.validateDate(foreclosure.getTransactionDate());
//
//        if(validateDate != 1){
//            SystemInit.getInstance().updateProcessStatus(tenantIdentifier, PROCESS_STATUS.INVALID_DATE);
//            return ResponseBuilder.invalidDate(validateDate);
//        }

        SSBPaymentsInit.getInstance().addNewProcess(tenantIdentifier);
        InputStream inputStream ;
        ForeclosureWorkbook foreclosureWorkbook = null ;
        List<Foreclosure> foreclosureList = new ArrayList<>();

        try{

            inputStream = file.getInputStream();
            foreclosureWorkbook = new ForeclosureWorkbook(inputStream);
            foreclosureWorkbook.readFile(0);

        }

        catch (IOException e){

            //// some exception here
            SSBPaymentsInit.getInstance().updateProcessStatus(tenantIdentifier, PROCESS_STATUS.FAILED);
            return ResponseBuilder.genericFailure("Failed to read the data file ,make sure file is a valid excel file");

        }


        foreclosureList = foreclosureWorkbook.getForeclosureList();
        ForeclosureHelper foreclosureHelper = new ForeclosureHelper(tenantIdentifier ,foreclosure);
        foreclosureList.stream().forEach(foreclosureHelper.forecloseConsumer);

        ObjectNode objectNode = Helper.statusNodes(true);

        objectNode.putPOJO("pageItems", foreclosureHelper.results());

        return objectNode ;

    }

}
