/*Created by Sinatra Gunda
  At 3:09 AM on 2/25/2020 */

package com.wese.weseaddons.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.bereaudechange.exceptions.ClientNotRegisteredToService;
import com.wese.weseaddons.bereaudechange.exceptions.FxDealSumsException;
import com.wese.weseaddons.crb.error.CrbException;
import com.wese.weseaddons.crb.error.CrbFailedToCollectDataException;
import com.wese.weseaddons.crb.error.NotSubscribedToCrbException;
import com.wese.weseaddons.crb.pojo.CreditApplicationRecord;
import com.wese.weseaddons.errors.springexceptions.*;
import com.wese.weseaddons.helper.ObjectNodeHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
@CrossOrigin
public class ExceptionAdviceController {


    @ExceptionHandler(value = FxDealSumsException.class)
    public ObjectNode fxDealSums(FxDealSumsException fxDealSumsException){
        return ObjectNodeHelper.controllerAdviceError(fxDealSumsException);
    }

    @ExceptionHandler(value = ClientNotRegisteredToService.class)
    public ObjectNode clientNotRegistered(ClientNotRegisteredToService clientNotRegisteredToService){
        return ObjectNodeHelper.controllerAdviceError(clientNotRegisteredToService);
    }


    @ExceptionHandler(value = NetworkConnectionException.class)
    public ObjectNode errorConnection(NetworkConnectionException networkConnectionException){
        return ObjectNodeHelper.controllerAdviceError(networkConnectionException);
    }


    @ExceptionHandler(value = LoanNotFoundException.class)
    public ObjectNode loanNotFound(LoanNotFoundException loanNotFoundException){
        return ObjectNodeHelper.controllerAdviceError(loanNotFoundException);
    }


    @ExceptionHandler(value = ClientNotFoundException.class)
    public ObjectNode clientNotFound(ClientNotFoundException clientNotFoundException){
        return ObjectNodeHelper.controllerAdviceError(clientNotFoundException);
    }


    @ExceptionHandler(value = CrbException.class)
    public ObjectNode crbExeption(CrbException crbException){

        return ObjectNodeHelper.controllerAdviceError(crbException);
    }

    @ExceptionHandler(value = NotSubscribedToCrbException.class)
    public ObjectNode notSubscribedToCrbException(NotSubscribedToCrbException notSubscribedToCrbException){
        return ObjectNodeHelper.controllerAdviceError(notSubscribedToCrbException);
    }
    
    @ExceptionHandler(value = CrbFailedToCollectDataException.class)
    public ObjectNode crbFailedToLoadDataException(CrbFailedToCollectDataException crbFailedToCollectDataException){
        return ObjectNodeHelper.controllerAdviceError(crbFailedToCollectDataException);
    }

    @ExceptionHandler(value = EmailAddressNotFound.class)
    public ObjectNode emailAddressNotFound(EmailAddressNotFound emailAddressNotFound){
        return ObjectNodeHelper.controllerAdviceError(emailAddressNotFound);
    }


    @ExceptionHandler(value = AppUserNotFound.class)
    public ObjectNode appUserNotFound(AppUserNotFound appUserNotFound){
        return ObjectNodeHelper.controllerAdviceError(appUserNotFound);
    }

    @ExceptionHandler(value = CreatingDataException.class)
    public ObjectNode creatingDataException(CreatingDataException creatingDataException){
        return ObjectNodeHelper.controllerAdviceError(creatingDataException);
    }

    @ExceptionHandler(value = DependantNull.class)
    public ObjectNode dependantNull(DependantNull dependantNull){
        return ObjectNodeHelper.controllerAdviceError(dependantNull);
    }

    @ExceptionHandler(value = AutoReportNotFoundException.class)
    public ObjectNode autoReportNotFound(AutoReportNotFoundException a){
        return ObjectNodeHelper.controllerAdviceError(a);
    }

    @ExceptionHandler(value = SqlQueryParameterNotFound.class)
    public ObjectNode sqlQueryParameterNotFound(SqlQueryParameterNotFound sqlQueryParameterNotFound){
        return ObjectNodeHelper.controllerAdviceError(sqlQueryParameterNotFound);
    }


    @ExceptionHandler(value = SqlQueryEmptyResultsException.class)
    public ObjectNode sqlQueryEmptyResults(SqlQueryEmptyResultsException s){
        return ObjectNodeHelper.controllerAdviceError(s);
    }


    @ExceptionHandler(value = FailedToReadWorkbookException.class)
    public ObjectNode failedToProcessWorkbook(FailedToReadWorkbookException failedToReadWorkbookException){
        return ObjectNodeHelper.controllerAdviceError(failedToReadWorkbookException);
    }

    @ExceptionHandler(value = SSbReportEmptyException.class)
    public ObjectNode ssbReportEmpty(SSbReportEmptyException ssbReportEmptyException){
        return ObjectNodeHelper.controllerAdviceError(ssbReportEmptyException);
    }

    @ExceptionHandler(value = SsbPaymentsReverseOnFailException.class)
    public ObjectNode ssbReportEmpty(SsbPaymentsReverseOnFailException sSbPaymentReverseOnFail){
        return ObjectNodeHelper.controllerAdviceError(sSbPaymentReverseOnFail);
    }



}
