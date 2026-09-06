package com.geekq.miasha.utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnowflakeIdWorkerTest {

    @Test
    public void getOrderIdIsUniqueWhenCalledConcurrently() throws Exception {
        int taskCount = 1_000;
        ExecutorService executor = Executors.newFixedThreadPool(8);
        try {
            Set<Future<Long>> futures = new HashSet<Future<Long>>();
            for (int i = 0; i < taskCount; i++) {
                futures.add(executor.submit(new Callable<Long>() {
                    @Override
                    public Long call() {
                        return SnowflakeIdWorker.getOrderId(0, 0);
                    }
                }));
            }

            Set<Long> ids = new HashSet<Long>();
            for (Future<Long> future : futures) {
                ids.add(get(future));
            }
            assertEquals(taskCount, ids.size());
        } finally {
            executor.shutdownNow();
        }
    }

    private Long get(Future<Long> future) throws InterruptedException, ExecutionException {
        return future.get();
    }
}
