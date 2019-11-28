package indi.com.traditional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 05_ThreadScopeShareData
 *
 * 线程范围内的共享数据，线程内共享，线程外独立
 *
 * 事务
 *
 */
public class ThreadScopeShareData {
    private static Map<Thread, Integer> map = new HashMap<>();
    private static int data = 0;
    public static void main(String[] args) {
        for (int i = 0;i<2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data = new Random().nextInt();
                    System.out.println("data:"+Thread.currentThread().getName()+"   data:"+data);

                    map.put(Thread.currentThread(),data);
                    System.out.println("map:"+Thread.currentThread().getName()+"   data:"+map.get(Thread.currentThread()));


                    new A().get();
                    new B().get();

                }
            }).start();
        }
    }


    /**
     * 模块A
     */
    static class A{
        public void get(){
            System.out.println("data:"+Thread.currentThread().getName()+"   A:"+data);
            System.out.println("map:"+Thread.currentThread().getName()+"   A:"+map.get(Thread.currentThread()));
        }
    }
    /**
     * 模块B
     */
    static class B{
        public void get(){
            System.out.println("data:"+Thread.currentThread().getName()+"   B:"+data);
            System.out.println("map:"+Thread.currentThread().getName()+"   B:"+map.get(Thread.currentThread()));
        }
    }
}
