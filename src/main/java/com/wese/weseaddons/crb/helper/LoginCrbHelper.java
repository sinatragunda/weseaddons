package com.wese.weseaddons.crb.helper;

import java.util.* ;

import com.wese.weseaddons.crb.dao.TokenDAO;
import com.wese.weseaddons.crb.httpex.HttpClientBridgeEx;
import com.wese.weseaddons.crb.pojo.Token;
import com.wese.weseaddons.crb.pojo.TokenRequest;
import com.wese.weseaddons.enumerations.REQUEST_METHOD;
import com.wese.weseaddons.helper.Constants;
import com.wese.weseaddons.helper.JsonHelper;
import com.wese.weseaddons.helper.TimeHelper;
import com.wese.weseaddons.weselicense.helper.DateDifferenceComparator;
import org.json.JSONObject;

public class LoginCrbHelper {

    public static Token login(String tenantIdentifier){

        TokenDAO tokenDAO = new TokenDAO(tenantIdentifier);

        Token token = tokenDAO.find();


        if(token==null){
            /// make new request of data
            return createOrUpdateToken(tokenDAO ,true);
        }

        Long expiresAfter = Long.valueOf(token.getExpiresAfter());

        Date expiresAfterDate = TimeHelper.timestampToDateSecond(expiresAfter);
        Date dateNow  = TimeHelper.dateNow("Africa/Harare");

        DateDifferenceComparator dateDifferenceComparator = new DateDifferenceComparator();
        long diffDays = dateDifferenceComparator.differenceInDays(expiresAfterDate ,dateNow);

        if(diffDays < 0){
            ///expired if date difference is less than 0
            return createOrUpdateToken(tokenDAO ,false);
        }

        return token;

    }

    public static String getTokenData(String tenantIdentifier){
        Token token = login(tenantIdentifier);
        return token.getToken() ;
    }


    public static Token createOrUpdateToken(TokenDAO tokenDAO,boolean create){

        TokenRequest tokenRequest = new TokenRequest(Constants.crbInfinityCode ,Constants.crbPassword ,Constants.crbUsername);

        String tokenRequestJson = JsonHelper.objectToJson(tokenRequest);

        if(tokenRequestJson==null){
            return null;
        }

        String url = "https://secure3.crbafrica.com/duv2/login";

        HttpClientBridgeEx httpClientBridgeEx = new HttpClientBridgeEx(REQUEST_METHOD.PLAIN);

        System.err.println(tokenRequestJson);

        httpClientBridgeEx.setPostObject(new JSONObject(tokenRequestJson));

        String response = httpClientBridgeEx.makeRequest(url);

        System.err.println("------------------------token created is ----------------"+response);

        Token token = JsonHelper.stringToObject(response ,new Token());

        if(create){
            tokenDAO.create(token);
            return token ;
        }
        tokenDAO.update(token);
        return token;

    }
}
