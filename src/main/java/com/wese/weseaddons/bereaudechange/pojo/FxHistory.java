package com.wese.weseaddons.bereaudechange.pojo;

import com.wese.weseaddons.bereaudechange.enumerations.AUDIT_TRAIL;

import com.wese.weseaddons.pojo.AppUser ;


public class FxHistory {

        private long id ;
        private AUDIT_TRAIL auditTrail ;
        private AppUser authorizer ;
        private long resourceId ;
        private long date ;


        public FxHistory() {
        }


        public FxHistory(long id) {
                this.id = id;
        }

        public FxHistory(AppUser authorizer ,AUDIT_TRAIL auditTrail ,long resourceId){
                this.authorizer = authorizer ;
                this.auditTrail = auditTrail ;
                this.resourceId = resourceId;
        }


        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public void setDate(long i){
                this.date = i ;
        }

        public long getDate(){
                return date ;
        }


        public AUDIT_TRAIL getAuditTrail() {
                return auditTrail;
        }

        public void setAuditTrail(int arg) {
                this.auditTrail = AUDIT_TRAIL.fromInt(arg);
        }

        public AppUser getAuthorizer() {
                return authorizer;
        }

        public void setAuthorizer(AppUser authorizer) {
                this.authorizer = authorizer;
        }

        public long getResourceId() {
                return resourceId;
        }

        public void setResourceId(long resourceId) {
                this.resourceId = resourceId;
        }
}