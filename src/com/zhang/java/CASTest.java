package com.zhang.java;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author zsy
 * @Create 2020/3/15 21:12
 * @Description
 */
public class CASTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019) + ":" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2014) + ":" + atomicInteger.get());
    }
}
