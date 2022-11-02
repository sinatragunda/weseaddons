/*Created by Sinatra Gunda
  At 09:13 on 2/16/2021 */

package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.Helper;
import com.wese.weseaddons.helper.ObjectNodeHelper;
import com.wese.weseaddons.sqlquerries.helper.BulkReportHelper;
import com.wese.weseaddons.sqlquerries.xlsreports.XlsReportsAction;
import com.wese.weseaddons.utility.CompletableFutureEx;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;



//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.StreamingOutput;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@RestController
@CrossOrigin
@RequestMapping(value ="/runreports")
public class XlsReportsController {

//
//    @RequestMapping(method = RequestMethod.POST ,produces = MediaType.APPLICATION_OCTET_STREAM)
//    public @ResponseBody HttpServletResponse runReport(@RequestBody String request ,HttpServletResponse response){
//
//        InputStream inputStream = null ;
//
//        try {
//            File file = XlsReportsAction.runReport(request);
//            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
//            inputStream = new BufferedInputStream(fileInputStream);
//
//            // Set the content type and attachment header.
//            String attachement = String.format("attachment;filename=%s", file.getName());
//            //response.addHeader("Content-disposition", attachement);
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition", String.format("attachment; filename=%s", file.getName()));
//
//
//            // Copy the stream to the response's output stream.
//            FileCopyUtils.copy(inputStream, response.getOutputStream());
//            //file.delete();
//            return response;
//        }
//
//        catch (Exception r){
//            r.printStackTrace();
//        }
//        return null ;
//    }


    @RequestMapping(method = RequestMethod.POST)
    public ObjectNode runReport(@RequestBody String request){

        Callable callable = XlsReportsAction.runReportEx(request);
        String json = BulkReportHelper.runBulkReport(callable).toString();
        return ObjectNodeHelper.objectNodeFromString(json);

    }



    @RequestMapping(method = RequestMethod.GET ,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody HttpServletResponse queryReport(@RequestParam("referenceId") final String referenceId , HttpServletResponse response) {

        //int reportStatus = BulkReportHelper.bulkReportStatus(referenceId);
        Boolean reportStatus = CompletableFutureEx.getInstance().isDone(referenceId);

        if(reportStatus==null){
            //"Invalid reference number or request has been flushed out of the system .Run a new report";
            //ObjectNode objectNode = ObjectNodeHelper.statusNodes(false);
            //objectNode.put("message",");
            String message = "Invalid reference passed or reference been flushed out of system .Please try to rerun report";
            printStream(response ,message ,301);
            return null ;
            //return ResponseEntity.ok().body(objectNode);
        }

        if(reportStatus){

            ///report done processing now lets build output son
            //Future future = BulkReportHelper.getBulkReport(referenceId);
            Future future = CompletableFutureEx.getInstance().get(referenceId);
            InputStream inputStream = null ;
            String reportName = null ;

            try{
                File file = (File) future.get();
                reportName = file.getName();
                FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
                inputStream = new BufferedInputStream(fileInputStream);

                String attachement = String.format("attachment;filename=%s", file.getName());
            //response.addHeader("Content-disposition", attachement);
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", String.format("attachment; filename=%s", file.getName()));


            // Copy the stream to the response's output stream.
                FileCopyUtils.copy(inputStream, response.getOutputStream());
                response.setContentLength(inputStream.available());
            //file.delete();

                return response ;

            }

            catch(Exception e){
                e.printStackTrace();
            }

        }

        //request still being processed son
        String message = "Report is still being processed ,please wait";
        printStream(response ,message ,300);

        return null  ;
    }

    public static void printStream(HttpServletResponse response , String message,int status){
        
        try{
            ObjectNode objectNode = ObjectNodeHelper.statusNodes(false);
            objectNode.put("message" ,message);
            response.setStatus(status);
            response.getOutputStream().print(objectNode.toString());
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }



}
