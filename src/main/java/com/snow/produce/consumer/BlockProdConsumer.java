package com.snow.produce.consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by snow_fgy on 2020/5/31.
 */
class ShareData {
    private BlockingQueue<String> blockingQueue = null;
    private volatile boolean flag = true;
    private AtomicInteger number = new AtomicInteger();

    public ShareData(BlockingQueue<String> queue) {
        this.blockingQueue = queue;
    }

    public void producer() {
        String data;
        boolean result;
        try {

            while(flag) {
                data = number.getAndIncrement() + "";
                result = blockingQueue.offer(data, 1, TimeUnit.SECONDS);
                if (result) {
                    System.out.println(Thread.currentThread().getName() + "\t 生产完成,生产：" + data);
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t 生产失败");
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consumer() {

        try {
            String result;
            while (flag) {
                result = blockingQueue.poll(2, TimeUnit.SECONDS);
                if (result == null || result.equalsIgnoreCase("")) {
                    this.flag = false;
                    System.out.println(Thread.currentThread().getName() + "\t 队列没有数据，消费失败,生产消费结束");
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t 消费成功,消费：" + result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        flag = false;
        System.out.println("老板叫停");
    }




}




public class BlockProdConsumer {

    public static void main(String[] args) {
        ShareData shareData = new ShareData(new ArrayBlockingQueue(5));

        new Thread(() -> shareData.producer(),"A").start();

        new Thread(() -> shareData.consumer(),"B").start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        shareData.stop();

    }



}
