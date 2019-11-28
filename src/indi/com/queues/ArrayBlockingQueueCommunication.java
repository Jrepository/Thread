package indi.com.queues;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * 阻塞队列，主线程与子线程
 */
public class ArrayBlockingQueueCommunication {
        public static void main(String[] args) {

            Business business =new Business();
            //1、创建线程
            new Thread(new Runnable() {
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
        //成员变量，对象创建后分配空间
        private ArrayBlockingQueue queue1 = new ArrayBlockingQueue(1);//空，sub
        private ArrayBlockingQueue queue2 = new ArrayBlockingQueue(1);//非空

        //静态代码块在类的加载执行一次
        //匿名构造方法，在构造方法之前，创建几个对象，调用几次
        {
            try {
                queue2.put(1);
            }catch (Exception e){};
        }

        //主线程的业务
        public void main(int j){
//        if(flag){
                try {
                    queue2.put(1);
                    for (int i=0;i<100;i++) {
                        System.out.println("主线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
                    }
                    queue1.take();
                }catch (Exception e){
                    e.printStackTrace();
                }

        }
        //子线程的业务
        public void sub(int j){
                try {
                    queue1.put(1);
                    for (int i=0;i<10;i++) {
                        System.out.println("子线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
                    }
                    queue2.take();
                }catch (Exception e){}

        }

    }
}

