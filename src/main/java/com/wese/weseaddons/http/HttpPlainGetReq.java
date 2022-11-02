/*

    Created by Sinatra Gunda
    At 10:07 AM on 9/14/2022

*/
package com.wese.weseaddons.http;

import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.utility.ThreadContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HttpPlainGetReq {

    private MySSLContext mySslContext ;

    HttpGet createConnectivity(String restUrl){

        HttpGet post = new HttpGet(restUrl);
        String tenantIdentifier = ThreadContext.getTenant();

        post.setHeader("Content-Type", "application/json");
        post.setHeader("Fineract-Platform-TenantId", tenantIdentifier);

        System.err.println(post);

        // post.setHeader("X-Stream" , "true");
        return post;
    }


    String executeHttpRequest(JSONObject jsonData, HttpGet httpGet)  throws UnsupportedEncodingException, IOException {
        HttpResponse response=null;
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

        HttpResponse httpResponse = httpclient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        String responseString = EntityUtils.toString(httpEntity);

        return responseString ;


    }

    String executeReq(JSONObject jsonData, HttpGet httpGet){

        try{
            return executeHttpRequest(jsonData, httpGet);
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
            httpGet.releaseConnection();
        }

        return null ;
    }
}

