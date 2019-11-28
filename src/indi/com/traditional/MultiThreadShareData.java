package indi.com.traditional;

/**
 * 07_MultiThreadShareData
 *
 * 多线程共享数据
 *
 * 面试题：
 *      设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1
 *
 *
 *
 *
 * 如果每个线程执行代码相同，可以使用同一个Runnable对象，这个Runnable对象中有共享数据，eg:买票系统；
 * 如果每个线程执行代码不同，这个时候需要使用不同的Runnable，有一下两种方式实现Runnable对象之间的数据共享：
 *
 *      1、将共享对象封装到另一个对象中，并将对象传给Runnable对象
 *      2、将Runnable对象作为某个对象的内部类，共享数据作为该外部类的成员变量
 *      这两种方式的组合：将共享数据封装到另外一个对象中，对象作为外部类外部类的成员变量或者方法中的局部变量，
 *      每个线程的Runnable对象作为外部类的成员内部类或者局部内部类。
 *
 *
 *      两个内部类共享数据：两个内部类，共享数据做外部类的成员变量
 */
public class MultiThreadShareData {


    public static void main(String[] args) {
        Business business = new Business();
        ShareData data = new ShareData();


        new Thread(new MyRunnable1(data)).start();
        new Thread(new MyRunnable2(data)).start();
        for(int i = 0;i<2;i++){
            //加1
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    business.add();
                    data.increment();
                }
            }).start();
            //减1
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    business.incy();
                    data.decrement();
                }
            }).start();
        }
//        for(int i = 0;i<2;i++){
//
//        }

    }

    static class Business{
        int j = 0;
        public synchronized void add(){
            j++;
            System.out.println("add     "+j);
        }
        public synchronized void incy(){
            j--;
            System.out.println("incy    "+j);
        }
    }
}


//被多个线程使用，实现Runnable,多线程之间的代码相同，不适用于这个问题
class ShareData1 implements Runnable{
    private int count  = 100;

    @Override
    public void run() {
        count--;
    }
}
//被多个线程使用，实现Runnable,多线程之间的代码相同，不适用于这个问题
class ShareData {
    private int j = 0;
    //+
    public synchronized void increment(){
        j++;
    }
    //-
    public synchronized void decrement(){
        j--;
    }

}


//+
class MyRunnable1 implements Runnable{
    private ShareData shareData;
    public MyRunnable1(ShareData shareData){
        this.shareData = shareData;
    }
    @Override
    public void run() {
        while (true){
            shareData.increment();
        }
    }
}
//-
class MyRunnable2 implements Runnable{
    private ShareData shareData;
    public MyRunnable2(ShareData shareData){
        this.shareData = shareData;
    }
    @Override
    public void run() {
        while (true){
            shareData.decrement();
        }
    }
}