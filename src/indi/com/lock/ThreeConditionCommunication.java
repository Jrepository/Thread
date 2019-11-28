package indi.com.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 04_ConditionCommunication
 *
 * 面试题：
 * 子线程循环10次，接着主线程循环100次，
 * 接着又回到子线程循环10次，接着再回到主线程循环10次，
 * 如此循环50次
 *
 * 思路：
 *     1、先实现互斥，即子线程与子线程互不干扰，加锁保护，索可以使用类的字节码（不建议），xxx.class
 *     2、通讯
 */
public class ThreeConditionCommunication {


    public static void main(String[] args) {
        Business business = new Business();
        //1、创建线程
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<50;i++){
                            business.sub(i);
                        }
                    }
                }
        ).start();
        //1、创建线程
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<50;i++){
                            business.sub2(i);
                        }
                    }
                }
        ).start();


        for (int i=0;i<50;i++){
            business.main(i);
        }

    }



    /**
     * 业务处理
     */
    static class Business{
        private Lock lock = new ReentrantLock();

        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();
        private int shouldSub=1;
        //主线程的业务
        public void main(int j){

            lock.lock();
            try{
                while(shouldSub != 1){//子线程方法正在被调用，主线程方法等待
                    try {
                        condition1.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                for (int i=0;i<100;i++) {
                    System.out.println("主线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
                }
                shouldSub = 2;
                condition2.signal();
            }finally {
                lock.unlock();
            }
        }
        //子线程的业务
        public void sub(int j){
            lock.lock();
            try {
                while(shouldSub !=2){//主线程方法正在被调用，子线程方法等待
                    try{
                        condition2.await();
                    }catch (Exception e){

                    }
                }
                for (int i=0;i<10;i++) {
                    System.out.println("子线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
                }
                shouldSub = 3;
                condition3.signal();
            }finally {
                lock.unlock();
            }
        }
        //子线程的业务
        public void sub2(int j){
            lock.lock();
            try {
                while(shouldSub != 3){//主线程方法正在被调用，子线程方法等待
                    try{
                        condition3.await();
                    }catch (Exception e){

                    }
                }
                for (int i=0;i<10;i++) {
                    System.out.println("子线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
                }
                shouldSub = 1;
                condition1.signal();
            }finally {
                lock.unlock();
            }
        }

    }

}


