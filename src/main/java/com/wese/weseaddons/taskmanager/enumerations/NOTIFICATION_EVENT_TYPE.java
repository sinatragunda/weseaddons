package com.wese.weseaddons.taskmanager.enumerations;

public enum NOTIFICATION_EVENT_TYPE {

    OVERDUE_TASK("Overdue") ,
    CLOSED_TASK("Closed") ,
    ASSIGNED_TASK("Assigned"),
    MANAGE_TASK("Manage"),
    FEEDBACK_ADDED_TASK("Feedback"),
    REVERSE_FX_DEAL("Reverse Fx Deal");

    private String code ;

    NOTIFICATION_EVENT_TYPE(String code){
        this.code = code ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static NOTIFICATION_EVENT_TYPE fromCode(String code){

        for(NOTIFICATION_EVENT_TYPE state : NOTIFICATION_EVENT_TYPE.values()){
            if(state.getCode().equalsIgnoreCase(code)){
                return state ;
            }
        }
        return null ;
    }
}
