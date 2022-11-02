package com.wese.weseaddons.pojo;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wese.weseaddons.crb.enumerations.ACCOUNT_REPAYMENT_TERM;
import com.wese.weseaddons.crb.enumerations.ACCOUNT_STATUS;
import com.wese.weseaddons.crb.enumerations.ACCOUNT_TYPE;
import com.wese.weseaddons.crb.enumerations.APPLICATION_STATUS;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.interfaces.IdIndexedClass;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Loan implements IdIndexedClass {

    private boolean isActive ;
    private long id ;
    private String accountNo ;
    private Long clientId ;
    private double principalOutstanding ;
    private double interestOutstanding;
    private boolean isInArrears ;
    private double principal ;
    private double principalDisbursed ;
    private double principalPaid ;
    private boolean waitingForDisbursal ;
    private boolean pendingApproval ;
    private double interestPaid ;
    private double totalOverdue ;
    private double totalRepayment;
    private double transactionPayment ;
    private boolean closed ;
    private Date closedOnDate ;
    private Date disbursedDate ;
    private Date submittedDate ;
    private Date approvedOnDate ;
    private Date expectedMaturityDate ;
    private Date loanRepaymentDate ;
    private Currency currency ;
    private LoanType loanType ;
    private LoanSummary summary;
    private Client client ;
    private ACCOUNT_STATUS accountStatus ;
    private APPLICATION_STATUS applicationStatus ;
    private ACCOUNT_REPAYMENT_TERM accountRepaymentTerm ;
    private List<LoanRepaymentSchedule> loanRepaymentScheduleList;

    private Timeline timeline ;


    public APPLICATION_STATUS getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(APPLICATION_STATUS applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Loan(){}

    public Loan(Long id){
        this.id = id ;
    }

    public Loan(long id  , String accountNumber, double principal ,  double principalOutstanding , double interestOutstanding , double principalDisbursed , double principalPaid , double totalOutstanding , boolean isInArrears , boolean pendingApproval , boolean waitingForDisbursal , Date disbursedDate){
        this.id = id ;
        this.principal = principal ;
        this.accountNo = accountNumber;
        this.principalOutstanding = principalOutstanding ;
        this.interestOutstanding = interestOutstanding ;
        this.isInArrears = isInArrears ;
        this.pendingApproval = pendingApproval ;
        this.waitingForDisbursal = waitingForDisbursal ;
        this.principalDisbursed = principalDisbursed ;
        this.principalPaid = principalPaid ;
        this.disbursedDate = disbursedDate ;

    }

    public Loan(long id  , String accountNumber, double principal , double principalOutstanding , double interestPaid , double interestOutstanding , double principalDisbursed , double principalPaid , double totalOutstanding , double totalOverdue , double totalRepayment , boolean isInArrears , boolean closed , boolean pendingApproval , boolean waitingForDisbursal , Date submittedDate , Date approvedOnDate , Date disbursedDate, Date expectedMaturityDate , Date closedOnDate , String currency, ACCOUNT_TYPE loanType , Client client , APPLICATION_STATUS applicationStatus , ACCOUNT_REPAYMENT_TERM accountRepaymentTerm , ACCOUNT_STATUS accountStatus ,List loanPaymentScheduleList){
        this.id = id ;
        this.principal = principal ;
        this.accountNo = accountNumber;
        this.principalOutstanding = principalOutstanding ;
        this.interestOutstanding = interestOutstanding ;
        this.isInArrears = isInArrears ;
        this.interestPaid = interestPaid ;
        this.pendingApproval = pendingApproval ;
        this.waitingForDisbursal = waitingForDisbursal ;
        this.principalDisbursed = principalDisbursed ;
        this.principalPaid = principalPaid ;
        this.submittedDate = submittedDate ;
        this.disbursedDate = disbursedDate ;
        this.approvedOnDate = approvedOnDate;
       // this.currency = currency ;
        //this.loanType = loanType ;
        this.client = client ;
        this.applicationStatus = applicationStatus ;
        this.expectedMaturityDate = expectedMaturityDate ;
        this.accountRepaymentTerm = accountRepaymentTerm ;
        this.accountStatus = accountStatus ;
        this.totalOverdue = totalOverdue ;
        this.totalRepayment = totalRepayment ;
        this.closed = closed ;
        this.closedOnDate = closedOnDate ;
        this.loanRepaymentScheduleList = loanPaymentScheduleList ;
    }

    public Loan(long id  , double principal ,double principalOutstanding ,double interestOutstanding ,boolean isInArrears ,boolean pendingApproval){

        this.id = id ;
        this.principal = principal ;
        this.principalOutstanding = principalOutstanding ;
        this.interestOutstanding = interestOutstanding ;
        this.isInArrears = isInArrears ;
        this.pendingApproval = pendingApproval ;

    }



    public Date getLoanRepaymentDate() {
        return loanRepaymentDate;
    }

    public void setLoanRepaymentDate(Date loanRepaymentDate) {
        this.loanRepaymentDate = loanRepaymentDate;
    }

    public ACCOUNT_REPAYMENT_TERM getAccountRepaymentTerm() {
        return accountRepaymentTerm;
    }

    public void setAccountRepaymentTerm(ACCOUNT_REPAYMENT_TERM accountRepaymentTerm) {
        this.accountRepaymentTerm = accountRepaymentTerm;
    }

    public double getTotalOverdue() {
        return totalOverdue;
    }

    public double getTotalRepayment() {
        return totalRepayment;
    }

    public boolean isClosed() {
        return closed;
    }

    public List<LoanRepaymentSchedule> getLoanRepaymentScheduleList() {
        return loanRepaymentScheduleList;
    }

    public void setLoanRepaymentScheduleList(List<LoanRepaymentSchedule> loanRepaymentScheduleList) {
        this.loanRepaymentScheduleList = loanRepaymentScheduleList;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Date getClosedOnDate() {
        return closedOnDate;
    }

    public void setClosedOnDate(Date closedOnDate) {
        this.closedOnDate = closedOnDate;
    }

    public double getTransactionPayment() {
        return transactionPayment;
    }

    public void setTransactionPayment(double transactionPayment) {
        this.transactionPayment = transactionPayment;
    }

    public void setTotalRepayment(double totalRepayment) {
        this.totalRepayment = totalRepayment;
    }

    public void setTotalOverdue(double totalOverdue) {
        this.totalOverdue = totalOverdue;
    }

    public double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public Date getDisbursedDate() {
        return disbursedDate;
    }


    public double getPrincipalDisbursed() {
        return principalDisbursed;
    }

    public double getPrincipalPaid() {
        return principalPaid;
    }

    public boolean isWaitingForDisbursal() {
        return waitingForDisbursal;
    }

    public boolean isPendingApproval() {
        return pendingApproval;
    }

    public void setPendingApproval(boolean pendingApproval) {
        this.pendingApproval = pendingApproval;
    }

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public boolean isInArrears() {
        return isInArrears;
    }

    public void setInArrears(boolean inArrears) {
        this.isInArrears = inArrears;
    }

    public double getInterestOutstanding() {
        return interestOutstanding;
    }

    public void setInterestOutstanding(double interestOutstanding) {
        this.interestOutstanding = interestOutstanding;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public boolean isActive() {
        return isActive;
    }

    public double getPrincipalOutstanding() {
        return principalOutstanding;
    }

    public void setPrincipalOutstanding(double principalOutstanding) {
        this.principalOutstanding = principalOutstanding;
    }

    public ACCOUNT_STATUS getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(ACCOUNT_STATUS accountStatus) {
        this.accountStatus = accountStatus;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNo = accountNumber;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setPrincipalDisbursed(double principalDisbursed) {
        this.principalDisbursed = principalDisbursed;
    }

    public void setPrincipalPaid(double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public void setWaitingForDisbursal(boolean waitingForDisbursal) {
        this.waitingForDisbursal = waitingForDisbursal;
    }

    public void setDisbursedDate(Date disbursedDate) {
        this.disbursedDate = disbursedDate;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Date getApprovedOnDate() {
        return approvedOnDate;
    }

    public void setApprovedOnDate(Date approvedOnDate) {
        this.approvedOnDate = approvedOnDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getExpectedMaturityDate() {
        return expectedMaturityDate;
    }

    public void setExpectedMaturityDate(Date expectedMaturityDate) {
        this.expectedMaturityDate = expectedMaturityDate;
    }

    public LoanSummary getSummary() {
        return summary;
    }

    public void setSummary(LoanSummary summary) {
        this.summary = summary;
    }

    public static Loan fromHttpResponse(String arg){
        return JsonHelper.serializeFromHttpResponse(new Loan() ,arg);
    }

    public Timeline getTimeline(){
        return timeline ;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "isActive=" + isActive +
                ", id=" + id +
                ", clientId=" + clientId +
                ", principalOutstanding=" + principalOutstanding +
                ", interestOutstanding=" + interestOutstanding +
                ", accountNumber='" + accountNo + '\'' +
                ", isInArrears=" + isInArrears +
                ", principal=" + principal +
                ", principalDisbursed=" + principalDisbursed +
                ", principalPaid=" + principalPaid +
                ", waitingForDisbursal=" + waitingForDisbursal +
                ", pendingApproval=" + pendingApproval +
                ", interestPaid=" + interestPaid +
                ", totalOverdue=" + totalOverdue +
                ", totalRepayment=" + totalRepayment +
                ", transactionPayment=" + transactionPayment +
                ", closed=" + closed +
                ", closedOnDate=" + closedOnDate +
                ", disbursedDate=" + disbursedDate +
                ", submittedDate=" + submittedDate +
                ", approvedOnDate=" + approvedOnDate +
                ", expectedMaturityDate=" + expectedMaturityDate +
                ", loanRepaymentDate=" + loanRepaymentDate +
                ", currency=" + currency +
                ", loanType=" + loanType +
                ", summary=" + summary +
                ", client=" + client +
                ", accountStatus=" + accountStatus +
                ", applicationStatus=" + applicationStatus +
                ", accountRepaymentTerm=" + accountRepaymentTerm +
                ", loanRepaymentScheduleList=" + loanRepaymentScheduleList +
                '}';
    }
}