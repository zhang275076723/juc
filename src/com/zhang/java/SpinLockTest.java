package com.zhang.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author zsy
 * @Create 2020/3/17 10:51
 * @Description
 */
public class SpinLockTest {
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() {
        System.out.println(Thread.currentThread().getName() + "myLock...");
        while (!atomicReference.compareAndSet(null, Thread.currentThread())) {
        }
    }

    public void myUnlock() {
        System.out.println(Thread.currentThread().getName() + "myUnlock...");
        atomicReference.compareAndSet(Thread.currentThread(), null);
    }

    public static void main(String[] args) {
        SpinLockTest spinLockTest = new SpinLockTest();

        new Thread(() -> {
            spinLockTest.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockTest.myUnlock();
        }, "线程1").start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockTest.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockTest.myUnlock();
        }, "线程2").start();
    }
}
