package com.zhang.java;

import java.util.concurrent.CountDownLatch;

/**
 * @Author zsy
 * @Create 2020/3/10 22:14
 * @Description
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "离开");
                countDownLatch.countDown();
            }, "线程" + i).start();
        }
        countDownLatch.await();
        System.out.println("人走完才关门");
    }
}
