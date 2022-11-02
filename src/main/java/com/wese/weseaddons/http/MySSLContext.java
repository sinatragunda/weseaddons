package com.wese.weseaddons.http;


import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;


import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;

import com.wese.weseaddons.helper.Constants;



public class MySSLContext {
    private KeystoreData keystoreData ;
    private KeyStore keystore ;
    private SSLContext sslContext ;
    
    public MySSLContext(){
        
        init();
        
    }

    public MySSLContext(boolean crb){

        this.keystoreData = new KeystoreData(crb);
        keystoreData.init();
        keystore = keystoreData.getKeyStore();
    }
    
    public void init(){
        
        this.keystoreData = new KeystoreData();
        keystoreData.init();
        keystore = keystoreData.getKeyStore();
    
    }
    
    public SSLContext getSSLContext(){
        
        try {
            char[] password = "XYZ123".toCharArray();

            //SSLContexts.custom().loadKeyMaterial(new File(keystoreData.getKeyStoreFilename()),password ,new Sign)
            sslContext = SSLContexts.custom().loadTrustMaterial(new File(keystoreData.getKeyStoreFilename()), password,new TrustSelfSignedStrategy()).build();
        
        } 
        catch (NoSuchAlgorithmException e) {
            
            System.out.println(e);
        }
         catch (KeyStoreException e) {
            
            System.out.println(e);
        }
        
         catch (CertificateException e) {
            
            System.out.println(e);
        }
        
         catch (KeyManagementException e) {
            
            System.out.println(e);
        }
        
        catch(IOException iOException){
            
            System.out.println(iOException);
        }
        
        
        return sslContext ;
    }

}
