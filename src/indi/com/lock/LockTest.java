package indi.com.lock;

import indi.com.traditional.TraditionalThreadSynchronized;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 01_LockTest
 *
 * 多线程下安全问题
 */
public class LockTest {

    public static void main(String[] args) {
        new LockTest().init();
    }


    private  void init(){
        /**
         * 1、内部类不能访问局部变量，需要给局部变量加上final
         * 在静态方法中不能new内部类的实例对象，内部类可以访问外部类的成员变量，
         * 要想创建内部类的实例对象，必须要有内部类的实例对象
         */
        OutPuter outPuter = new OutPuter();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    outPuter.outPut("javatest");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    outPuter.outPut("python");
                }
            }
        }).start();
    }
    class OutPuter{
        Lock lock = new ReentrantLock();

        public void outPut(String name){
            int len = name.length();
            lock.lock();
            try {
                for (int i = 0;i<len;i++){
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }finally {
                lock.unlock();
            }
        }

        /**
         * 方式2，锁住方法
         *
         * 方法中的synchronized使用的是this对象，
         * 如果上面的outPut方法中使用synchronized (this)，则outPut2与outPut互斥
         * 如果上面的outOut方法中使用synchronized (xxx)，则outPut2与outPut不互斥
         * @param name
         */
        public synchronized void outPut2(String name){
            int len = name.length();
            for (int i = 0;i<len;i++){
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }

    }


    /**
     * 内部类上加static,就相当于外部类
     *
     *  方式1，方式2同步
     *  方式1、方式2与方式3不同步
     *  方式3、方式4同步
     *
     */
    static class OutPuter2{

        /**
         *  方式1：锁住部分代码
         * @param name
         */
//        String xxx = "";
        public void outPut(String name){
            int len = name.length();
//            synchronized (xxx){   //这样也可以，但是没必要，直接用this
            synchronized (this){
                for (int i = 0;i<len;i++){
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

        /**
         * 方式2，锁住方法
         *
         * 方法中的synchronized使用的是this对象，
         * 如果上面的outPut方法中使用synchronized (this)，则outPut2与outPut互斥
         * 如果上面的outOut方法中使用synchronized (xxx)，则outPut2与outPut不互斥
         * @param name
         */
        public synchronized void outPut2(String name){
            int len = name.length();
            for (int i = 0;i<len;i++){
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }

        /**
         * 方式3：
         *  静态的方法不能写在内部类中，内部类上加static，就不是内部类了，就相当于外部类
         *  使用的类的字节码对象
         * @param name
         */
        public static synchronized void outPut3(String name){
            int len = name.length();
            for (int i = 0;i<len;i++){
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }


        /**
         * 方式4:
         * @param name
         */
        public void outPut4(String name){
            int len = name.length();
            synchronized (OutPuter2.class){
                for (int i = 0;i<len;i++){
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

    }
}
