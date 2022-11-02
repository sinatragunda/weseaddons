package com.wese.weseaddons.concurrent;

import com.wese.weseaddons.bereaudechange.helper.OffshoreThread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ConsumerThread<T> {

    public static void run(Consumer consumer ,Object consumable){

        Runnable runnable = ()->{

            consumer.accept(consumable);

        };

        OffshoreThread.run(runnable);
    }
}
