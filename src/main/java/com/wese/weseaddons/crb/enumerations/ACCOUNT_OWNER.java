/*Created by Sinatra Gunda
  At 11:26 AM on 2/24/2020 */

package com.wese.weseaddons.crb.enumerations;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ACCOUNT_OWNER {

    O("the credit facility is obtained by the borrower himself"),
    J("one of the borrowers in a joint application, e.g. in a joint housing loan"),
    S("the credit facility is obtained by the sole proprietorship in which the borrower is the owner"),
    P("the credit facility is obtained by the partnership in which the borrower is one of the partners"),
    G("The borrowers of the loan are within a group");

    String code ;

    public String getCode(){
        return code ;
    }

    ACCOUNT_OWNER(String code){
        this.code = code ;
    }

    public static ACCOUNT_OWNER fromString(String arg){

        for(ACCOUNT_OWNER c : ACCOUNT_OWNER.values()){
            if(c.getCode().equalsIgnoreCase(arg)){
                return c ;
            }
        }

        return null ;
    }

    @JsonValue
    public String value(){
        return name() ;
    }
}
