package indi.com.traditional;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 02_TraditionalTimer
 */
public class TraditionalTimer {

    public static void main(String[] args) {

        /**
         * 10s以后开始
         */
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                System.out.println("炸弹...");

            }
        },10000);*/
//        while (true){
//            System.out.println(new Date().getSeconds());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


        /**
         * 10s以后开始，之后每隔3秒执行
         */
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                System.out.println("炸弹...");

            }
        },10000,3000);*/


        /**
         * 子母弹，这种匿名内部类（匿名内部类一次性的）的实现方式，程序不会停止，但是子母弹只炸一次，不能实现不停地炸，
         * 实现炸弹中油炸的，不停的炸，需要写一个类
         */
        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("母炸弹...");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("子炸弹...");
                    }
                },2000);
            }
        },10000);
*/



//        new Timer().schedule(new MyTimerTask(), 10000);
        /**
         * 优化
         */
        new Timer().schedule(new MyTimerTask2(), 10000);
    }

    static class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            System.out.println("炸弹..."+new Date().getSeconds());
            new Timer().schedule(new MyTimerTask(),10000);
        }
    }

    /**
     * 优化
     */
    static class MyTimerTask2 extends TimerTask{
        static int count = 0;
        @Override
        public void run() {
            int i =  ++count%2;
            System.out.println("炸弹..."+new Date().getSeconds());
            new Timer().schedule(new MyTimerTask2(),10000+10000*i);
        }
    }
}
