package indi.com.other;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 两者都到达后，进行交易，交易后，各自执行自己的操作
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Exchanger exchanger = new Exchanger();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String data1 = "zxx";
                    System.out.println(Thread.currentThread().getName()+"正在将数据"+data1+"换出去");
                    Thread.sleep((long)(Math.random()*1000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println(Thread.currentThread().getName()+"将数据:"+data1+"换为："+data2);
                }catch (Exception e){}
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String data2 = "yxx";
                    System.out.println(Thread.currentThread().getName()+"正在将数据"+data2+"换出去");
                    Thread.sleep((long)(Math.random()*1000));
                    String data1 = (String) exchanger.exchange(data2);
                    System.out.println(Thread.currentThread().getName()+"将数据:"+data2+"换为："+data1);
                }catch (Exception e){}
            }
        });
    }
}
