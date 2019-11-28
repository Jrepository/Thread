package indi.com.traditional;

import sun.security.jca.GetInstance;

import java.util.Random;

/**
 * 06_ThreadLocalTest
 *
 * 线程范围内的共享数据，线程内共享，线程外独立
 * 事务
 *
 * 如何将多个变量放到ThreadLocal中，定一个类
 */
public class ThreadLocalTest {

    private static ThreadLocal<Integer> tl = new ThreadLocal<Integer>();
    //方式1
    //private static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();
    public static void main(String[] args) {
        for (int i = 0;i<2;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName()+"   data:"+data);
                    tl.set(data);

                    //方式1
                    //MyThreadScopeData myData = new MyThreadScopeData();
                    //myData.setName("name"+data);
                    //myData.setAge(1);
                    //myThreadScopeData.set(myData);

                    MyThreadScopeData instance = MyThreadScopeData.getInstance();
                    instance.setName("test");
                    instance.setAge(1);
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
            System.out.println(Thread.currentThread().getName()+"   A:"+tl.get());
//            System.out.println("myThreadScopeData   "+Thread.currentThread().getName()+"   A:"+myThreadScopeData.get());
        }
    }
    /**
     * 模块B
     */
    static class B{
        public void get(){
            System.out.println(Thread.currentThread().getName()+"   B:"+tl.get());
//            System.out.println("myThreadScopeData   "+Thread.currentThread().getName()+"   B:"+myThreadScopeData.get().getName()+"/"+myThreadScopeData.get().getAge());
        }
    }
}


class MyThreadScopeData{
    private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
    private MyThreadScopeData(){}
    public static MyThreadScopeData getInstance(){
        MyThreadScopeData instance = map.get();
        if(null == instance){
            instance = new MyThreadScopeData();
            map.set(instance);
        }
        return instance;
    }

//        单例
//    private static MyThreadScopeData instance = null;
////    private static MyThreadScopeData instance = new MyThreadScopeData();
//
//    private MyThreadScopeData(){}
//
//    public static synchronized MyThreadScopeData getInstance(){
//        if(null == instance){
//            instance = new MyThreadScopeData();
//        }
//        return instance;
//    }
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
