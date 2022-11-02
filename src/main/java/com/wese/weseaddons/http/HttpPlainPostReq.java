/*

    Created by Sinatra Gunda
    At 11:59 AM on 9/14/2022

*/
package com.wese.weseaddons.http;

import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.utility.ThreadContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;

public class HttpPlainPostReq {

    private MySSLContext mySslContext ;

    HttpPost createConnectivity(String restUrl) throws IllegalArgumentException{

        HttpPost post = new HttpPost(restUrl);
        String tenantIdentifier = ThreadContext.getTenant();
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Fineract-Platform-TenantId", tenantIdentifier);
        post.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        post.setHeader("Access-Control-Allow-Origin" ,"*");
        post.setHeader("Access-Control-Allow-Headers" ," Origin, Content-Type, Accept");
        // post.setHeader("X-Stream" , "true");
        return post;
    }


    String executeHttpRequest(JSONObject jsonData, HttpPost httpPost)  throws UnsupportedEncodingException, IOException
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


        Optional.ofNullable(jsonData).ifPresent(e->{
            try {
                httpPost.setEntity(new StringEntity(jsonData.toString()));
            } catch (UnsupportedEncodingException e1) {
                System.err.println("--------------unsupported encoding ");
                e1.printStackTrace();
            }
        });

        HttpResponse httpResponse = httpclient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity() ;
        String responseString = EntityUtils.toString(httpEntity);

        return responseString ;


    }

    String executeReq(JSONObject jsonData, HttpPost httpPost) {

        System.err.println("-------------------------exception caught --------------");

        try{
            return executeHttpRequest(jsonData, httpPost);
        }
        catch (UnsupportedEncodingException e){
            System.out.println("---------------------------error while encoding api url : "+e.getMessage());

        }
        catch (IOException e){
            System.out.println("-----------------------------ioException occured while sending http request : "+e.getMessage());
        }
        catch(Exception e){
            System.err.println("--------------print stack track --------------------");
            e.printStackTrace();
            System.out.println("------------------exception occured while sending http request : "+e.getMessage());
        }

        finally{
            httpPost.releaseConnection();
        }

        System.err.println("----------------------return null --------------------from http runming");
        return null ;
    }
}

