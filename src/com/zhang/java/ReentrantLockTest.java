package com.zhang.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zsy
 * @Create 2020/3/17 10:09
 * @Description
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket).start();
        new Thread(ticket).start();
    }
}

class Ticket implements Runnable {
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        lock.lock();
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "dwd");
            fun();
        } finally {
            lock.unlock();
            lock.unlock();
        }
    }

    public void fun() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "fun...");
        } finally {
            lock.unlock();
        }
    }
}
