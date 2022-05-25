package com.zhang.java;

import java.util.concurrent.Semaphore;

/**
 * @Author zsy
 * @Create 2020/3/10 22:57
 * @Description
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "抢到");
//                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "走了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, "线程" + i).start();
        }
    }
}
