package com.wese.weseaddons.crb.httpex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.wese.weseaddons.enumerations.AUTHENTICATION_TYPE;
import com.wese.weseaddons.errors.springexceptions.NetworkConnectionException;
import com.wese.weseaddons.http.MySSLContext;
import com.wese.weseaddons.http.SSLContextTest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;


public class HttpPostReqEx {

    private MySSLContext mySslContext ;

    HttpPost createConnectivity(String restUrl ,String token ,AUTHENTICATION_TYPE authenticationType){


        HttpPost post = new HttpPost(restUrl);
        String authHeader =  String.format("%s %s",authenticationType.getCode() ,token);
        post.setHeader("AUTHORIZATION", authHeader);
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        post.setHeader("Access-Control-Allow-Origin" ,"*");
        post.setHeader("Access-Control-Allow-Headers" ," Origin, Content-Type, Accept");
        // post.setHeader("X-Stream" , "true");
        return post;
    }


    HttpPost createConnectivity(String restUrl){


        HttpPost post = new HttpPost(restUrl);
        post.setHeader("Content-Type", "application/json");
//        post.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//        post.setHeader("Access-Control-Allow-Origin" ,"*");
//        post.setHeader("Access-Control-Allow-Headers" ," Origin, Content-Type, Accept");
//        // post.setHeader("X-Stream" , "true");
        return post;
    }
    
    String executeHttpRequest(JSONObject jsonData,  HttpPost httpPost)  throws UnsupportedEncodingException, IOException
    {


        //mySslContext = new MySSLContext();
        SSLContext sslContext = SSLContextTest.getSllContext() ;

       // @SuppressWarnings("deprecation")
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext ,
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



        if(jsonData !=null){

            httpPost.setEntity(new StringEntity(jsonData.toString()));

        }

        HttpResponse httpResponse = httpclient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity() ;
        String responseString = EntityUtils.toString(httpEntity);

        return responseString ;
        
        
       
    }
    
    String executeReq(JSONObject jsonData, HttpPost httpPost) {

        Exception exception =null;

        try{
            return executeHttpRequest(jsonData, httpPost);
        }
        catch (UnsupportedEncodingException e){
            System.out.println("error while encoding api url : "+e);
            exception = e ;
        
        }
        catch (IOException e){
            System.out.println("ioException occured while sending http request : "+e);
            throw new NetworkConnectionException();
        }
        catch(Exception e){
            System.out.println("exception occured while sending http request : "+e);
            exception = e ;
        }
        finally{
            httpPost.releaseConnection();
        }
        
        return exception.getMessage() ;
    }
}
