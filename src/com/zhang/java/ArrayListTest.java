package com.zhang.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author zsy
 * @Create 2020/3/11 9:51
 * @Description
 */
public class ArrayListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
//        List<String> list = new CopyOnWriteArrayList<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        //ConcurrentModificationException
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + ":" + list);
            }, String.valueOf(i)).start();
        }

    }
}
