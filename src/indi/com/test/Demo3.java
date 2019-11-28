package indi.com.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 *线程打印数据，key相同时打印时间不同，key不相同时打印时间相同
 *
 *
 */
//该类不能动
public class Demo3 extends Thread {

    private TestDo testDo;

    private String key;

    private String value;

    public Demo3(String key,String key2,String value){
        this.testDo = TestDo.getInstance();
        this.key = key+key2;
        this.value = value;
    }

    public static void main(String[] args) {
        Demo3 a = new Demo3("1", "", "1");
        Demo3 b = new Demo3("1", "", "2");
        Demo3 c = new Demo3("3", "", "3");
        Demo3 d = new Demo3("4", "", "4");
        a.start();
        b.start();
        c.start();
        d.start();
    }

    @Override
    public void run() {
        testDo.doSome(key,value);
    }
}

class TestDo{
    private TestDo(){};
    private static TestDo instance = new TestDo();

    public static TestDo getInstance(){
        return instance;
    }

//    private ArrayList keys = new ArrayList();
    private CopyOnWriteArrayList keys = new CopyOnWriteArrayList();
    public void doSome(Object key,String value){
        Object obj = key;
        if(!keys.contains(obj)){
            keys.add(obj);
        }else{
            for (Iterator iterator=keys.iterator();iterator.hasNext();){
                Object next = iterator.next();
                if(next.equals(obj)){
                    obj = next;
                }
            }
        }

        synchronized (obj)
        //大括号内的不能动
        {
          try {
              Thread.sleep(1000);
              System.out.println(key+":"+value+":"+(System.currentTimeMillis()/1000));
          }catch (Exception e){}
        }
    }
}
