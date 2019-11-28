package indi.com.traditional;

/**
 * 01_TraditionalThread
 * 线程
 */
public class TraditionalThread {


    public static void main(String[] args) {
//        Thread thread = new Thread();
//        thread.start();

        //方式1:
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println("方式1："+Thread.currentThread().getName());
                }
            }
        };

       // thread.start();

        //方式2：
        /**
         * 与方式1相比，更加能体现面向对象的思想
         */
       Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("方式2："+Thread.currentThread().getName());
                }
            }
        });
      //thread2.start();


       //扩展
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("父类...");
                }
            }
        }){
            @Override
            public void run() {
                while (true){
                    System.out.println("子类...");
                }
            }
        }.start();
    }
}
