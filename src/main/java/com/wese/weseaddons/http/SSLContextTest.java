/*Created by Sinatra Gunda
  At 1:18 PM on 2/18/2020 */

package com.wese.weseaddons.http;

import org.springframework.beans.factory.annotation.Value;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SSLContextTest {


    public static SSLContext getSllContext(){

        SSLContext sslContext = null ;

        try{

            InputStream is = new FileInputStream("C:\\Users\\user\\Desktop\\crb.crt");
            //InputStream is = new FileInputStream("C:\\Users\\HP\\Desktop\\crb.cert");
// You could get a resource as a stream instead.


            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate caCert = (X509Certificate)cf.generateCertificate(is);

            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null); // You don't need the KeyStore instance to come from a file.
            ks.setCertificateEntry("caCert", caCert);

            tmf.init(ks);

            sslContext = SSLContext.getInstance("TLS");

            sslContext.init(null, tmf.getTrustManagers(), null);

        }

        catch (NoSuchAlgorithmException | IOException | CertificateException | KeyStoreException | KeyManagementException f){

            f.printStackTrace();
        }


        return sslContext ;


    }

}
