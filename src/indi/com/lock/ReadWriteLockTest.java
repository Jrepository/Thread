package indi.com.lock;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 02_
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        final  Queue3 q3= new Queue3();
        for (int i = 0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        q3.get();
                    }
                }
            }).start();
        }
        for (int i = 0;i<3;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        q3.put(new Random().nextInt(10000));
                    }
                }
            }).start();
        }
    }

}
class Queue3{
    private Object data = null;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public void get(){
        lock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"我准备读了");
            Thread.sleep((long)(Math.random()*1000));
            System.out.println(Thread.currentThread().getName()+":"+data);
            System.out.println(Thread.currentThread().getName()+"我读完了");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
    }
    public void put(Object data){
        lock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"我准备写了");
            Thread.sleep((long)(Math.random()*1000));
            this.data=data;
            System.out.println(Thread.currentThread().getName()+"："+data);
            System.out.println(Thread.currentThread().getName()+"我写完了");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }
}