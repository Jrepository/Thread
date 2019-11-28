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
public class ConditionCommunication {


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


        for (int i=0;i<50;i++){
            business.main(i);
        }

    }



    /**
     * 业务处理
     */
    static class Business{
        private Lock lock = new ReentrantLock();

        Condition condition = lock.newCondition();
        private boolean flag = true;//标志子线程方法是否被调用
        //主线程的业务
        public void main(int j){

            lock.lock();
            try{
                while(flag){//子线程方法正在被调用，主线程方法等待
                    try {
                        condition.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                for (int i=0;i<100;i++) {
                    System.out.println("主线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
                }
                flag = true;
                condition.signal();
            }finally {
                lock.unlock();
            }
        }
        //子线程的业务
        public void sub(int j){
            lock.lock();
            try {
                while(!flag){//主线程方法正在被调用，子线程方法等待
                    try{
                        condition.await();
                    }catch (Exception e){

                    }
                }
                for (int i=0;i<10;i++) {
                    System.out.println("子线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
                }
                flag = false;
                condition.signal();
            }finally {
                lock.unlock();
            }
        }

    }

}


