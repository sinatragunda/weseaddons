package com.wese.weseaddons.controller;


///written by Sinatra Gunda 05/0/2019

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wese.weseaddons.batchprocessing.client.*;
import com.wese.weseaddons.batchprocessing.enumerations.*;
import com.wese.weseaddons.batchprocessing.helper.*;
import com.wese.weseaddons.batchprocessing.helper.Parameters;
import com.wese.weseaddons.batchprocessing.pojo.ExcelClientData;
import com.wese.weseaddons.batchprocessing.pojo.SSbTransactionsAdapter;
import com.wese.weseaddons.batchprocessing.pojo.SsbPaymentsResults;
import com.wese.weseaddons.enumerations.STATUS;
import com.wese.weseaddons.errors.springexceptions.FailedCredentialsException;
import com.wese.weseaddons.errors.springexceptions.SSbReportEmptyException;
import com.wese.weseaddons.helper.*;
import com.wese.weseaddons.networkrequests.CredentialsRequest;
import com.wese.weseaddons.networkrequests.LoanRepaymentScheduleRequest;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.pojo.*;
import com.wese.weseaddons.batchprocessing.init.SSBPaymentsInit;
import com.wese.weseaddons.batchprocessing.pojo.SsbPayment;
import com.wese.weseaddons.batchprocessing.processor.ProcessClientDeposits;
import com.wese.weseaddons.batchprocessing.processor.ProcessClientLoanRepayments;
import com.wese.weseaddons.batchprocessing.wese.ClientRequest;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.batchprocessing.workbook.SSBPaymentsWorkbook;

import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Currency;
import com.wese.weseaddons.sqlquerries.helper.ClearWorkingSetHelper;
import com.wese.weseaddons.ussd.helper.MonetaryInput;
import com.wese.weseaddons.utility.ThreadContext;
import javassist.bytecode.stackmap.BasicBlock;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.format.number.money.MonetaryAmountFormatter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@RestController
@CrossOrigin
@RequestMapping(value="/batchprocess/ssbpayments")
public class BatchProcessorController {

    private ClientRequest clientRequest ;
    private SavingsRequest savingsRequest ;
    private Parameters parameters ;
    private SSbTransactionsAdapter  failedDeposits ;
    private SSbTransactionsAdapter  failedRepayments;
    private SSbTransactionsAdapter  noActiveLoans ;
    private SSbTransactionsAdapter  noMatchingLoans ;
    private SSbTransactionsAdapter waitingApproval = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter  repaidLoans = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter invalidSearchData = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter adjustedDeposit = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter invalidNrcNumber = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter noValidDrawdownAccount = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter savingsAccountPreloaded = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter excelClientDataList = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter sharesTransactionSuccessfull = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter sharesTransactionPending= new SSbTransactionsAdapter();
    private SSbTransactionsAdapter maximumSharesReached = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter noActiveSharesAccount = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter sharesInternalError = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter excessBalanceMoved = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter shortfallBalance = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter successfullDeposit = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter ddaFundingFailed = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter ddaFundingSuccess = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter zeroBalanceDeposit = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter failedExcessBalanceDeposits = new SSbTransactionsAdapter();

    /**
     * Added 14/10/2022 at 0215
     */
    private SSbTransactionsAdapter reverseList = new SSbTransactionsAdapter();
    private SSbTransactionsAdapter ddaFundNotValid ;

    private SSbTransactionsAdapter savingsMovements ;
    private SSbTransactionsAdapter ddaDepositSuccess ;
    private SSbTransactionsAdapter ddaDepositFailed ;
    private SSbTransactionsAdapter savingsToSharesMovements;

    private List<ObjectNode> objectNodesList;
    private Map<String ,BigDecimal> balancesMap;
    private SsbPayment ssbPayment ;
    private SsbPaymentsResults ssbPaymentResults ;
    private AccountTransferParser accountTransferParser ;

    private Boolean allowStaging = false;
    private Boolean shouldProcess = true ;

    // added 03/02/2022
    private Boolean isDDAFunded = false ;

    // added 05/04/2022
    private String transactionDate ;
    private Date transactionDateEx ;

    /**
     * Added 21/09/2022
     */
    private BigDecimal threshold ;

    private List<ClientSavingsExcelData> clientSavingsExcelDataList;

    /**
     * Added 14/10/2022 at 0329
     */
    private boolean reverseOnFail = false ;


    private List<FundDDAAccount> fundDDAAccountList ;

    @RequestMapping(value="/clearworkingset" ,method = RequestMethod.GET)
    public ObjectNode status(@RequestParam("tenantIdentifier")String tenantIdentifier ,@RequestParam("workingSet")int workingSet){

        ClearWorkingSetHelper clearWorkingsetHelper = new ClearWorkingSetHelper();
        boolean status = clearWorkingsetHelper.clear(workingSet);
        ObjectNode objectNode = ResponseBuilder.getObjectNode();
        objectNode.put("status", status);
        return objectNode ;
    }


    @RequestMapping(value="/status")
    public ObjectNode status(){

        PROCESS_STATUS processStatus = SSBPaymentsInit.getInstance().getProcessStatus();
        ObjectNode objectNode = ResponseBuilder.getObjectNode();
        ObjectNode previousResults = SSBPaymentsInit.getInstance().getProcessResults();
        if(previousResults ==null){
            objectNode.put("process" ,processStatus.ordinal());
            objectNode.put("status",false);
            return objectNode;
        }
        previousResults.put("status", true);
        previousResults.put("process", processStatus.ordinal());
        //objectNode.putOpreviousResults);

        return previousResults ;
    }

    @RequestMapping(value="/clear")
    public void clear(@RequestParam("tenantIdentifier")String tenantIdentifier){
        SSBPaymentsInit.getInstance().updateProcessStatus(tenantIdentifier, PROCESS_STATUS.CLEARED);
    }

    @RequestMapping(value="/reporttemplate" ,method = RequestMethod.GET)
    public List<String> reportTemplate(){
        return SSB_REPORT_TYPE.template();
    }

    @RequestMapping(value="/report" ,params = {"type"},produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] report(@RequestParam("type")String type){

        SSB_REPORT_TYPE reportType = SSB_REPORT_TYPE.fromString(type);
        File file = new File(String.format("ssb_report_%s",reportType.getCode()));
        file = SsbPaymentsResultHelper.results(file ,reportType);

        InputStream inputStream = null ;
        try{
            inputStream = new FileInputStream(file);
            return IOUtils.toByteArray(inputStream);
        }
        catch (Exception f){
            f.printStackTrace();
            throw new SSbReportEmptyException(reportType.getCode());
        }
    }


    @RequestMapping(value = "/reverse", method = RequestMethod.GET)
    public ObjectNode reverseFinancialTransactions(@RequestParam("type")String type){

        SSB_REPORT_TYPE reportType = SSB_REPORT_TYPE.fromString(type);
        boolean isReversable = SsbPaymentsResultHelper.isTransactionReversable(reportType);

        if(!isReversable){
            return ObjectNodeHelper.statusNodes(false).put("message" ,"Failed transactions are not reversable");
        }

        List<ExcelClientData> excelClientDataList = SsbPaymentsResultHelper.results(reportType);

        ReverseTransactionHelper.reverseTransactions(excelClientDataList);
        ObjectNode objectNode = SSBPaymentsInit.getInstance().getProcessResults();
        objectNode.put("status" ,true);
        if(objectNode==null){
            return ObjectNodeHelper.statusNodes(false).put("message" ,"No valid results");
        }

        System.err.println(objectNode.toString());
        return objectNode;
    }


    public void init(){

        failedRepayments = new SSbTransactionsAdapter();
        noActiveSharesAccount = new SSbTransactionsAdapter();
        noValidDrawdownAccount = new SSbTransactionsAdapter();;
        noMatchingLoans = new SSbTransactionsAdapter();
        sharesInternalError = new SSbTransactionsAdapter();
        sharesTransactionSuccessfull = new SSbTransactionsAdapter();
        sharesTransactionPending = new SSbTransactionsAdapter();
        failedDeposits = new SSbTransactionsAdapter();
        adjustedDeposit = new SSbTransactionsAdapter();
        successfullDeposit = new SSbTransactionsAdapter();
        shortfallBalance = new SSbTransactionsAdapter();
        excessBalanceMoved = new SSbTransactionsAdapter();
        invalidNrcNumber = new SSbTransactionsAdapter();
        maximumSharesReached = new SSbTransactionsAdapter();
        savingsAccountPreloaded = new SSbTransactionsAdapter();
        invalidSearchData = new SSbTransactionsAdapter();
        waitingApproval = new SSbTransactionsAdapter();
        repaidLoans = new SSbTransactionsAdapter();
        noActiveLoans = new SSbTransactionsAdapter();
        ddaFundingFailed = new SSbTransactionsAdapter();
        ddaFundingSuccess = new SSbTransactionsAdapter();
        ddaFundNotValid = new SSbTransactionsAdapter();

        // added 09/02/2022
        ddaDepositFailed = new SSbTransactionsAdapter();
        ddaDepositSuccess = new SSbTransactionsAdapter();

        // added 14/04/2022
        failedExcessBalanceDeposits = new SSbTransactionsAdapter();

        balancesMap = new HashMap<>();
        excelClientDataList = new SSbTransactionsAdapter();
        objectNodesList = new ArrayList<>();
        clientSavingsExcelDataList = new ArrayList<>();
        fundDDAAccountList = new ArrayList<>();

        // added 23/03/2022
        savingsMovements = new SSbTransactionsAdapter();

        // added 28/03/2022
        savingsToSharesMovements = new SSbTransactionsAdapter();

        /**
         * Added17/10/2022 at 0418
         * New design results ssbpayments results to be initialized twice
         * Once for failed results and the other for when sucessfull
         */
        ssbPaymentResults = new SsbPaymentsResults();

    }

    private Consumer<FundDDAAccount> processDepositFundDDAConsumer = (e)->{

        BigDecimal amount = e.getAmount();
        Long ddaSavingsAccountId = e.getId();

        FundDDAAccount oldAccount[] = {e} ;
        boolean success[] = {false};

        SavingsAccount savingsAccount = savingsRequest.savingsAccount(ddaSavingsAccountId);
        ProcessClientDeposits processClientDeposits[] = {null} ;
        boolean isDepositable[] = {false} ;

        // added 27/07/2022 ,should report error if dda fund account not present
        boolean isPresent = Optional.ofNullable(savingsAccount).isPresent();

        if(!isPresent){
            noValidDrawdownAccount.addOnFail(e.toClientData() ,SSB_REPORT_TYPE.NO_VALID_SAVINGS_ACCOUNT ,PORTFOLIO_TYPE.SAVINGS, reverseList ,reverseOnFail,ssbPaymentResults);
            return ;
        }

        // no need of double checking null here
            //Currency currency = savingsAccount.getCurrency();
        isDepositable[0] = ComparatorUtility.isDepositableAmount(amount);

        if(isDepositable[0]) {
            processClientDeposits[0] = new ProcessClientDeposits(savingsAccount, amount.doubleValue(), parameters);
            success[0] = processClientDeposits[0].deposit(true);
            oldAccount[0].updateFromSavingsAccount(savingsAccount);
        }

        e = oldAccount[0];

        if(success[0]){

            // failed dda client deposit here
            MakerCheckerEntry makerCheckerEntry = processClientDeposits[0].getMakerCheckerEntry();
            isPresent = Optional.ofNullable(makerCheckerEntry).isPresent();

            if(isPresent){
                Long resourceId = makerCheckerEntry.getResourceId();
                Long objectId = makerCheckerEntry.getSavingsId();

                e.setResourceId(resourceId);
                e.setId(objectId);
                ddaDepositSuccess.add(e.toClientData() ,SSB_REPORT_TYPE.DDA_FUND_DEPOSIT_SUCCESS,PORTFOLIO_TYPE.SAVINGS, reverseList,ssbPaymentResults);
            }
            return;
        }

        String failedMessage = String.format("Failed DDA transfer from %s to account",e.getName());
        e.setStatusDescription(failedMessage);

        if(isDepositable[0]){
            ddaDepositFailed.addOnFail(e.toClientData() ,SSB_REPORT_TYPE.DDA_FUND_TO_CLIENT_FAILED,PORTFOLIO_TYPE.SAVINGS, reverseList ,reverseOnFail ,ssbPaymentResults);
        }
    };

    private Consumer<ExcelClientData>  linkToFundDDAConsumer = (client)->{

        boolean isPresent = Optional.ofNullable(client.getDdaFundAccountName()).isPresent();

        Optional.ofNullable(client.getDdaFundAccountName()).ifPresent(fundDDA ->{

            String clientFundName = fundDDA;

            List<FundDDAAccount> account = fundDDAAccountList.stream().filter(e->{
                //Long fundId = e.getId();
                String fundName = e.getName();
                return ComparatorUtility.compareStringsIgnoreCase(clientFundName ,fundName);

            }).collect(Collectors.toList());

            if(!account.isEmpty()){
                FundDDAAccount fundDDAAccount = account.get(0);
                client.setFundDDAAccount(fundDDAAccount);
            }
        });

        if(!isPresent){
            ddaFundNotValid.addOnFail(client ,SSB_REPORT_TYPE.DDA_FUNDER_NOT_VALID ,PORTFOLIO_TYPE.SAVINGS,reverseList,reverseOnFail,ssbPaymentResults);
        }
    };

    private Consumer<ExcelClientData> processDepositsConsumer = (e)->{

        boolean isPresharePurchase = ssbPayment.isPreshareAccountPurchase();
        Long objectId = null ;
        MakerCheckerEntry makerCheckerEntry = null ;

        shouldProcess = SsbPaymentsHelper.shouldDoProcess(e.getStaging() ,STAGING.SUCCESS ,allowStaging);

        if(!shouldProcess){
            return ;
        }

        Long resourceId = null ;

        String nrcNumber = e.getNrcNumber();
        Client client = clientRequest.getClient(nrcNumber);

        if(client==null) {
            /// invalid search data
            e.setStaging(STAGING.FAILED_ON_CLIENT_SEARCH);
            invalidNrcNumber.add(e);
            return;
        }



        /// architecture should change here now all money should come from dda account to do respective shares


        /// here if record has a valid dda account money should be moved from dda to share

        ///client transactions zone here so set clientid as resource id
        e.setName(client.getDisplayName());
        e.setClient(client);
        e.setResourceId(client.getId());

        shouldProcess = SsbPaymentsHelper.shouldDoProcess(e.getStaging() ,STAGING.FAILED_ON_DDA_DEPOSIT ,allowStaging);

        // dda to client transfers
        if(shouldProcess && isDDAFunded){

            ClientDrawdownAccount clientDrawdownAccount = new ClientDrawdownAccount(client, e).invoke();

            // client has no drawdown account so return
            if (!clientDrawdownAccount.hasDrawdownAccount()){
                noValidDrawdownAccount.addOnFail(e ,SSB_REPORT_TYPE.NO_VALID_SAVINGS_ACCOUNT,PORTFOLIO_TYPE.SAVINGS, reverseList, reverseOnFail,ssbPaymentResults);
                return;
            }

            //System.err.println("-------account balance before -----"+clientDrawdownAccount.drawdownSavingsAccount.getAccountBalance());

            makerCheckerEntry = FundDDAHelper.transferFromFundDDAToClientDDA(e ,savingsRequest ,parameters);

            /**
             * Added 17/10/2022 at 0223
             * MakerChecker returns null when transferfromfundtoclient fails to link customer
             *
             */
            boolean isPresent = Optional.ofNullable(makerCheckerEntry).isPresent();

            if(!isPresent){
                return ;
            }

            boolean success = makerCheckerEntry.success();

            if(!success){
                e.setStaging(STAGING.FAILED_ON_DDA_DEPOSIT);
                ddaFundingFailed.addOnFail(e ,SSB_REPORT_TYPE.DDA_FUND_DEPOSIT_FAILED,PORTFOLIO_TYPE.SAVINGS, reverseList,reverseOnFail,ssbPaymentResults);
                return;
            }

            /// source account reversals
            resourceId = makerCheckerEntry.getTransactionId();
            objectId  = makerCheckerEntry.getSavingsId();

            //e.setObjectId(objectId);
            //e.setResourceId(resourceId);

            // get dda account id here and reverse these transactions from the root see where it gets us but will have to modify all other reversals

            //ddaFundingSuccess.add(new ExcelClientData(e));

            addBalanceMovement(ddaFundingSuccess ,e, objectId ,resourceId,SSB_REPORT_TYPE.DDA_FUND_TO_CLIENT_SUCCESS ,PORTFOLIO_TYPE.SAVINGS ,reverseList);

            // destination account reversals
            resourceId = makerCheckerEntry.getSubResourceId();
            objectId = e.getDrawdownAccount().getId();

            //e.setResourceId(resourceId);
            //e.setObjectId(objectId);
            //savingsMovements.add(new ExcelClientData(e));

            addBalanceMovement(savingsMovements ,e ,objectId ,resourceId,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER ,PORTFOLIO_TYPE.SAVINGS,reverseList);
            // considering timestamps source account is the first transaction to happen ,so when reversing its the last that should happen

        }


        // first buy shares if only its preshare purchase ,if not dda funded then take directly from the money
        shouldProcess = SsbPaymentsHelper.shouldDoProcess(e.getStaging() ,STAGING.FAILED_ON_SHARES_PURCHASE ,allowStaging);

        // money should move out of savings account instead
        // share account zone here set resource id to shares account id
        if(shouldProcess && isPresharePurchase){

            ShareAccountPreloadingHelper shareAccountPreloadingHelper = new ShareAccountPreloadingHelper(ssbPayment, parameters,e.getAmount(), client.getId());

            // deposit excess to who ?
            // get balance here ,which is amount from share transaction .
            shareAccountPreloadingHelper.purchaseShares(true && !isDDAFunded, e, excessBalanceMoved ,isDDAFunded);

            TRANSACTION_STATUS transactionStatus = shareAccountPreloadingHelper.getTransactionStatus();

            // if it was dda funded then we should create an entry for that reversal transaction reference ?
            makerCheckerEntry = shareAccountPreloadingHelper.getMakerCheckerEntry();

            BigDecimal transactionAmount = shareAccountPreloadingHelper.getTransactionAmount();

            switch (transactionStatus){
                case SHARES_BOUGHT_SUCCESSFULLY:
                case SHARE_TRANSACTION_PENDING:
                    e.setAmount(transactionAmount);
                    break;
            }

            boolean sharesTransactionSuccess = addToShareTransactionStatus(e, transactionStatus ,true ,makerCheckerEntry);

            if(isDDAFunded && sharesTransactionSuccess){

                // some failed share transactions they would have a default null maker checker entry so need to check for that
                boolean isPresent = Optional.ofNullable(makerCheckerEntry).isPresent();

                if(isPresent) {
                    // source transfer reversal thus savings account
                    objectId = makerCheckerEntry.getSavingsId();
                    // entity id here comes off as savings transaction id
                    resourceId = makerCheckerEntry.getSubResourceId();

                    // set amount of shares purchased here for better presentation
                    //e.setResourceId(resourceId);
                    //e.setObjectId(objectId);
                    //savingsToSharesMovements.add(new ExcelClientData((e)));

                    addBalanceMovement(savingsToSharesMovements ,e ,objectId ,resourceId ,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_SHARE_TRANSFER ,PORTFOLIO_TYPE.SAVINGS ,reverseList);
                }
            }

            /**
             * check our balance from shares purchase
             * if share balance is 0 then we cant proceed to next transaction ,else if it was not funded from dda then the balance ought to be taken elsewhere .if excess balance not deposited ?
             */
            BigDecimal balance = shareAccountPreloadingHelper.balance();

            // this balance becomes our orignal deposit as well after we have paid all shares money
            e.setAmount(balance);
            e.setOrignalDeposit(balance);

        }


        /// if e is linked to fundddaaccount then transfer funds here

        /// now done with shares lets pay off loans
        /// if loans is empty then nothing happens
        List<ClientLoanAccount> clientLoanAccountList  = clientRequest.getClientLoanAccounts(client);

        if(clientLoanAccountList.isEmpty()){

            // client has no loan accounts
            // if not settle shortfall then this process should skip
            // if it was dda funded then transfer excess to settle account
            // if not dda funded then transfer whatever left to settle account
            //set id for client resource here but already set on top there

            e.setStaging(STAGING.FAILED_ON_NO_ACTIVE_LOANS);
            noActiveLoans.add(e);

            if(ssbPayment.isSettleShortfallExcess()){

                SavingsAccountHelper savingsAccountHelper = new SavingsAccountHelper();

                // settle funds should go to either account .if its same account lets see what happens
                boolean status = false;
                BigDecimal amountToSettle = e.getAmount();

                // if amount is zero then dont bother but return
                boolean isZeroBalance = ComparatorUtility.isBigDecimalZero(amountToSettle);

                if(isZeroBalance){
                    return;
                }

                Long clientSettleAccountId = 0L ;

                // if its dda funding then money would already have moved since it was transferred to a drawdown account hence do a transfer

                if(isDDAFunded){

                    SavingsAccount settlementAccount = getSettlementSavingsAccount(e ,ssbPayment.getSettleSavingsProductId());
                    SavingsAccount drawdownAccount = e.getDrawdownAccount();

                    boolean isSameAccount = settlementAccount.equals(drawdownAccount);

                    if(isSameAccount){
                        return ;
                    }

                    clientSettleAccountId = settlementAccount.getId();
                    String description = String.format("Excess funds moved from %s to %s" ,drawdownAccount.getSavingsProductName() ,settlementAccount.getSavingsProductName());

                    accountTransferParser.savingToSavingTransfer(client.getId() ,drawdownAccount ,settlementAccount ,amountToSettle ,description);
                    // lol actually no transaction taking off here lol

                    makerCheckerEntry = SavingsRequest.invokeAccountTransfer(accountTransferParser);
                    status = makerCheckerEntry.success();

                }
                else{
                     /// if not using dda funding then funds havent been moved from excel sheet to anywhere
                    /// the below would just be a deposit only
                    String description = String.format("No fund to process ,excess funds deposited");
                    parameters.setTranferDescription(description);
                    status = savingsAccountHelper.deposit(client.getId() ,ssbPayment.getSettleSavingsProductId() ,amountToSettle ,parameters);
                    makerCheckerEntry = savingsAccountHelper.getMakerCheckerEntry();
                    clientSettleAccountId = savingsAccountHelper.getSavingsAccount().getId();
                }

                if (status){
                    // source account reversal
                    objectId = makerCheckerEntry.getSavingsId();
                    resourceId = makerCheckerEntry.getTransactionId();

                    //e.setObjectId(objectId);
                    //e.setResourceId(resourceId);
                    //savingsMovements.add(new ExcelClientData(e));

                    addBalanceMovement(savingsMovements ,e ,objectId ,resourceId ,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER ,PORTFOLIO_TYPE.SAVINGS,reverseList);

                    // destination account reversal
                    objectId = clientSettleAccountId;
                    resourceId = makerCheckerEntry.getSubResourceId();

                    //e.setResourceId(resourceId);
                    //e.setObjectId(objectId);
                    //excessBalanceMoved.add(new ExcelClientData(e));

                    addBalanceMovement(excessBalanceMoved ,e ,objectId ,resourceId ,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER ,PORTFOLIO_TYPE.SAVINGS,reverseList);
                    return;
                }
                e.setObjectId(objectId);
                failedExcessBalanceDeposits.addOnFail(e,SSB_REPORT_TYPE.FAILED_DEPOSITS,PORTFOLIO_TYPE.SAVINGS, reverseList ,reverseOnFail,ssbPaymentResults);
            }
            return;
        }

        // here loans are paid from ids
        // get single loans and pay them off from dda accounts
        if(!ssbPayment.isAllowMultiLoanPayments()){

            ClientLoanAccount clientLoanAccount = clientRequest.findAccountWithSuppliedAccountNumber(clientLoanAccountList, e.getLoanAccountNumber());

            clientLoanAccountList.clear();

            boolean hasLoanAccount = Optional.ofNullable(clientLoanAccount).isPresent();

            // if has no loan account thus no matching loans then do excess funding or ?
            if(!hasLoanAccount){

                noMatchingLoans.add(e);

                // should settle excess funds here since no matching loan hence put funds to some account
                // should cater for moving funds as well
                if(ssbPayment.isSettleShortfallExcess()){

                    // whether dda funded or not we just moving funds from drawdown account to settlement account
                    List<SavingsAccount> savingsAccountList = savingsRequest.getClientSavingsAccount(e.getClient());

                    List<SavingsAccount> savingsAccountListTemp = savingsAccountList.stream().filter(e1->{
                        return e1.getSavingsProductId()==ssbPayment.getSettleSavingsProductId();
                    }).collect(Collectors.toList());

                    boolean hasDestinationAccount  = savingsAccountListTemp.stream().findFirst().isPresent();

                    if(hasDestinationAccount){

                        SavingsAccount ddaAccount = e.getDrawdownAccount();
                        SavingsAccount destinationAccount = savingsAccountListTemp.stream().findFirst().get();
                        // if destination account and settlement account the same then dont transact just skip
                        boolean sameAccount = ddaAccount.equals(destinationAccount);

                        if(sameAccount){
                            return;
                        }

                        String description = String.format("Savings transfer from own account %d to settlement account %d",ddaAccount.getId() ,destinationAccount.getId());

                        accountTransferParser.savingToSavingTransfer(e.getClient().getId() ,ddaAccount ,destinationAccount ,e.getAmount(),description);

                        makerCheckerEntry = SavingsRequest.invokeAccountTransfer(accountTransferParser);

                        if(makerCheckerEntry.success()){
                            // source transaction
                            objectId = makerCheckerEntry.getSavingsId();
                            resourceId = makerCheckerEntry.getTransactionId();
                            //e.setResourceId(resourceId);
                            //e.setObjectId(objectId);
                            //savingsMovements.add(new ExcelClientData(e));
                            addBalanceMovement(savingsMovements ,e ,objectId ,resourceId,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER ,PORTFOLIO_TYPE.SAVINGS,reverseList);
                            // destination transaction

                            objectId = destinationAccount.getId();
                            resourceId = makerCheckerEntry.getSubResourceId();
                            //e.setResourceId(objectId);
                            //e.setResourceId(resourceId);
                            //excessBalanceMoved.add(new ExcelClientData(e));

                            addBalanceMovement(excessBalanceMoved ,e ,objectId ,resourceId,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER ,PORTFOLIO_TYPE.SAVINGS,reverseList);
                            return ;
                        }
                        // failed to move excess balance no settle account
                    }
                }
                return;
            }
            clientLoanAccountList.add(clientLoanAccount);
        }

        BigDecimal amountLeftAfterAllTransaction =  e.getAmount();

        /// This is for clients doing multiloan payments so that it will just deposit once

        boolean drawdownAccountDeposited = false ;
        /// if pay loans from multi-loans but funds not deposited to drawdown account then push them to drawdown account

        int clientLoanCount = 0;
        for(ClientLoanAccount clientLoanAccount : clientLoanAccountList) {

            if(isDDAFunded) {
                // no more deposit here since we have already deposited ,so we set amount to 0.0 .

                /**
                 * Error Identification : 29/04/2022 at 1228
                 * Discovered transactions that DDA funded that have not transacted anything on shares would have hard time clearing balances
                 * Problem arise when a transaction is in the future i.e pass earlyRepayment bool
                 * So first excel client record going to repayments will keep its orignal remaining balance instead of 0.0
                 */
                BigDecimal amount = BigDecimal.ZERO;
                if(clientLoanCount==0){
                    amount = amountLeftAfterAllTransaction;
                    ++clientLoanCount;
                }
                ExcelClientData excelClientData = new ExcelClientData(client.getDisplayName(), amount, e.getNrcNumber(), e.getEmployeeNumber(), clientLoanAccount.getAccountNumber(),e.getStaging() ,e.getFundDDAAccount(),null ,  e.getCount());
                addExcelRecord(excelClientData);
                e = excelClientData;
            }

            // if not dda funded and no initial deposit to drawdown then for this list of loans deposit money to dda
            if(!drawdownAccountDeposited&& !isDDAFunded){

                // not dda funded then put money into a dda account ,remaining from shares purchase and every other event that occured before this;
                if(!MoneyHelper.depositable(amountLeftAfterAllTransaction)){
                    e.setStaging(STAGING.FAILED_ON_SAVINGS_DEPOSIT);
                    zeroBalanceDeposit.add(new ExcelClientData(e));
                    return;
                }


                ClientDrawdownAccount clientDrawdownAccount = new ClientDrawdownAccount(client, e).invoke();

                SavingsAccount drawdownSavingsAccount = clientDrawdownAccount.getDrawdownSavingsAccount();

                ProcessClientDeposits processClientDeposits = new ProcessClientDeposits(drawdownSavingsAccount, amountLeftAfterAllTransaction.doubleValue(), parameters);
                makerCheckerEntry = processClientDeposits.deposit();
                e.setAmount(amountLeftAfterAllTransaction);

                /// amount here has been changed since some of it has been deposited to share accounts
                ///e.setOrignalDeposit(newDepositAmount);

                boolean success = makerCheckerEntry.success();

                if (!success) {
                    e.setStaging(STAGING.FAILED_ON_SAVINGS_DEPOSIT);
                    failedDeposits.addOnFail(e ,SSB_REPORT_TYPE.FAILED_DEPOSITS,PORTFOLIO_TYPE.SAVINGS, reverseList ,reverseOnFail,ssbPaymentResults);
                    return;
                }

                e.setResourceId(makerCheckerEntry.getResourceId());
                e.setObjectId(makerCheckerEntry.getSavingsId());
                successfullDeposit.add(e ,SSB_REPORT_TYPE.SUCCESSFULL_DEPOSIT ,PORTFOLIO_TYPE.SAVINGS, reverseList,ssbPaymentResults);
                drawdownAccountDeposited = true ;
            }

            Long loanId = clientLoanAccount.getLoanId();
            Loan loan = LoansRequest.findLoan(loanId);
            e.setLoanAccountNumber(clientLoanAccount.getAccountNumber());
            e.setLoan(loan);

            client.setClientLoanAccount(clientLoanAccount);

            ClientDrawdownAccount clientDrawdownAccount = new ClientDrawdownAccount(client, e).invoke();

            if (!clientDrawdownAccount.hasDrawdownAccount()){
                return;
            }

            ClientSavingsExcelData clientSavingsExcelData = clientDrawdownAccount.getClientSavingsExcelData();

            clientSavingsExcelDataList.add(clientSavingsExcelData);
            /// If its multiloan payments then there is only one deposit instead of many deposits here
        }
    };

    private Consumer<ClientSavingsExcelData> processRepaymentsConsumer = (e)->{

        shouldProcess = SsbPaymentsHelper.shouldDoProcess(e.getExcelClientData().getStaging() ,STAGING.FAILED_ON_LOAN_REPAYMENT ,allowStaging);

        if(!shouldProcess){
            return ;
        }

        boolean settleShortfall  = ssbPayment.isSettleShortfallExcess();
        boolean allowEarlyRepayment = ssbPayment.isAllowEarlyRepayment() ;

        // this amount is the total available for repayment
        double amount = e.getExcelClientData().getAmount().doubleValue();

        boolean isDepositable = MoneyHelper.depositable(new BigDecimal(amount));

        // if the amount is not depositable and does not settle shortfall then skip transaction
        if(!isDepositable && !settleShortfall){
            //return ;
        }

        // get active amounts for this schedule
        Loan loan = e.getExcelClientData().getLoan();
        Long loanId = loan.getId();

        Long objectId = loanId ;
        Long transactionId = null ;

        // set zoning to loan id here son
        e.getExcelClientData().setObjectId(loanId);
        LoanRepaymentScheduleRequest loanRepaymentScheduleRequest = new LoanRepaymentScheduleRequest(loan);
        LoanRepaymentSchedule loanRepaymentSchedule = loanRepaymentScheduleRequest.activeSchedule();

        if(loanRepaymentSchedule==null){
            return;
        }
        /**
         * Added 19/09/2022
         * Allow early repayment on loans .If false on loans whose payment date is after current date will be processed
         */
        if(!allowEarlyRepayment){

            Date dueDate = loanRepaymentSchedule.getDueDate();
            boolean isFutureTransaction = transactionDateEx.before(dueDate);
            if(isFutureTransaction){
                String key = e.getExcelClientData().getNrcNumber();
                SsbPaymentsHelper.transactionBalance(balancesMap ,key, new BigDecimal(amount));
                return;
            }
        }

        BigDecimal totalLoanDue = loan.getSummary().getTotalOutstanding();
        Double periodDue = loanRepaymentSchedule.getTotalInstallmentAmountForPeriod();

        if(periodDue > totalLoanDue.doubleValue()){
            /// means will overpay the loan since total being paid is greater than overdue hence loan will get into overpay
            periodDue = totalLoanDue.doubleValue();

        }

        e.getExcelClientData().setAmount(new BigDecimal(periodDue));

        ///check infufficient balance thing ;
        SavingsAccount savingsAccount = e.getSavingsAccount();
        ////update it cause it now has stale balance we have learned
        savingsAccount = savingsRequest.savingsAccount(savingsAccount.getId());
        
        ///10000 > 6000
        /// 500 > 6000


        Double accountBalance = savingsAccount.getSummary().getAvailableBalance().doubleValue();

        if(periodDue > accountBalance){
            /// periodDue is greater here than account balance hence should check for transfer here son

            if(settleShortfall){

                Double shortfall = periodDue-accountBalance;
                periodDue = accountBalance ;

                //e.getExcelClientData().setAmount(accountBalance);

                Long settleProductId = ssbPayment.getSettleSavingsProductId();

                if(savingsAccount.getSavingsProductId() != settleProductId){

                    //what if its equal ?
                    // use settle account here son
                    List<SavingsAccount> clientSavingsAccounts = savingsRequest.getClientSavingsAccount(e.getClient());
                    SavingsAccount settleAccount = SsbPaymentsHelper.getAccountFromStreamedList(clientSavingsAccounts ,settleProductId);

                    settleAccount = savingsRequest.savingsAccount(settleAccount.getId());

                    if(settleAccount != null){

                        Double transferBalance = shortfall ;
                        Double settleAccountBalance = settleAccount.getSummary().getAccountBalance().doubleValue();

                        if(shortfall > settleAccountBalance){
                            transferBalance = settleAccountBalance;
                        }

                        e.setSavingsAccount(settleAccount);
                        
                        ProcessClientLoanRepayments processClientLoanRepayments = new ProcessClientLoanRepayments(parameters ,e ,new BigDecimal(transferBalance));

                        TRANSACTION_STATUS transactionStatus = processClientLoanRepayments.repayLoan();

                        boolean isSuccessfullLoanRepayment = transactionStatus.isSuccess();

                        if(isSuccessfullLoanRepayment){

                            MakerCheckerEntry makerCheckerEntry = processClientLoanRepayments.getMakerCheckerEntry();

                            // add to savings movements
                            addSavingsMovement(e ,makerCheckerEntry);

                            // this in turn resource id is used 
                            transactionId = makerCheckerEntry.getTransactionId();

                            e.getExcelClientData().setAmount(new BigDecimal(transferBalance));
                            e.getExcelClientData().setResourceId(transactionId);

                            // object id is loan id
                            e.getExcelClientData().setObjectId(objectId);
                            shortfallBalance.add(e.getExcelClientData() ,SSB_REPORT_TYPE.SHORTFALL_BALANCE ,PORTFOLIO_TYPE.LOANS, reverseList,ssbPaymentResults);
                        }
                    }
                }
            }
            e.setSavingsAccount(savingsAccount);
            e.getExcelClientData().setAmount(new BigDecimal(accountBalance));
            periodDue = accountBalance ;
        }


        /// carries on another money savings transfer which then needs to be cleared as well
        ProcessClientLoanRepayments processClientLoanRepayments = new ProcessClientLoanRepayments(parameters ,e ,new BigDecimal(periodDue));
        TRANSACTION_STATUS transactionStatus = processClientLoanRepayments.repayLoan();
        MakerCheckerEntry makerCheckerEntry = processClientLoanRepayments.getMakerCheckerEntry();

        switch (transactionStatus){

            case LOAN_REAPYMENT_WAITING_APPROVAL:
                e.getExcelClientData().setStaging(STAGING.SUCCESS);
                waitingApproval.add(e.getExcelClientData() ,SSB_REPORT_TYPE.LOAN_REPAYMENT_WAITING_APPROVAL ,PORTFOLIO_TYPE.LOANS, reverseList,ssbPaymentResults);
                break;

            case LOAN_REPAYMENT_FAILED:
                e.getExcelClientData().setStaging(STAGING.FAILED_ON_LOAN_REPAYMENT);
                ErrorMessageTranslator.transactionStatus(makerCheckerEntry ,transactionStatus);

                e.getExcelClientData().setTransactionStatus(transactionStatus);

                failedRepayments.addOnFail(e.getExcelClientData() ,SSB_REPORT_TYPE.FAILED_REPAYMENTS ,PORTFOLIO_TYPE.LOANS, reverseList ,reverseOnFail,ssbPaymentResults);

                e.getExcelClientData().resetTransactionStatus();
                break;
            case LOAN_REPAYMENT_SUCCESS:

                /// we need to check double transaction here savings to loan transaction
                /// all transactions will create one loan entry and another savings entry .
                /// here we need to get the savings entry as well
                addSavingsMovement(e, makerCheckerEntry);

                transactionId = makerCheckerEntry.getTransactionId();
                e.getExcelClientData().setObjectId(loanId);
                e.getExcelClientData().setResourceId(transactionId);
                e.getExcelClientData().setStaging(STAGING.SUCCESS);
                e.getExcelClientData().setPortfolioType(PORTFOLIO_TYPE.LOANS);
                e.getExcelClientData().setTransactionDate(transactionDate);

                repaidLoans.add(e.getExcelClientData() ,SSB_REPORT_TYPE.REPAID_LOANS ,PORTFOLIO_TYPE.LOANS, reverseList,ssbPaymentResults);
                break;
        }
        /**
         * Modified 29/09/2022
         * Only failed loan repayments were adding values to balance map ,now all should do so
         */
        String key = e.getExcelClientData().getNrcNumber();
        SsbPaymentsHelper.transactionBalance(balancesMap ,key, new BigDecimal(periodDue));

    };

    // get from loan transaction response the data needed to keep track of the transaction parameters
    private void addSavingsMovement(ClientSavingsExcelData e, MakerCheckerEntry makerCheckerEntry) {

        Long subEntityId = makerCheckerEntry.getSubResourceId();

        e.getExcelClientData().setResourceId(subEntityId);
        e.getExcelClientData().setObjectId(makerCheckerEntry.getSavingsId());

        //e.getExcelClientData().setPortfolioType(PORTFOLIO_TYPE.SAVINGS);
        //e.getExcelClientData().setSsbReportType(SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_LOAN_TRANSFER);

        savingsMovements.add(e.getExcelClientData() ,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_LOAN_TRANSFER ,PORTFOLIO_TYPE.SAVINGS, reverseList,ssbPaymentResults);
    }


    private Consumer<ExcelClientData> processSettleExcessConsumer = (e)->{

        // This is for clients doing multiloan payments so that it will just deposit once

        Long settleAccountProductId = ssbPayment.getSettleSavingsProductId();
        /// savings account zoning hwew for settling excess funds now
        try {

            SavingsAccount settleAccount = getSettlementSavingsAccount(e, settleAccountProductId);

            // here we have issues of transactions not found now lets settle it
            // we moving from dda account to settlement account
            // so transaction id mismatch son
            boolean isSettlementAccountPresent = Optional.ofNullable(settleAccount).isPresent();

            if(isSettlementAccountPresent) {

                Long settleAccountId = settleAccount.getId();
                // set zoning here for savings account id
                e.setResourceId(settleAccountId);
                SavingsAccount drawdownAccount = e.getDrawdownAccount();
                MakerCheckerEntry makerCheckerEntry = SsbPaymentsHelper.moveExcessBalances(balancesMap, e, accountTransferParser, drawdownAccount, settleAccount ,threshold);
                boolean hasErrors = hasErrors(makerCheckerEntry);

                if(!hasErrors){
                    // tracking transaction at source account ,reversing transaction at source account first lets see ,maybe we need to do savings movement thing
                    e.setStaging(STAGING.SUCCESS);
                    Long objectId = makerCheckerEntry.getSavingsId();
                    Long resourceId = makerCheckerEntry.getTransactionId();
                    //e.setResourceId(resourceId);
                    //e.setObjectId(objectId);
                    //excessBalanceMoved.add(new ExcelClientData(e));
                    addBalanceMovement(excessBalanceMoved ,e ,objectId ,resourceId,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER ,PORTFOLIO_TYPE.SAVINGS,reverseList);

                    // destination account reversal ,this then completes a working cycle with no defects on reversal lets hope
                    objectId = settleAccountId;
                    resourceId = makerCheckerEntry.getSubResourceId();
                    //e.setObjectId(objectId);
                    //e.setResourceId(resourceId);
                    //savingsMovements.add(new ExcelClientData(e));
                    addBalanceMovement(savingsMovements,e ,objectId ,resourceId,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER ,PORTFOLIO_TYPE.SAVINGS,reverseList);
                    return;
                }
                //makerCheckerEntry.
                //failedExcessBalanceDeposits.add(new ExcelClientData(e));
            }
        }
        catch(NullPointerException  n){
            n.printStackTrace();
        }

    };

    // function to add balance movement to a list ,testing phase function
    // added 28/07/2022
    private void addBalanceMovement(SSbTransactionsAdapter ssbTransactionsAdapter ,ExcelClientData e ,Long objectId ,Long resourceId ,SSB_REPORT_TYPE ssbReportType ,PORTFOLIO_TYPE portfolioType, SSbTransactionsAdapter reverseList){
        e.setObjectId(objectId);
        e.setResourceId(resourceId);
        ssbTransactionsAdapter.add(e);
    }


    /**
     * Added 16/03/2022
     */
    private Consumer<ExcelClientData> setTransactionDate = (e->{
        String transactionDate = ssbPayment.getPostedDate();
        e.setTransactionDate(transactionDate);
    });


    private SavingsAccount getSettlementSavingsAccount(ExcelClientData e, Long settleAccountProductId) {

        SavingsAccount savingsAccount[] = {null} ;

        Optional.ofNullable(e.getClient()).ifPresent(c->{
            List<SavingsAccount> clientSavingsAccounts = savingsRequest.getClientSavingsAccount(e.getClient());
            savingsAccount[0] = SsbPaymentsHelper.getAccountFromStreamedList(clientSavingsAccounts ,settleAccountProductId);
        });
        return savingsAccount[0];
    }

    /**
     * Added 14/09/2022 at 0936
     * validateCredentials before proceeding with transaction
     * @param ssbPayment
     *
     */
    private void validateCredentials(SsbPayment ssbPayment ,String tenant){
        String username = ssbPayment.getUsername();
        String password = ssbPayment.getPassword();
        LoginData loginData = CredentialsRequest.login(username ,password);

        Supplier supplier = FailedCredentialsException::new;
        Optional.ofNullable(loginData).orElseThrow(supplier);

        String credentials[] = {loginData.getUsername(),password};

        if (com.wese.weseaddons.helper.Constants.passwordMap.containsKey(tenant)){
            com.wese.weseaddons.helper.Constants.passwordMap.replace(tenant ,credentials);
            return;
        }
        com.wese.weseaddons.helper.Constants.passwordMap.put(tenant,credentials);

    }

    @RequestMapping(method = RequestMethod.POST ,consumes={MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ObjectNode postFinancialTransactions(@RequestPart MultipartFile file ,@RequestPart String ssb ){

        ssbPayment = SsbPayment.fromHttpResponse(ssb);
        if(ssbPayment==null){
            return ResponseBuilder.genericFailure("Failed to get appropiate parameters from the user");
        }
        /**
         * Added 14/10/2022 at 0403
         */
        reverseOnFail = ssbPayment.isReverseOnFail();

        String tenantIdentifier = ThreadContext.getTenant();
        threshold = ssbPayment.getThreshold();

        validateCredentials(ssbPayment,tenantIdentifier);

        init();

        allowStaging = ssbPayment.isAllowStaging();

        transactionDate = ssbPayment.getPostedDate();
        transactionDateEx = TimeHelper.stringToDate(transactionDate);

        SSBPaymentsInit.getInstance().setTransactionDate(transactionDate);

        isDDAFunded = ssbPayment.isDDAFunded();

        parameters = new Parameters(ssbPayment.getDescription() ,ssbPayment.getPostedDate() ,ssbPayment.getOfficeId());

        this.accountTransferParser = new AccountTransferParser(parameters);

        boolean processStartedSuccessfully = SSBPaymentsInit.getInstance().addNewProcess(tenantIdentifier);

        if(!processStartedSuccessfully){
            return ObjectNodeHelper.statusNodes(processStartedSuccessfully).put("message","Failed to start new processing ,current process not yet cleared");
        }

        SSBPaymentsWorkbook ssbPaymentsWorkbook = null ;
        SSBPaymentsWorkbook fundPaymentsWorkbook = null ;

        try{
            //WorkbookFactory.create(file);
            ssbPaymentsWorkbook = new SSBPaymentsWorkbook(file.getInputStream() ,ssbPayment);
            ssbPaymentsWorkbook.readAccountsSheetEx();

            if(isDDAFunded){
                /// read dda funded sheet
                fundPaymentsWorkbook = new SSBPaymentsWorkbook(file.getInputStream() ,ssbPayment);
                fundPaymentsWorkbook.readDDAFundsSheetEx();
            }
        }

        catch (IOException e){
            //// some exception here
            System.err.println("--------------any error caught ?");
            e.printStackTrace();
            return ResponseBuilder.genericFailure("Failed to read the data file ,make sure file is a valid excel file");
        }

        excelClientDataList = ssbPaymentsWorkbook.getExcelClientDataList();

        this.clientRequest = new ClientRequest(parameters);

        clientRequest.loadClients();

        this.savingsRequest = new SavingsRequest(parameters);

        savingsRequest.loadSavingsAccounts();

        if(isDDAFunded){

            fundDDAAccountList = fundPaymentsWorkbook.getFundDDAAccountsList();
            fundDDAAccountList.stream().forEach(processDepositFundDDAConsumer);
        }

        if (!excelClientDataList.isEmpty()) {

            excelClientDataList.stream().forEach(setTransactionDate);

            Predicate isDDAFundedPredicate = (e)->{
              return isDDAFunded ;
            };

            excelClientDataList.stream().filter(isDDAFundedPredicate).forEach(linkToFundDDAConsumer);

            excelClientDataList.stream().filter(e-> excelClientDataList.contains(e)).collect(Collectors.toSet())
                    .forEach(processDepositsConsumer);

        }

        if(!clientSavingsExcelDataList.isEmpty()) {
            clientSavingsExcelDataList.stream().filter(e->clientSavingsExcelDataList.contains(e)).collect(Collectors.toSet())
                    .forEach(processRepaymentsConsumer);

            // can we stream this as well to set transaction date ?
        }

        if(ssbPayment.isSettleShortfallExcess()){
            excelClientDataList.stream().forEach(processSettleExcessConsumer);
        }

        ObjectNode mainObjectNode = ResponseBuilder.getObjectNode();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode mainArrayNode = objectMapper.createArrayNode();

        ssbPaymentResults = new SsbPaymentsResults();

        /// these should be filled in specific order so as to reverse them successfully
        fillSpecificNode(excessBalanceMoved ,SSB_REPORT_TYPE.EXCESS_BALANCE_MOVED ,"SUCCESS" ,"Savings Account ,excess balance moved",PORTFOLIO_TYPE.SAVINGS);
        fillSpecificNode(repaidLoans, SSB_REPORT_TYPE.REPAID_LOANS,"SUCCESS", "Successful loan repayment" ,PORTFOLIO_TYPE.LOANS);

        fillSpecificNode(invalidSearchData, SSB_REPORT_TYPE.INVALID_SEARCH_DATA ,"FAILED", "Invalid search data",PORTFOLIO_TYPE.DISCARD);
        fillSpecificNode(failedRepayments,SSB_REPORT_TYPE.FAILED_REPAYMENTS , "FAILED", "Failed Loan Repayment ,possibly insufficient balance" ,PORTFOLIO_TYPE.LOANS);
        fillSpecificNode(noActiveLoans, SSB_REPORT_TYPE.NO_ACTIVE_LOANS,"SKIPPED", "No active loan",PORTFOLIO_TYPE.CLIENTS);
        fillSpecificNode(noMatchingLoans, SSB_REPORT_TYPE.NO_MATCHING_LOANS,"FAILED", "No active loan with Id found",PORTFOLIO_TYPE.CLIENTS);
        fillSpecificNode(waitingApproval, SSB_REPORT_TYPE.LOAN_REPAYMENT_WAITING_APPROVAL,"PENDING", "Waiting for approval",PORTFOLIO_TYPE.DISCARD);
        fillSpecificNode(adjustedDeposit,SSB_REPORT_TYPE.ADJUSTED_DEPOSIT, "SUCCESS", "Adjusted for deposit",PORTFOLIO_TYPE.SAVINGS);
        fillSpecificNode(invalidNrcNumber ,SSB_REPORT_TYPE.INVALID_NRC_SEARCH_NUMBER ,"FAILED" ,"Client ID not found in the system" ,PORTFOLIO_TYPE.DISCARD);
        fillSpecificNode(failedDeposits ,SSB_REPORT_TYPE.FAILED_DEPOSITS,"FAILED" ,"Failed deposit ,no existing savings account",PORTFOLIO_TYPE.CLIENTS);

        // added 14/04/2022
        fillSpecificNode(failedExcessBalanceDeposits ,SSB_REPORT_TYPE.FAILED_DEPOSITS,"FAILED" ,"Failed to settle excess balance in account",PORTFOLIO_TYPE.SAVINGS);


        fillSpecificNode(noValidDrawdownAccount ,SSB_REPORT_TYPE.NO_VALID_SAVINGS_ACCOUNT,"FAILED","No valid account type for this client ,please create one to proceed",PORTFOLIO_TYPE.CLIENTS);
        fillSpecificNode(savingsAccountPreloaded,SSB_REPORT_TYPE.SAVINGS_ACCOUNT_PRELOADED,"SUCCESS" ,"Savings account successfully preloaded",PORTFOLIO_TYPE.SAVINGS);
        fillSpecificNode(maximumSharesReached ,SSB_REPORT_TYPE.SHARES_MAXIMUM_REACHED ,"SKIPPED" ,"Shares account reached maximum allowed",PORTFOLIO_TYPE.SHARES);
        fillSpecificNode(sharesTransactionPending ,SSB_REPORT_TYPE.SHARES_TRANSACTION_PENDING ,"PENDING" ,"Share purchase transaction pending",PORTFOLIO_TYPE.SHARES);
        fillSpecificNode(sharesTransactionSuccessfull ,SSB_REPORT_TYPE.SHARES_TRANSACTION_SUCCESSFULL ,"SUCCESS" ,"Share purchase transaction successfull",PORTFOLIO_TYPE.SHARES);
        fillSpecificNode(noActiveSharesAccount ,SSB_REPORT_TYPE.NO_ACTIVE_SHARES_ACCOUNT ,"FAILED" ,"Client has no active share account",PORTFOLIO_TYPE.CLIENTS);
        fillSpecificNode(sharesInternalError ,SSB_REPORT_TYPE.SHARES_INTERNAL_ERROR ,"FAILED" ,"Internal error ,failed to buy shares", PORTFOLIO_TYPE.SHARES);
        fillSpecificNode(shortfallBalance ,SSB_REPORT_TYPE.SHORTFALL_BALANCE ,"SUCCESS" ,"Loan paid successfully ,but with a shortfall",PORTFOLIO_TYPE.LOANS);
        fillSpecificNode(successfullDeposit ,SSB_REPORT_TYPE.SUCCESSFULL_DEPOSIT ,"SUCCESS" ,"Drawdown account deposited successfully",PORTFOLIO_TYPE.SAVINGS);

        /**
         * Added 17/10/2022 at 0149
         */
        fillSpecificNode(ddaFundNotValid ,SSB_REPORT_TYPE.DDA_FUNDER_NOT_VALID ,"FAILED",SSB_REPORT_TYPE.DDA_FUNDER_NOT_VALID.getCode() ,PORTFOLIO_TYPE.SAVINGS);

        // added 08/02/2022
        fillSpecificNode(zeroBalanceDeposit ,SSB_REPORT_TYPE.ZERO_BALANCE_DEPOSIT ,"SKIPPED" ,"Zero Balance Deposit ,operation skipped" ,PORTFOLIO_TYPE.SAVINGS);
        fillSpecificNode(ddaDepositSuccess ,SSB_REPORT_TYPE.DDA_FUND_DEPOSIT_SUCCESS ,"SUCCESS" ,"DDA Fund deposited succesfully" ,PORTFOLIO_TYPE.SAVINGS);
        fillSpecificNode(ddaDepositFailed ,SSB_REPORT_TYPE.DDA_FUND_DEPOSIT_FAILED ,"SKIPPED" ,"DDA Fund deposit failed ,possible cause zero balance deposit" ,PORTFOLIO_TYPE.SAVINGS);

        //ssbPaymentResults.add(SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_LOAN_TRANSFER ,savingsMovements);
        // added 03/02/2022
        fillSpecificNode(ddaFundingSuccess ,SSB_REPORT_TYPE.DDA_FUND_TO_CLIENT_SUCCESS ,"SUCCESS" ,"DDA Fund to client DDA transfer successful" ,PORTFOLIO_TYPE.SAVINGS);
        fillSpecificNode(ddaFundingFailed ,SSB_REPORT_TYPE.DDA_FUND_TO_CLIENT_FAILED ,"FAILED" ,"DDA Fund to client account transfer failed ,possible cause insufficient funds in DDA Account",PORTFOLIO_TYPE.SAVINGS);



        for(ObjectNode o : objectNodesList) {
            mainArrayNode.add(o);
        }

        // these should skip presentation phase they cloud the screen so much
        fillSpecificNode(savingsMovements ,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER,"SUCCESS" ,"Savings to Savings Account Movement of funds",PORTFOLIO_TYPE.SAVINGS);
        fillSpecificNode(savingsToSharesMovements ,SSB_REPORT_TYPE.SUCCESSFULL_SAVINGS_SHARE_TRANSFER ,"SUCCESS" ,"Savings to share transfer successfull" ,PORTFOLIO_TYPE.SAVINGS);

        mainObjectNode.putPOJO("pageItems", mainArrayNode);
        SSBPaymentsInit.getInstance().updateProcessStatus(tenantIdentifier, PROCESS_STATUS.FINISHED);
        SSBPaymentsInit.getInstance().saveProcessResults(tenantIdentifier ,ssbPaymentResults, mainObjectNode);

        return mainObjectNode ;

    }


    private boolean skipNode(SSB_REPORT_TYPE ssbReportType){

        boolean skip = false ;
        switch (ssbReportType){
            case SUCCESSFULL_SAVINGS_SHARE_TRANSFER:
            case SUCCESSFULL_SAVINGS_TO_SAVINGS_TRANSFER:
            case SUCCESSFULL_SAVINGS_LOAN_TRANSFER:
                skip = true ;
                break;
        }
        return skip ;
    }

    public void fillSpecificNode(SSbTransactionsAdapter list ,SSB_REPORT_TYPE ssbReportType , String status ,String description ,PORTFOLIO_TYPE portfolioType) {

        // skip specific nodes ,so that some internal transaction node are not show on screen so as to not scatter it with transactions
        boolean skipNode = skipNode(ssbReportType);

        for (ExcelClientData excelClientData : list) {

            Optional.ofNullable(excelClientData).ifPresent(e -> {
                if(!skipNode) {
                    ObjectNode objectNode = ResponseBuilder.getObjectNode();
                    objectNode.put("count", excelClientData.getCount());
                    objectNode.put("amount", excelClientData.getAmount());
                    objectNode.put("externalId", excelClientData.getNrcNumber());
                    objectNode.put("status", status);
                    objectNode.put("portfolioType", portfolioType.getCode());
                    objectNode.put("accountNumber", excelClientData.getLoanAccountNumber());
                    objectNode.put("clientName", excelClientData.getName());

                    /**
                     * Modified 29/09/2022 at 0954
                     * Need to make errors more descriptive
                     * objectNode.put("resultsDescription", description);
                     */
                    objectNode.put("resultsDescription",TransactionStatusHelper.description(ssbReportType ,e));
                    objectNode.put("resourceId", excelClientData.getResourceId());
                    objectNode.put("timestamp", excelClientData.getTimestamp());
                    objectNode.put("reportType", ssbReportType.name());
                    objectNode.put("reversed", false);
                    objectNode.put("objectId", excelClientData.getObjectId());

                    objectNodesList.add(objectNode);
                }

                // process these steps so as to not have portfolioType null
                excelClientData.setStatus(STATUS.valueOf(status));
                excelClientData.setSsbReportType(ssbReportType);

                excelClientData.setStatusDescription(description);
                excelClientData.setPortfolioType(portfolioType);

            });
        }
        ssbPaymentResults.add(ssbReportType ,list);
    }

    public void addExcelRecord(ExcelClientData excelClientData){

        Predicate<ExcelClientData> isLoanNumber = (e)-> {
            String accountNumber = Optional.ofNullable(e.getLoanAccountNumber()).orElse("");
            return excelClientData.getLoanAccountNumber().equalsIgnoreCase(accountNumber);
        };

        List loansList = excelClientDataList.stream().filter(isLoanNumber).collect(Collectors.toList());
        /**
         * Modified 29/09/2022 at 1237
         * If loan number the same then dont add it to excelrecords
         */
        if(loansList.isEmpty()){
            excelClientDataList.add(excelClientData);
        }
    }

    // return true if it has status ,needs resolving
    private boolean getMakerCheckerStatus(MakerCheckerEntry makerCheckerEntry){

        boolean failed[] = {false} ;
        Optional.ofNullable(makerCheckerEntry).ifPresent(e->{
            failed[0] = makerCheckerEntry.hasErrors();
        });

        // if it has errors then it has failed
        return failed[0] ;
    }

    private boolean hasErrors(MakerCheckerEntry makerCheckerEntry){

        boolean errors[] = {true} ;
        Optional.ofNullable(makerCheckerEntry).ifPresent(e->{
            errors[0] = makerCheckerEntry.hasErrors();
        });

        /**
         * if it has errors then it has failed
         */
        return errors[0] ;
    }

    private boolean addToShareTransactionStatus(ExcelClientData e ,TRANSACTION_STATUS transactionStatus ,boolean setStaging ,MakerCheckerEntry makerCheckerEntry){

        Long resourceId = null ;
        Long objectId = null ;

        // set resource ids
        switch (transactionStatus){
            case SHARES_BOUGHT_SUCCESSFULLY:
            case SHARE_TRANSACTION_PENDING:
                resourceId = makerCheckerEntry.getTransactionId();
                objectId = makerCheckerEntry.getCommandId() ;
                e.setResourceId(resourceId);
                e.setObjectId(objectId);
                break;
        }

        boolean proceed = true ;

        switch (transactionStatus){
            case SHARE_TRANSACTION_PENDING:
                e.setStaging(STAGING.STOPPED_ON_SHARES_PENDING);
                sharesTransactionPending.add(e,SSB_REPORT_TYPE.SHARES_TRANSACTION_PENDING ,PORTFOLIO_TYPE.SHARES ,reverseList,ssbPaymentResults);
                break;
            case MAXIMUM_SHARES_REACHED:
                e.setAmount(BigDecimal.ZERO);
                e.setStaging(STAGING.SUCCESS);
                maximumSharesReached.add(e ,SSB_REPORT_TYPE.SHARES_MAXIMUM_REACHED,PORTFOLIO_TYPE.SHARES ,reverseList,ssbPaymentResults);
                break;
            case SHARES_BOUGHT_SUCCESSFULLY:
                e.setStaging(STAGING.SUCCESS);
                sharesTransactionSuccessfull.add(e ,SSB_REPORT_TYPE.SHARES_TRANSACTION_SUCCESSFULL ,PORTFOLIO_TYPE.SHARES ,reverseList,ssbPaymentResults);
                break;
            case NO_ACTIVE_SHARE_ACCOUNT:
                e.setStaging(STAGING.FAILED_ON_SHARES_PURCHASE);
                noActiveSharesAccount.addOnFail(e ,SSB_REPORT_TYPE.NO_ACTIVE_SHARES_ACCOUNT ,PORTFOLIO_TYPE.SHARES, reverseList , reverseOnFail,ssbPaymentResults);
                proceed = false ;
                makerCheckerEntry = null ;
                break;
            case SHARES_INTERNAL_ERROR:
                e.setStaging(STAGING.FAILED_ON_SHARES_PURCHASE);
                String message = ErrorMessageTranslator.getMessage(makerCheckerEntry);
                transactionStatus.setCode(message);
                e.setTransactionStatus(transactionStatus);
                sharesInternalError.addOnFail(e ,SSB_REPORT_TYPE.SHARES_INTERNAL_ERROR ,PORTFOLIO_TYPE.SHARES, reverseList ,reverseOnFail,ssbPaymentResults);
                e.resetTransactionStatus();
                proceed= false;
            case NO_PRESHARE:
                break;

        }
        return proceed;
    }

    private class ClientDrawdownAccount {

        private boolean hasDrawdownAccount;
        private Client client;
        private ExcelClientData e;
        private Long drawdownId;
        private SavingsAccount drawdownSavingsAccount;
        private ClientSavingsExcelData clientSavingsExcelData;

        public ClientDrawdownAccount(Client client, ExcelClientData e) {
            this.client = client;
            this.e = e;
        }

        boolean hasDrawdownAccount() {
            return hasDrawdownAccount;
        }

        public Long getDrawdownId() {
            return drawdownId;
        }

        public SavingsAccount getDrawdownSavingsAccount() {
            return drawdownSavingsAccount;
        }

        public ClientSavingsExcelData getClientSavingsExcelData() {
            return clientSavingsExcelData;
        }

        public ClientDrawdownAccount invoke() {

            List<SavingsAccount> clientSavingsAccounts = savingsRequest.getClientSavingsAccountsEx(client);

            /// this is a drawdown account here son
            drawdownId = ssbPayment.getDrawdownProductId();

            //savings account zoning set
            e.setResourceId(drawdownId);

            this.drawdownSavingsAccount = SsbPaymentsHelper.getAccountFromStreamedList(clientSavingsAccounts, drawdownId);

            if(drawdownSavingsAccount==null){
                e.setAmount(BigDecimal.ZERO);
                //noValidDrawdownAccount.add(new ExcelClientData(e));
                e.setFailed(true);
                hasDrawdownAccount = false;
                return this;
            }

            e.setClient(client);
            Long accountId = drawdownSavingsAccount.getId();
            e.setObjectId(accountId);
            e.setResourceId(accountId);

            this.clientSavingsExcelData = new ClientSavingsExcelData(e, drawdownSavingsAccount, client);

            e.setDrawdownAccount(drawdownSavingsAccount);
            hasDrawdownAccount = true;
            return this;
        }
    }

    private class SharePurchase {

        private ExcelClientData e;
        private Client client;
        private BigDecimal newDepositAmountBg;
        private TRANSACTION_STATUS transactionStatus;
        private boolean proceed;
        private boolean settleExcessBalance= false;
        private SSbTransactionsAdapter resultsAdaptor = null ; // excess balance moved


        public SharePurchase(ExcelClientData e, Client client ,SSbTransactionsAdapter resultsAdaptor , boolean settleExcessBalance) {
            this.e = e;
            this.client = client;
            this.resultsAdaptor = resultsAdaptor;
            this.settleExcessBalance =settleExcessBalance ;
        }

        public BigDecimal getNewDepositAmountBg() {
            return newDepositAmountBg;
        }

        public TRANSACTION_STATUS getTransactionStatus() {
            return transactionStatus;
        }

        public boolean isProceed() {
            return proceed;
        }

        public SharePurchase invoke() {

            ShareAccountPreloadingHelper shareAccountPreloadingHelper = new ShareAccountPreloadingHelper(ssbPayment, parameters, e.getAmount(), client.getId());
            shareAccountPreloadingHelper.purchaseShares(settleExcessBalance, e, resultsAdaptor ,isDDAFunded);
            newDepositAmountBg = shareAccountPreloadingHelper.balance();
            ShareAccount shareAccount = shareAccountPreloadingHelper.getShareAccount();
            MakerCheckerEntry makerCheckerEntry = shareAccountPreloadingHelper.getMakerCheckerEntry();

            // added 30/03/2022 make share account id to be set to an objectid there
            Optional.ofNullable(makerCheckerEntry).ifPresent(e->{
                makerCheckerEntry.setCommandId(shareAccount.getId());
            });

            //System.err.println("-------------------account balance here is ---------------"+newDepositAmountBg.doubleValue());
            transactionStatus = shareAccountPreloadingHelper.getTransactionStatus();
            proceed = addToShareTransactionStatus(e, transactionStatus, false ,makerCheckerEntry);
            return this;
        }
    }
}
