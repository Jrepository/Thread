package indi.com.other;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 跑步比赛
 *
 * 一个人通知多个人，多个人通知一个人
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        CountDownLatch cdlOrder = new CountDownLatch(1);
        CountDownLatch cdlAnswer = new CountDownLatch(3);
        for (int i=0;i<3;i++){
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    try {

                        System.out.println(Thread.currentThread().getName()+":正准备接受命令");
                        cdlOrder.await();
                        System.out.println(Thread.currentThread().getName()+":已经准备接受命令");
                        Thread.sleep((long)(Math.random()*1000));
                        System.out.println(Thread.currentThread().getName()+"：响应接受的命令");
                        cdlAnswer.countDown();
                    }catch (Exception e){}
                }
            };
            service.execute(runnable);
        }

        try {
            Thread.sleep((long)(Math.random()*1000));
            System.out.println(Thread.currentThread().getName()+"：即将发布命令");
            cdlOrder.countDown();
            System.out.println(Thread.currentThread().getName()+"：已发布命令，正在等待结果");
            cdlAnswer.await();
            System.out.println(Thread.currentThread().getName()+"：已收到响应结果");
        }catch (Exception e){}

    }
}
