package com.wese.weseaddons.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.utility.ThreadContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.PrettyPrinter;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.json.JSONObject;


public class HttpPutReq {

    private MySSLContext mySslContext ;
    
    HttpPut createConnectivity(String restUrl){
        
        HttpPut put = new HttpPut(restUrl);

        String tenantIdentifier = ThreadContext.getTenant();

        String[] credentials = Constants.credentialsMap();
        String username = credentials[0];
        String password = credentials[1];
        String auth=new StringBuffer(username).append(":").append(password).toString();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        put.setHeader("AUTHORIZATION", authHeader);
        put.setHeader("Content-Type", "application/json");
        put.setHeader("Fineract-Platform-TenantId", tenantIdentifier);
        // post.setHeader("X-Stream" , "true");
        return put;
    }
    
    
    String executeHttpRequest(JSONObject jsonData,  HttpPut httpPut)  throws UnsupportedEncodingException, IOException
    {


        mySslContext = new MySSLContext();
        
        @SuppressWarnings("deprecation")
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                mySslContext.getSSLContext(),
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build()
                ;
        
        PoolingHttpClientConnectionManager phccm = new PoolingHttpClientConnectionManager(registry);
        phccm.setMaxTotal(2000);
       

        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(phccm)
                .build();



        httpPut.setEntity(new StringEntity(jsonData.toString()));
        
        HttpResponse httpResponse = httpclient.execute(httpPut);
        HttpEntity httpEntity = httpResponse.getEntity() ;
        String responseString = EntityUtils.toString(httpEntity);

        return responseString ;
        
        
       
    }
    
    String executeReq(JSONObject jsonData, HttpPut httpPut)
    {
        try{
            return executeHttpRequest(jsonData, httpPut);
        }
        catch (UnsupportedEncodingException e){
            System.out.println("error while encoding api url : "+e);
        
        }
        catch (IOException e){
            System.out.println("ioException occured while sending http request : "+e);
        }
        catch(Exception e){
            System.out.println("exception occured while sending http request : "+e);
        }
        finally{
            httpPut.releaseConnection();
        }
        
        return null ;
    }
}
