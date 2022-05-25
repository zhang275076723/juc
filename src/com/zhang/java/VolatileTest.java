package com.zhang.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author zsy
 * @Create 2020/3/15 15:42
 * @Description
 */
public class VolatileTest {
    public static void main(String[] args) {
        MyData myData = new MyData();
//        new Thread(() -> {
//            while (myData.num == 0) {
//            }
//            System.out.println(Thread.currentThread().getName() + ":" + myData.num);
//        }, "线程1").start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        new Thread(() -> {
//            System.out.println(Thread.currentThread().getName() + "执行前:" + myData.num);
//            myData.fun();
//            System.out.println(Thread.currentThread().getName() + "执行后:" + myData.num);
//        }, "线程2").start();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.add();
                    myData.addAtomic();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(myData.num);
        System.out.println(myData.atomicInteger);
    }
}

class MyData {
    volatile int num;
    AtomicInteger atomicInteger = new AtomicInteger();

    public void fun() {
        num = 1;
    }

    public void add() {
        num++;
    }

    public void addAtomic() {
        atomicInteger.getAndIncrement();
    }
}
