/*

    Created by Sinatra Gunda
    At 12:42 PM on 6/11/2021

*/
package com.wese.weseaddons.batchprocessing.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wese.weseaddons.batchprocessing.enumerations.PRE_LOADING_ACCOUNT;
import com.wese.weseaddons.batchprocessing.enumerations.STAGING;
import com.wese.weseaddons.helper.ComparatorUtility;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.weselicense.helper.ObjectNodeBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SsbPayment {

    private boolean settleShortfallExcess = false;
    private boolean preshareAccountPurchase = false ;
    private String description ;
    private int officeId ;
    private String postedDate ;
    private Boolean allowMultiLoanPayments = false;
    private Long drawdownProductId = 0L;
    private Boolean overrideMakerCheckerTasks = false;
    private Long settleSavingsProductId = 0L;
    private Long shareProductId = 0L;
    private Boolean allowStaging = false ;
    private Boolean isDDAFunded = false ;
    private String username ;
    private String password ;
    private boolean allowEarlyRepayment;
    private BigDecimal threshold = BigDecimal.ONE;

    /**
     * Added 14/10/2022 at 0303
     * Reverse all transactions on fail (of a single transaction)
     */
    private boolean reverseOnFail = false;

    public SsbPayment() {
    }

    public boolean isReverseOnFail() {
        return reverseOnFail;
    }

    public void setReverseOnFail(boolean reverseOnFail) {
        this.reverseOnFail = reverseOnFail;
    }

    public Boolean isAllowStaging() {
        return allowStaging;
    }

    public void setAllowStaging(Boolean allowStaging) {
        this.allowStaging = allowStaging;
    }

    public boolean isSettleShortfallExcess() {
        return settleShortfallExcess;
    }

    public void setSettleShortfallExcess(boolean settleShortfallExcess) {
        this.settleShortfallExcess = settleShortfallExcess;
    }

    public boolean isPreshareAccountPurchase() {
        return preshareAccountPurchase;
    }

    public void setPreshareAccountPurchase(boolean preshareAccountPurchase) {
        this.preshareAccountPurchase = preshareAccountPurchase;
    }

    public void setDrawdownProductId(Long drawdownProductId) {
        this.drawdownProductId = drawdownProductId;
    }


    public Long getSettleSavingsProductId() {
        // if not set should bring back drawdown id since its mandatory by default
        // error was when not set when funds were trying to be moved from share purchase it was not finding any account
        boolean isZero = ComparatorUtility.isLongZero(settleSavingsProductId);

        if(isZero){
            settleSavingsProductId = drawdownProductId;
        }
        return settleSavingsProductId;
    }

    public void setSettleSavingsProductId(Long settleSavingsProductId) {
        this.settleSavingsProductId = settleSavingsProductId;
    }

    public Long getShareProductId() {
        return shareProductId;
    }

    public void setShareProductId(Long shareProductId) {
        this.shareProductId = shareProductId;
    }

    public Boolean getOverrideMakerCheckerTasks() {
        return overrideMakerCheckerTasks;
    }

    public void setOverrideMakerCheckerTasks(Boolean overrideMakerCheckerTasks) {
        this.overrideMakerCheckerTasks = overrideMakerCheckerTasks;
    }


    public Boolean getAllowMultiLoanPayments() {
        return allowMultiLoanPayments;
    }

    public void setAllowMultiLoanPayments(Boolean allowMultiLoanPayments) {
        this.allowMultiLoanPayments = allowMultiLoanPayments;
    }

    public Long getDrawdownProductId() {
        return drawdownProductId;
    }

    public void setSavingsProductId(Long savingsProductId) {
        this.drawdownProductId = savingsProductId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String transferDescription) {
        this.description = transferDescription;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }


    public boolean isAllowMultiLoanPayments(){
        return allowMultiLoanPayments;
    }

    public void setAllowMultiLoanPayments(boolean multiLoanPayments) {
        this.allowMultiLoanPayments = multiLoanPayments;
    }

    public boolean isDDAFunded(){
        return isDDAFunded;
    }

    public void setIsDDAFunded(boolean isDDAFunded) {
        this.isDDAFunded = isDDAFunded;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllowEarlyRepayment() {
        return allowEarlyRepayment;
    }

    public void setAllowEarlyRepayment(boolean allowEarlyRepayment) {
        this.allowEarlyRepayment = allowEarlyRepayment;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public static SsbPayment fromHttpResponse(String arg){
        return JsonHelper.serializeFromHttpResponse(new SsbPayment() ,arg);

    }
}
