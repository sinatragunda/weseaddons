/*Created by Sinatra Gunda
  At 9:27 AM on 2/26/2020 */

package com.wese.weseaddons.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.helper.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MakerCheckerEntry {

    private Long resourceId = null;
    private String subEntityName ;
    private String entityName ;
    private String actionName ;
    private Long commandId ;
    private Boolean status = false;
    private Boolean rollbackTransaction ;
    private Changes changes ;
    private Long savingsId ;
    private Long loanId ;
    private Long transactionId ;
    private List<DeveloperErrors> errors = new ArrayList<>();

    private Long subResourceId ;

    public MakerCheckerEntry() {
    }

    public MakerCheckerEntry(long resourceId, String subEntityName, String actionName) {
        this.resourceId = resourceId;
        this.subEntityName = subEntityName;
        this.actionName = actionName;
    }

    public Changes getChanges() {
        return changes;
    }

    public void setChanges(Changes changes) {
        this.changes = changes;
    }

    public Long getSavingsId() {
        return savingsId;
    }

    public void setSavingsId(Long savingsId) {
        this.savingsId = savingsId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }

    public Boolean getRollbackTransaction() {
        return rollbackTransaction;
    }

    public void setRollbackTransaction(Boolean rollbackTransaction) {
        this.rollbackTransaction = rollbackTransaction;
    }

    public long getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getsubEntityName() {
        return subEntityName;
    }

    public void setsubEntityName(String subEntityName) {
        this.subEntityName = subEntityName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public void setLoanId(Long id){
        this.loanId = id;
    }

    public Long getLoanId(){
        return this.loanId;
    }

    public void setErrors(List<DeveloperErrors> list){
        this.errors = list ;
    }

    public void setTransactionId(Long id){
        this.transactionId = id ;
    }

    public Long getTransactionId(){
        return this.transactionId;
    }

    public void setSubResourceId(Long id){
        this.subResourceId = id ;
    }

    public Long getSubResourceId(){
        return this.subResourceId;
    }

    public void setEntityName(String name){
        this.entityName = name ;
    }

    public String getEntityName(){
        return this.entityName;
    }

    public List<DeveloperErrors> getErrors(){
        return this.errors; 
    }

    public static MakerCheckerEntry fromHttpResponse(String arg){
        boolean hasResponse = Optional.ofNullable(arg).isPresent();
        if(hasResponse){
            return (MakerCheckerEntry)JsonHelper.serializeFromHttpResponse(new MakerCheckerEntry(),arg);
        }
        return new MakerCheckerEntry();
    }

    public boolean success(){
        return Optional.ofNullable(resourceId).isPresent();
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

}
