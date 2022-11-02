package com.wese.weseaddons.http;

import com.wese.weseaddons.WeseaddonsApplication;
import com.wese.weseaddons.helper.Constants;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Objects;


public class KeystoreData {

    private String filename;

    private String keyname =null;
    private InputStream inputStream ;
    private KeyStore keyStore ;
    private X509Certificate x509Certificate ;

    public KeystoreData() {

        initKeystoreFilename();

    }

    public KeystoreData(boolean crb){
        initKeystoreFilenameForCrb();
    }

    public void init(){

        File file = new File(filename);
        if(!file.exists()){
            System.err.println("Keystore is invalid");
        }

        char[] keyStorePassword = "XYZ123".toCharArray();

        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            inputStream = new FileInputStream(file);

            if(inputStream==null){

                System.err.println("InputStream is null");
                return ;

            }


            if(keyStorePassword.length==0){
                System.err.println("Password is null");
                return ;

            }

            keyStore.load(inputStream, keyStorePassword);
            createKeyEntryFromKeyStore(keyStore);

        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException);
        }

        catch(IOException iOException){

            System.out.println("They is an error heere"+iOException);
        }

        catch(NoSuchAlgorithmException noSuchAlgorithmException){

            System.err.println(noSuchAlgorithmException);
        }
        catch(CertificateException certificateException){

            System.err.println(certificateException);
        }

        catch(KeyStoreException keyStoreException){

            System.err.println(keyStoreException);
        }

    }

    public KeyStore getKeyStore(){

        Objects.requireNonNull(keyStore);
        return keyStore ;

    }

    public String getKeyStoreFilename(){
        return this.filename ;
    }

    public void initKeystoreFilename(){

        File file = null ;

        for(String keystoreFileLocation : Constants.keystoreFileLocations) {

            file = new File(keystoreFileLocation);
            if(file.exists()) {
                this.filename = keystoreFileLocation ;
                return ;
            }
        }
    }


    public void initKeystoreFilenameForCrb(){

        File file = new File("C:\\Program Files\\Java\\jre1.8.0_191\\lib\\security\\cacerts") ;

        if(file.exists()) {
            this.filename = file.getPath() ;
            return ;
        }

        System.err.println("File not found");

    }

    public void createKeyEntryFromKeyStore(KeyStore keyStore){

        Enumeration<String> enumeration = null ;
        try{

            String enumString = null ;
            enumeration = keyStore.aliases();

            while(enumeration.hasMoreElements()){

                enumString = enumeration.nextElement();

                if(!keyStore.isKeyEntry(enumString)){
                    return ;
                }

                keyname= enumString ;
            }
        }

        catch(KeyStoreException keyStoreException){

            System.out.println(keyStoreException);
        }

        initX509Certification();
    }

    public void initX509Certification(){

        try{

            x509Certificate = (X509Certificate)keyStore.getCertificate(keyname);

        }

        catch(KeyStoreException keyStoreException){

            System.out.println(keyStoreException);
        }
    }

    public X509Certificate getX509Certificate(){
        return x509Certificate ;
    }
}
