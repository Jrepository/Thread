package indi.com.other;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrerTest {


    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0;i<3;i++){
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    try {

                        Thread.sleep((long)(Math.random()*1000));
                        System.out.println(Thread.currentThread().getName()+":即将到达集合点1，已有："+cyclicBarrier.getNumberWaiting()+"到达");
                        cyclicBarrier.await();
                        Thread.sleep((long)(Math.random()*1000));
                        System.out.println(Thread.currentThread().getName()+":即将到达集合点2，已有："+cyclicBarrier.getNumberWaiting()+"到达");
                        cyclicBarrier.await();
                        Thread.sleep((long)(Math.random()*1000));
                        System.out.println(Thread.currentThread().getName()+":即将到达集合点3，已有："+cyclicBarrier.getNumberWaiting()+"到达");
                        cyclicBarrier.await();

                    }catch (Exception e){}
                }
            };
            service.execute(runnable);
        }
    }
}
