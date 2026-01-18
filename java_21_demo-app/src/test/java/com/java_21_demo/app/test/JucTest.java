package com.java_21_demo.app.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JucTest {
    @RepeatedTest(10)
    public void start() throws InterruptedException {
        Thread thread = new Thread(() -> {
            LockSupport.park();
            log.info("{}", "Hello from A");
        });
        thread.start();

        Thread thread2 = new Thread(() -> {
            LockSupport.park();
            log.info("{}", "Hello from B");
            LockSupport.unpark(thread);
        });
        thread2.start();

        log.info("{}\n\n", "Hello from Main");

        LockSupport.unpark(thread2);
        thread.join();
        thread2.join();
    }

    @Test
    public void loopPrint() throws InterruptedException {
        // 3个线程循环打印bcd 5次
        Object lock = new Object();
        char[] chars = { 'a', 'b', 'c' };
        int[] currIndex = { 0 };
        for (int i = 0; i < 3; i++) {
            final int index = i;
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    synchronized (lock) {
                        while (currIndex[0] != index) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        log.info("{} {}", chars[index], currIndex[0] == chars.length - 1 ? "\n" : "");
                        currIndex[0] = (currIndex[0] + 1) % 3;
                        lock.notifyAll();
                    }
                }
            }).start();
        }

        Thread.sleep(200);
    }

    @Test
    public void loopPrint2() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition[] conditions = { reentrantLock.newCondition(), reentrantLock.newCondition(),
                reentrantLock.newCondition() };
        char[] chars = { 'a', 'b', 'c' };
        int len = conditions.length;

        for (int i = 0; i < len; i++) {
            final int index = i;
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    reentrantLock.lock();
                    try {
                        conditions[index].await();
                        log.info("{} {}", chars[index], index == len - 1 ? "\n" : "");
                        conditions[(index + 1) % len].signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        reentrantLock.unlock();
                    }
                }
            }).start();
        }

        reentrantLock.lock();
        try {
            conditions[0].signal();
            Thread.sleep(200);
        } finally {
            reentrantLock.unlock();
        }
    }

    @Test
    public void execThread() throws InterruptedException, ExecutionException {
        // 错误做法：池化虚拟线程
        // 正确做法：按需创建
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            log.info("{}", "Start tasks");
            Object[][] tasks = { { 1000L, "Task completed" }, { 2000L, "Another task completed" } };
            List<Future<?>> result = new ArrayList<>();
            List.of(tasks).forEach(task -> {
                Future<?> f = executor.submit(() -> doSleep((long) task[0], (String) task[1]));
                result.add(f);
            });
            for (Future<?> f : result) {
                f.get();
            }
            log.info("{}", "Tasks end");
        } // 执行器关闭时会等待所有任务完成

    }

    public void doSleep(long millis, String message) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{}", message);
    }

    public void doSleep(long millis) {
        doSleep(millis, "");
    }

    @Test
    public void futureTest() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        CompletableFuture<String> r = CompletableFuture.supplyAsync(() -> {
            log.info("{}", "Start task");
            doSleep(1000);
            return "Task result";
        }, executor).thenCompose(result -> {
            return CompletableFuture.supplyAsync(() -> {
                log.info("{}", "Processing result: " + result);
                doSleep(1000);
                return "Processed " + result;
            }).completeOnTimeout("Timeout result", 5, TimeUnit.SECONDS);
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            log.info("{}", "Starting another task");
            doSleep(1800);
            return "Another task result";
        }), (result1, result2) -> {
            log.info("{}", "Combining results: " + result1 + " & " + result2);
            return result1 + " + " + result2;
        }).orTimeout(4, TimeUnit.SECONDS).exceptionally(ex -> {
            log.error("{}", "Error occurred: " + ex.getMessage());
            return null;
        }).thenApply(f -> f).whenComplete((res, ex) -> {
            log.info("{}", "All tasks completed");
            executor.shutdown();
        });

        r.get();
    }
}
