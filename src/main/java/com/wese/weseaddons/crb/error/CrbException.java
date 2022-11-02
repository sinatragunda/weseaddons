/*Created by Sinatra Gunda
  At 5:57 AM on 2/25/2020 */

package com.wese.weseaddons.crb.error;

import com.wese.weseaddons.errors.WeseErrorService;

public class CrbException extends RuntimeException implements WeseErrorService{

    public enum CRB_EXCEPTION{

        CA
    } ;

    private CRB_EXCEPTION crbException ;
    private String validationMessage ;

    public CrbException(CRB_EXCEPTION crbException){
        this.crbException = crbException ;

    }

    public CrbException(String message){
        this.validationMessage = message ;
    }



    @Override
    public String getMessage() {

        if(validationMessage !=null){
            return validationMessage ;
        }

        String message = null ;

        switch (crbException){

            case CA :
                message = "Credit Application upload has failed to upload new loan data";
                break;
        }

        return message ;
    }
}
