package com.zhang.java;

import java.util.concurrent.TimeUnit;

/**
 * @Author zsy
 * @Create 2020/3/18 14:02
 * @Description
 */
public class DeadLockTest {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        new Thread(() -> {
            synchronized (o1) {
                System.out.println(Thread.currentThread().getName() + "锁住o1，想获取o2");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.println(Thread.currentThread().getName() + "锁住o2，想获取o1");
                }
            }
        }, "线程1").start();

        new Thread(() -> {
            synchronized (o2) {
                System.out.println(Thread.currentThread().getName() + "锁住o2，想获取o1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println(Thread.currentThread().getName() + "锁住o1，想获取o2");
                }
            }
        }, "线程2").start();
    }
}

