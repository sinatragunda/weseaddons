package com.wese.weseaddons.batchprocessing.pojo;

import com.wese.weseaddons.batchprocessing.enumerations.PORTFOLIO_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.SSB_REPORT_TYPE;
import com.wese.weseaddons.batchprocessing.enumerations.STAGING;
import com.wese.weseaddons.batchprocessing.enumerations.TRANSACTION_STATUS;
import com.wese.weseaddons.enumerations.STATUS;
import com.wese.weseaddons.helper.BigDecimalHelper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.pojo.FundDDAAccount;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.pojo.Client;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Optional;

public class ExcelClientData{

    private String name;
    private BigDecimal amount ;
    private String nrcNumber ;
    private String employeeNumber ;
    private String loanAccountNumber;
    private int count ;
    private Loan loan = null;
    private boolean failed = false ;
    private BigDecimal orignalDeposit = BigDecimal.ZERO;

    private SavingsAccount drawdownAccount ;
    private Client client ;
    private STAGING staging = STAGING.NONE;
    private Long resourceId = null;
    private Long timestamp = null ;
    private String statusDescription ;
    private STATUS status = STATUS.NONE;

    // added 19/03/2022
    private PORTFOLIO_TYPE portfolioType = PORTFOLIO_TYPE.DISCARD ;
    private SSB_REPORT_TYPE ssbReportType = SSB_REPORT_TYPE.NONE ;
    private boolean isReversed = false;
    private String transactionDate = null;
    private Long objectId = 0L;


    // added 02/02/2022
    private SavingsAccount fundDDAAccount;
    private String ddaFundAccountName ;

    /**
     * Added 29/09/2022 at 0956
     */
    TRANSACTION_STATUS transactionStatus = null;
    private BigDecimal transactionsTotal = BigDecimal.ZERO;

    /**
     * Added 18/0/2022 at 0400
     */

    public ExcelClientData(ExcelClientData excelClientData){
        initNew(excelClientData);

    }

    public static ExcelClientData instance(ExcelClientData excelClientData,SSB_REPORT_TYPE ssbReportType){

        /**
         * Added 14/10/2022 at 0223
         * Bad design here ,need to find a way to not repeat this whole init code
         * Code changed to initNew refactored class since two constructors were using it
         */

        ExcelClientData e = new ExcelClientData(excelClientData);
        e.setSsbReportType(ssbReportType);
        return e;
    }

    private ExcelClientData initNew(ExcelClientData excelClientData) {
        this.amount = BigDecimalHelper.roundOf2(excelClientData.getAmount());
        this.name = excelClientData.getName();
        this.nrcNumber = excelClientData.getNrcNumber();
        this.employeeNumber = excelClientData.getEmployeeNumber();
        this.loanAccountNumber = excelClientData.getLoanAccountNumber();
        this.count = excelClientData.getCount();
        this.loan = excelClientData.getLoan();
        this.client = excelClientData.getClient();
        this.staging = excelClientData.getStaging();
        this.resourceId = excelClientData.getResourceId();
        this.fundDDAAccount = excelClientData.getFundDDAAccount();
        this.status = excelClientData.getStatus();
        this.portfolioType = excelClientData.getPortfolioType();
        this.transactionDate = excelClientData.getTransactionDate();
        this.objectId = excelClientData.getObjectId();
        //this.timestamp = TimeHelper.epochNow();
        this.timestamp = TimeHelper.nanoTime();
        this.transactionStatus = excelClientData.getTransactionStatus();

        return this;
    }

    public ExcelClientData(FundDDAAccount fundDDAAccount){
        this.name = fundDDAAccount.getName();
        this.amount = BigDecimalHelper.roundOf2(fundDDAAccount.getAmount());
        this.portfolioType = PORTFOLIO_TYPE.SAVINGS ;
        this.resourceId = fundDDAAccount.getResourceId();
        this.objectId = fundDDAAccount.getId();
        this.statusDescription = fundDDAAccount.getStatusDescription();
        this.timestamp = TimeHelper.nanoTime();

    }


    public ExcelClientData(String name , BigDecimal amount , String nrcNumber,String employeeNumber ,String loanAccountNumber ,STAGING staging ,SavingsAccount savingsAccount ,String ddaFundAccountName , int count){
        this.name = name ;
        this.amount = BigDecimalHelper.roundOf2(amount) ;
        this.nrcNumber = nrcNumber ;
        this.employeeNumber = employeeNumber ;
        this.loanAccountNumber = loanAccountNumber ;
        this.count = count ;
        this.orignalDeposit = amount;
        if(staging!=null){
            this.staging = staging ;
        }
        this.ddaFundAccountName = ddaFundAccountName ;
    }

    public ExcelClientData(String name , BigDecimal amount , String nrcNumber,String employeeNumber ,String loanAccountNumber ,STAGING staging ,SavingsAccount savingsAccount ,String ddaFundAccountName,STATUS status,PORTFOLIO_TYPE portfolioType,SSB_REPORT_TYPE ssbReportType , int count){
        this.name = name ;
        this.amount = BigDecimalHelper.roundOf2(amount) ;
        this.nrcNumber = nrcNumber ;
        this.employeeNumber = employeeNumber ;
        this.loanAccountNumber = loanAccountNumber ;
        this.count = count ;
        this.orignalDeposit = this.amount;
        if(staging!=null){
            this.staging = staging ;
        }
        this.ddaFundAccountName = ddaFundAccountName ;
        this.status = status ;
        this.portfolioType = portfolioType;
        this.ssbReportType = ssbReportType ;
    }

    public void updateTransactionsTotal(BigDecimal amount){
        this.transactionsTotal = transactionsTotal.add(amount);
    }

    public BigDecimal getTransactionsTotal(){
        return this.transactionsTotal ;
    }


    public TRANSACTION_STATUS getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TRANSACTION_STATUS transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        if(timestamp==null){
            this.timestamp = TimeHelper.nanoTime();
            return;
        }
        this.timestamp = timestamp;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public STAGING getStaging(){
        return staging;
    }

    public void setStaging(STAGING staging) {
        this.staging = staging;
    }

    public BigDecimal getOrignalDeposit() {
        return orignalDeposit;
    }

    public void setOrignalDeposit(BigDecimal orignalDeposit) {
        this.orignalDeposit = BigDecimalHelper.roundOf2(orignalDeposit);
    }

    public SavingsAccount getDrawdownAccount() {
        return drawdownAccount;
    }

    public void setDrawdownAccount(SavingsAccount drawdownAccount) {
        this.drawdownAccount = drawdownAccount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setFailed(boolean arg){
        this.failed = arg ;
    }

    public boolean isFailed(){
        return this.failed ;
    }

    public void setName(String arg){
        this.name = arg ;
    }

    public void setLoan(Loan loan){
        this.loan = loan;
    }

    public Loan getLoan(){

        if(this.loan==null){
            this.loan = new Loan(new Long(0));
        }
        return this.loan ;
    }
    
    public String getLoanAccountNumber() {
        return loanAccountNumber ;
    }
    
    public int getCount() {
        return count ;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = BigDecimalHelper.roundOf2(amount);
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getName() {
        return name;
    }

    public String getNrcNumber() {
        return nrcNumber;
    }

    public void setLoanAccountNumber(String loanAccountNumber) {
        this.loanAccountNumber = loanAccountNumber;
    }


    public void setFundDDAAccount(SavingsAccount savingsAccount){
        this.fundDDAAccount = savingsAccount;
    }

    public SavingsAccount getFundDDAAccount(){
        return this.fundDDAAccount ;
    }


    public String getDdaFundAccountName() {
        return ddaFundAccountName;
    }

    public STATUS getStatus(){
        return status ;
    }

    public PORTFOLIO_TYPE getPortfolioType(){
        return portfolioType ;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public void setPortfolioType(PORTFOLIO_TYPE portfolioType) {
        this.portfolioType = portfolioType;
    }

    public SSB_REPORT_TYPE getSsbReportType() {
        return this.ssbReportType;
    }

    public void setSsbReportType(SSB_REPORT_TYPE ssbReportType) {
        this.ssbReportType = ssbReportType;
    }

    public boolean isReversed() {
        return isReversed;
    }

    public void setReversed(boolean reversed) {
        isReversed = reversed;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate ;
    }

    public String getTransactionDate() {
        return this.transactionDate;
    }

    public void setObjectId(Long id){
        this.objectId = id ;
    }

    public Long getObjectId(){
        return this.objectId; 
    }

    public void resetTransactionStatus(){
        this.transactionStatus=null ;
    }

    /**
     * Added 14/10/2022 at 0355
     */
    public String processDetails(){

        String value[] = {String.format("%s Transaction for client %s failed with result %s" ,portfolioType.getCode(),name,ssbReportType.getCode())};
        
        Optional.ofNullable(transactionStatus).ifPresent(e->{
            value[0] = String.format("%s Transaction for client %s failed with result %s" ,portfolioType.getCode(),name ,transactionStatus.getCode());
        
        });
        return value[0];
    }  

 

    @Override
    public String toString(){
        return String.format("%s : %s\n",name ,loanAccountNumber);
    }
}
