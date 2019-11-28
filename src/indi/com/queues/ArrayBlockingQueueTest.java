package indi.com.queues;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 阻塞队列
 */
public class ArrayBlockingQueueTest {



    public static void main(String[] args) {

        final ArrayBlockingQueue queue = new ArrayBlockingQueue(3);
        //放数据
        for (int i = 0;i<2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep((long)Math.random()*1000);
                            System.out.println(Thread.currentThread().getName()+"准备放数据");
                            queue.put(1);
                            System.out.println(Thread.currentThread().getName()+"已放进一个数据，现有数据个数："+queue.size());
                        }catch (Exception e){}
                    }
                }
            }).start();
        }

        //放数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep((long)Math.random()*1000);
                        System.out.println(Thread.currentThread().getName()+"取数据：准备取数据");
//            queue.add(1);
                        queue.take();
                        System.out.println(Thread.currentThread().getName()+"取数据：已经取出一个数据，现有数据个数："+queue.size());
                    }catch (Exception e){}
                }
            }
        }).start();

    }
}
