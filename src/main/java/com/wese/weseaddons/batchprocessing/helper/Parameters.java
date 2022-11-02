

package com.wese.weseaddons.batchprocessing.helper;
import com.wese.weseaddons.batchprocessing.client.Client;
import com.wese.weseaddons.batchprocessing.client.LoanProduct;
import com.wese.weseaddons.batchprocessing.wese.Office;

import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.wese.weseaddons.utility.ThreadContext;


public class Parameters {

    private String tenantIdentifier ;
    private LoanProduct loanProduct ;
    private Office office ;
    private Client client ;
    private String postedDate ;
    private String tranferDescription ;
    private String authoriser ;
    private String filename ;
    private InputStream file ;
    private int officeId ;


    public Parameters(String description ,String postedDate,int officeId){
        this.postedDate = postedDate ;
        this.tranferDescription = description ;
        this.tenantIdentifier = ThreadContext.getTenant();
        this.officeId = officeId ;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        this.tenantIdentifier = tenantIdentifier;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
    
    public int getOfficeId() {
        return officeId ;
    }

    public Office getOffice() {
        return office;
    }
    
    public InputStream getFile() {
        return file ;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getTenantIdentifier() {
        return tenantIdentifier;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getTranferDescription() {
        return tranferDescription;
    }

    public void setTranferDescription(String tranferDescription) {
        this.tranferDescription = tranferDescription;
    }


    public LoanProduct getLoanProduct() {
        return loanProduct;
    }

    public String getAuthoriser() {
        return authoriser;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
