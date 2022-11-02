package com.wese.weseaddons.ussd.session;


import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.ussd.enumerations.*;
import com.wese.weseaddons.ussd.helper.MonetaryInput;
import com.wese.weseaddons.ussd.helper.PostRequestArg;
import com.wese.weseaddons.ussd.interfaces.USSDMenu;
import com.wese.weseaddons.ussd.tree.TreeDataStructure;

import java.util.ArrayList;
import java.util.List;

public class Session {


    private TreeDataStructure<USSDMenu> treeDataStructure ;
    private String sessionId ;
    private String sessionCode ;
    private String phoneNumber ;
    private String option ;
    private int executeIndex ;
    private Client client ;
    private List<Loan> loansList ;
    private INPUT_FLAG inputFlag ;
    private EXECUTE_FLAG executeFlag ;
    private MonetaryInput monetaryInput ;
    private List<SavingsAccount> savingsAccountList ;
    private List<LoanProducts> loanProductsList ;
    private ClientRequest clientRequest;
    private LoansRequest loansRequest ;
    private PostRequestArg postRequestArg ;
    private PHONE_NUMBER_FLAG phoneNumberFlag ;
    private PREREASON_FLAG preReasonFlag ;
    private INTERUPT_CALL interuptCall ;
    private Object interuptId ;
    private long timestamp ;
    private String tenantIdentifier ;


    public Session(String sessionId ,String phoneNumber ,String sessionCode){

        this.sessionCode = sessionCode ;
        this.sessionId = sessionId ;
        this.tenantIdentifier = sessionCode ;
        this.option = String.valueOf(0) ;
        this.phoneNumber = phoneNumber ;
        this.monetaryInput = new MonetaryInput();
        this.inputFlag = INPUT_FLAG.OFF ;
        this.executeFlag = EXECUTE_FLAG.OFF ;
        this.phoneNumberFlag = PHONE_NUMBER_FLAG.OFF ;
        this.preReasonFlag = PREREASON_FLAG.OFF ;
        this.interuptCall = INTERUPT_CALL.NULL ;

        init();

    }


    public Session(String sessionId ,String phoneNumber ,String sessionCode ,String serviceCode ,TreeDataStructure<USSDMenu> treeDataStructure){
        this.sessionCode = sessionCode ;
        this.sessionId = sessionId ;
        this.phoneNumber = phoneNumber ;
        this.tenantIdentifier = serviceCode ;
        this.treeDataStructure = treeDataStructure ;
    }

    public void init(){

        loansList = new ArrayList<>();
        postRequestArg = new PostRequestArg();

    }

    public List<LoanProducts> getLoanProductsList() {
        return loanProductsList;
    }

    public void setLoanProductsList(List<LoanProducts> loanProductsList) {
        this.loanProductsList = loanProductsList;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getInteruptId() {
        return interuptId;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public void setInteruptId(Object interuptId) {
        this.interuptId = interuptId;
    }

    public INTERUPT_CALL getInteruptCall() {
        return interuptCall;
    }

    public void setInteruptCall(INTERUPT_CALL interuptCall) {
        this.interuptCall = interuptCall;
    }

    public PREREASON_FLAG getPreReasonFlag() {
        return preReasonFlag;
    }

    public void setPreReasonFlag(PREREASON_FLAG preReasonFlag) {
        this.preReasonFlag = preReasonFlag;
    }

    public PHONE_NUMBER_FLAG getPhoneNumberFlag() {
        return phoneNumberFlag;
    }

    public void setPhoneNumberFlag(PHONE_NUMBER_FLAG phoneNumberFlag) {
        this.phoneNumberFlag = phoneNumberFlag;
    }

    public PostRequestArg getPostRequestArg() {
        return postRequestArg;
    }

    public void setPostRequestArg(PostRequestArg postRequestArg) {
        this.postRequestArg = postRequestArg;
    }

    public void setClientSavingsAccountList(List<SavingsAccount> list){
        this.savingsAccountList = list ;
    }

    public List<SavingsAccount> getClientSavingsAccountList(){

        return savingsAccountList ;
    }

    public void setExecuteIndex(int arg){
        this.executeIndex = arg;
    }

    public int getExecuteIndex(){

        return this.executeIndex;
    }

    public ClientRequest getClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(ClientRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    public List<SavingsAccount> getSavingsAccountList() {
        return savingsAccountList;
    }

    public void setSavingsAccountList(List<SavingsAccount> savingsAccountList) {
        this.savingsAccountList = savingsAccountList;
    }

    public INPUT_FLAG getInputFlag() {
        return inputFlag;
    }

    public void setInputFlag(INPUT_FLAG inputFlag) {
        this.inputFlag = inputFlag;
    }

    public EXECUTE_FLAG getExecuteFlag(){
        return executeFlag ;
    }

    public void setExecuteFlag(EXECUTE_FLAG executeFlag){
        this.executeFlag = executeFlag ;
    }

    public List<Loan> getLoansList() {
        return loansList;
    }

    public void setLoansList(List<Loan> loansList) {
        this.loansList = loansList;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getOption(){
        return option;
    }

    public void setOption(String option){
        this.option = option ;
    }

    public MonetaryInput getMonetaryInput() {
        return monetaryInput;
    }

    public void setMonetaryInput(MonetaryInput monetaryInput) {
        this.monetaryInput = monetaryInput;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public TreeDataStructure<USSDMenu> getTreeDataStructure(){
        return treeDataStructure ;
    }

    public void setTreeDataStructure(TreeDataStructure<USSDMenu> treeDataStructure) {
        this.treeDataStructure = treeDataStructure ;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }

    public Session current(){
        return this;
    }

}
