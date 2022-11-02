package com.wese.weseaddons.assetregister.pojo;

import com.wese.weseaddons.interfaces.PojoInterface;

public class InputErrorCode implements PojoInterface{

    private String code ;
    private String reason ;

    @Override
    public String getSchema(){
        return "m_input_error_code";
    }

    public InputErrorCode() {
    }

    @Override
    public Long getId() {
        return null;
    }

    public InputErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
