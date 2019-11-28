package indi.com.test;

import java.sql.SQLOutput;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个模拟代码产生16个日志对象，需要16秒才能打印完
 *
 *要求：增加四个线程调用parselog方法分头打印这16个日志对象，程序只需要运行4s
 * 即可打印完这些日志对象
 */
public class Demo {

    public static void main(String[] args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(16);
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int i = 0;i<4;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            String log = queue.take();
                            Demo.parseLog(log);
                        }catch (Exception e){}
                    }


                }
            });
        }
//        for (int i = 0;i<4;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while(true){
//                        try {
//                            String log = queue.take();
//                            Demo.parseLog(log);
//                        }catch (Exception e){}
//                    }
//
//
//                }
//            }).start();
//        }

        System.out.println("begin:"+(System.currentTimeMillis()/1000));
        for(int i = 0;i<16;i++){
            final String log = ""+(i+1);//不能动
            {
                try{
                    queue.put(log);
                }catch (Exception e){}
                //Demo.parseLog(log);

            }
        }
    }

    //不能动
    public static void parseLog(String log){
        System.out.println(log+"："+(System.currentTimeMillis()/1000));
        try {
            Thread.sleep(1000);
        }catch (Exception e){}
    }

}
