package com.wese.weseaddons.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.wese.weseaddons.ussd.exceptions.ArrayBoundsException;
import com.wese.weseaddons.ussd.exceptions.JsonExceptions;
import com.wese.weseaddons.ussd.helper.Constants;

@RestControllerAdvice
@CrossOrigin
public class UssdExceptionAdvice{


    @ExceptionHandler(value = JsonExceptions.class)
    public ModelAndView handleJsonException(JsonExceptions jsonExceptions){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message" ,jsonExceptions.getMessage());
       // modelAndView.setViewName(Constants.genericError);

        return modelAndView ;
    }


    @ExceptionHandler(value = ArrayBoundsException.class)
    public ResponseEntity<Object> handleArrayBoundsException(ArrayBoundsException arrayBoundsException){
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }
}
