package indi.com.traditional;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 08_ThreadPoolTest
 * 线程池
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
        //线程池中有三个线程，每次都只有3个任务被同时处理，等其中一个任务执行完之后，其它未被执行的任务将被执行
        //固定的线程池
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //缓存线程池
//        ExecutorService threadPool = Executors.newCachedThreadPool();
        //单一线程池，如果线程池中的线程死了，会再找一个线程（找一个替补），从而保证线程中一直有一个线程
        //(如何实现线程死了然后重新启动？单一线程池)
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        for (int i = 0;i<10;i++){//循环10遍，就是像线程池中放10个任务
            final int task = i;//第几个任务
            //线程池
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //每个任务循环10次
                    for(int i = 0;i<10;i++){
                        try {
                            Thread.sleep(1000);
                        }catch (Exception e){

                        }

                        System.out.println(Thread.currentThread().getName()+"----"+i+"----task:"+task);
                    }
                }
            });
        }
        //所有任务都被执行完后，关闭线程
//        threadPool.shutdownNow();

        //定时线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("炸弹...");
            }
        }, 10, TimeUnit.SECONDS);


        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                System.out.println("bombing!");
            }
        }, 6, 2, TimeUnit.SECONDS);


        //指定时间执行的话，用指定的时间-当前时间（data.getTime()-System.currentTimeMillis()）
    }

}
