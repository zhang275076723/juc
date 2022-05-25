package com.zhang.java;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author zsy
 * @Create 2020/3/10 19:18
 * @Description
 */
public class ProducerAndConsumerTest {
    public static void main(String[] args) throws InterruptedException {
//        Clerk clerk = new Clerk();
//        new Thread(() -> {
//            for (int i = 1; i <= 10; i++) {
//                try {
//                    clerk.increase();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "producer").start();
//        new Thread(() -> {
//            for (int i = 1; i <= 10; i++) {
//                try {
//                    clerk.decrease();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "consumer").start();

//        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(3);
//        new Thread(() -> {
//            for (int i = 1; i <= 10; i++) {
//                try {
//                    int num = (int) (Math.random() * 10);
//                    System.out.println(Thread.currentThread().getName() + "生产:" + num);
//                    blockingQueue.put(num);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "producer").start();
//        new Thread(() -> {
//            for (int i = 1; i <= 10; i++) {
//                try {
//                    System.out.println(Thread.currentThread().getName() + "消费:" + blockingQueue.take());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "consumer").start();
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(3));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            try {
                myResource.produce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "生产者").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            try {
                myResource.consume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "消费者").start();

        TimeUnit.SECONDS.sleep(3);
        System.out.println("时间到，停止活动");
        myResource.stop();
    }
}

class Clerk {
    private int product = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

//    public synchronized void increase() throws InterruptedException {
//        while (product != 0) {
//            wait();
//        }
//        System.out.println(Thread.currentThread().getName() + "生产:" + ++product);
//        notifyAll();
//    }
//
//    public synchronized void decrease() throws InterruptedException {
//        while (product == 0) {
//            wait();
//        }
//        System.out.println(Thread.currentThread().getName() + "消费:" + --product);
//        notifyAll();
//    }

    public void increase() throws InterruptedException {
        lock.lock();
        try {
            while (product != 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "生产:" + ++product);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrease() throws InterruptedException {
        lock.lock();
        try {
            while (product == 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "生产:" + --product);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class MyResource {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue;

    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void produce() throws InterruptedException {
        String data;
        boolean retValue;
        while (flag) {
            data = String.valueOf(atomicInteger.getAndIncrement());
            retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "生产" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "生产" + data + "失败");
            }
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    public void consume() throws InterruptedException {
        String result;
        while (flag) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (result == null) {
                System.out.println(Thread.currentThread().getName() + "消费失败");
            } else {
                System.out.println(Thread.currentThread().getName() + "消费" + result + "成功");
            }
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }

    public void stop() {
        flag = false;
    }
}
