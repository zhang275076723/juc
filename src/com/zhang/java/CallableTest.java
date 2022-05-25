package com.zhang.java;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @Author zsy
 * @Create 2020/3/8 20:58
 * @Description
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask, "线程1").start();
        new Thread(futureTask, "线程2").start();
        System.out.println(Thread.currentThread().getName());
        System.out.println(futureTask.get());
    }
}

class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("Callable...");
        TimeUnit.SECONDS.sleep(2);
        return "success"+ Math.random();
    }
}