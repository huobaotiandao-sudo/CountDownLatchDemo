package com.snow.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by snow_fgy on 2020/5/28.
 */
public class CountDownlatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t " + temp);
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        System.out.println("all");





    }
}
