package me.kodingking.bots.kodax.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreading {
    
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private static final Executor EXECUTOR = Executors.newCachedThreadPool(r -> new Thread(r, "Multithread-" + ATOMIC_INTEGER.incrementAndGet()));
    
    public static void runAsync(Runnable r) {
        EXECUTOR.execute(r);
    }
    
}
