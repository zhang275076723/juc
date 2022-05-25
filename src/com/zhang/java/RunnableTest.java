package com.zhang.java;

import sun.security.krb5.internal.Ticket;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zsy
 * @Create 2020/3/11 9:05
 * @Description
 */
public class RunnableTest {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }, "Runnable线程").start();
    }
}
