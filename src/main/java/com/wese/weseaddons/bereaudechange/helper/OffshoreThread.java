package com.wese.weseaddons.bereaudechange.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OffshoreThread {

    public static void run(Runnable runnable){
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(runnable);

        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
        System.err.println("Thread has been executed now");
    }
}
