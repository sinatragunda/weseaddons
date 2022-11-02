/*Created by Sinatra Gunda
  At 03:47 on 2/18/2021 */

package com.wese.weseaddons.utility;

import com.wese.weseaddons.sqlquerries.enumerations.THROWED_ERROR;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class CompletableFutureEx {

    private static CompletableFutureEx completableFutureEx ;
    private Map<String ,Future> futureMap ;
    private Map<String ,THROWED_ERROR> throwedErrorsMap ;

    public CompletableFutureEx(){
        this.futureMap = new HashMap<>();
        this.throwedErrorsMap = new HashMap<>();
    }

    public static CompletableFutureEx getInstance() {

        if(completableFutureEx==null){
            completableFutureEx = new CompletableFutureEx();

        }

        return completableFutureEx;
    }

    public void add(String id ,Future future){
        futureMap.putIfAbsent(id ,future);
    }

    public Future get(String id){

        if(futureMap.containsKey(id)){
            return futureMap.get(id);
        }

        return null ;
    }

    public Boolean isDone(String id){

        if(futureMap.containsKey(id)){
            Future future = futureMap.get(id);
            return future.isDone();
        }
        return null ;
    }

    public void addError(String key ,THROWED_ERROR thrOwEDError){
        throwedErrorsMap.putIfAbsent(key ,thrOwEDError);
    }

    public THROWED_ERROR getThrowedError(String key){
        if(throwedErrorsMap.containsKey(key)){
            return throwedErrorsMap.get(key);
        }

        return null ;
     }
}
