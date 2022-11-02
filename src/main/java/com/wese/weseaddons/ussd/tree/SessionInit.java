package com.wese.weseaddons.ussd.tree;

import com.wese.weseaddons.networkrequests.ClientRequest;
import com.wese.weseaddons.networkrequests.LoanProductsRequest;
import com.wese.weseaddons.networkrequests.LoansRequest;
import com.wese.weseaddons.networkrequests.SavingsRequest;
import com.wese.weseaddons.pojo.Client;
import com.wese.weseaddons.pojo.Loan;
import com.wese.weseaddons.pojo.LoanProducts;
import com.wese.weseaddons.pojo.SavingsAccount;
import com.wese.weseaddons.ussd.session.Session;
import com.wese.weseaddons.ussd.session.SessionDetails;


import java.util.List;

public class SessionInit {

    private List<Client> clientsList ;
    public static SessionInit instance ;
    private ClientRequest clientRequest ;

    public static SessionInit getInstance(){

        if(instance==null){

            instance = new SessionInit();
        }

        return instance ;
    }
    

    public void loadClients(){

        this.clientRequest = new ClientRequest();
        //clientRequest.loadClientsAsync();
        this.clientsList = clientRequest.getClientList();
      
    }

    public Runnable loadSavingsAccountsAsync(Session session){

        Runnable runnable = ()->{
            Client client = session.getClient();
            SavingsRequest savingsRequest = new SavingsRequest();
            savingsRequest.getClientSavingsAccount(client);
            List<SavingsAccount> savingsAccountList = savingsRequest.getSavingsAccounts() ;
            session.setClientSavingsAccountList(savingsAccountList);
            SessionDetails.getInstance().updateSessionObject(session);

        };

        return runnable ;

    }
    public Runnable loadLoanAccountsAsync(Session session){

        Runnable runnable = ()->{

            Client client = session.getClient();
            LoansRequest loansRequest = new LoansRequest(session);
            loansRequest.loadClientLoans(client.getId());
            List<Loan> loanList = loansRequest.getLoanList() ;
            session.setLoansList(loanList);
            SessionDetails.getInstance().updateSessionObject(session);

        };

        return runnable ;

    }

    public Runnable loanProductsRequestAsync(Session session){

        Runnable runnable = ()->{
            LoanProductsRequest loanProductsRequest = new LoanProductsRequest(session.getTenantIdentifier());
            List<LoanProducts> loanProductsList = loanProductsRequest.getLoanGeneratorProducts();
            session.setLoanProductsList(loanProductsList);
            SessionDetails.getInstance().updateSessionObject(session);
        };

        return runnable;

    }
    public List<Client> getClientsList(){

        return clientsList ;
    }
}
