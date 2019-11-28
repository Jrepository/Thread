package indi.com.test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 * 原程序：现成程序中的Test类中的代码在不断的产生数据
 *
 * 要求：改成10个线程消费产生的数据，一个消费者消费完之后，下一个消费者才能消费数据，
 *
 *  下一个消费者是谁都可以，但是消费者拿到的数据是有序的
 */
public class Demo2 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1);
        SynchronousQueue<String> queue = new SynchronousQueue<String>();
        for (int i = 0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        String input = queue.take();
                        String output = DemoDo.doSome(input);
                        System.out.println(Thread.currentThread().getName()+":"+output);
                        semaphore.release();
                    }catch (Exception e){}
                }
            }).start();
        }


        System.out.println("begin："+(System.currentTimeMillis()/1000));

        for (int i = 0;i<10;i++){//不能动
            String input = ""+i;//不能动
            try {
                queue.put(input);
            }catch (Exception e){}
//            String output = DemoDo.doSome(input);
//            System.out.println(Thread.currentThread().getName()+":"+output);

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        semaphore.acquire();
//                        String output = DemoDo.doSome(input);
//                        System.out.println(Thread.currentThread().getName()+":"+output);
//                        semaphore.release();
//                    }catch (Exception e){}
//                }
//            }).start();
        }
    }
}

//不能动
class DemoDo{
    public static String doSome(String input){
        try {
            Thread.sleep(1000);
        }catch (Exception e){}
        String outout = input+"："+(System.currentTimeMillis()/1000);
        return outout;
    }
}
