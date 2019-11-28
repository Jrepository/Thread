package indi.com.other;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号灯
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(3);
        ExecutorService pools = Executors.newCachedThreadPool();
//        ExecutorService pools = Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            pools.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                    }catch (Exception e){}
                    System.out.println(Thread.currentThread().getName()+"：已进入，当前有："+(3-semaphore.availablePermits())+"个并发");

                    try {
                        Thread.sleep((long)(Math.random()*1000));
                    }catch (Exception e){}
                    System.out.println(Thread.currentThread().getName()+"：即将离开");
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName()+"：已离开，当前有："+(3-semaphore.availablePermits())+"个并发");
                }
            });
        }
    }
}
