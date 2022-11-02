package com.wese.weseaddons.controller;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.helper.ObjectNodeHelper;
import com.wese.weseaddons.taskmanager.actions.TaskOverview;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public ObjectNode test(){

        //read html file and post it back to html
        String test = "This is a test view";
        String page = "<div> <h3> Welcome to our Core Banking server rendered page ,cross origin and server access test</h3></div>";
        return ObjectNodeHelper.statusNodes(true).put("message",test).put("view" ,page);
    }
}
