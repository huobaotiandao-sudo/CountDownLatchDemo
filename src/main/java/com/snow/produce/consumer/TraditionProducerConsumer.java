package com.snow.produce.consumer;

/**
 * Created by snow_fgy on 2020/5/30.
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类
 */
class DataShare {

    private int numerber = 0;
    private Lock lock = new ReentrantLock();
    private Condition lockCondition = lock.newCondition();

    public void producer() {
        lock.lock();
        try {
            while (numerber != 0) {
                System.out.println(Thread.currentThread().getName() + "\t 等待生产。。。");
                lockCondition.await();
            }
            numerber++;
            lockCondition.signal();
            System.out.println(Thread.currentThread().getName() + "\t 生产完毕，number：" + numerber);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void consumer() {
        lock.lock();
        try {
            while (numerber == 0) {
                System.out.println(Thread.currentThread().getName() + "\t 等待消费。。。");
                lockCondition.await();
            }
            numerber--;
            lockCondition.signal();
            System.out.println(Thread.currentThread().getName() + "\t 消费完毕，number：" + numerber);
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}


public class TraditionProducerConsumer {

    public static void main(String[] args) {
        DataShare dataShare = new DataShare();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                dataShare.producer();
            }, "AAAA").start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                dataShare.consumer();
            }, "BBBB").start();
        }


    }
}
