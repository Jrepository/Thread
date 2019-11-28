package indi.com.traditional;

/**
 * 04_TraditionalThreadCommunication
 *
 * 面试题：
 * 子线程循环10次，接着主线程循环100次，
 * 接着又回到子线程循环10次，接着再回到主线程循环10次，
 * 如此循环50次
 *
 * 思路：
 *     1、先实现互斥，即子线程与子线程互不干扰，加锁保护，索可以使用类的字节码（不建议），xxx.class
 *     2、通讯
 */
public class TraditionalThreadCommunication {


    public static void main(String[] args) {
        Business business = new Business();
        //1、创建线程
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<50;i++){
//                             for (int j=0;j<10;j++) {
//                                 System.out.println("子线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
//                             }
                            business.sub(i);
                        }
                    }
                }
        ).start();


        for (int i=0;i<50;i++){
//            for (int j=0;j<10;j++) {
//                System.out.println("主线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
//            }
            business.main(i);
        }






//        int count = 0;
//       // 内部类不能访问局部变量，需要给局部变量加上final
//        for (int i = 0;i<50;i++){
//            System.out.println("======第"+i+"次======");
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                        for (int j = 0;j<10;j++){
//                            System.out.println("子线程:j="+j+"***"+Thread.currentThread().getName());
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//            thread.start();
//            //主线程
//            for (int j = 0;j<100;j++){
//                System.out.println("主线程:j="+j+"***"+Thread.currentThread().getName());
//            }
//        }


    }

}
/**
 * 业务处理
 */
class Business{
    private boolean flag = true;//标志子线程方法是否被调用
    //主线程的业务
    public synchronized void main(int j){
//        if(flag){
        while(flag){//子线程方法正在被调用，主线程方法等待
            try {
                this.wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        for (int i=0;i<100;i++) {
            System.out.println("主线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
        }
        flag = true;
        this.notify();
    }
    //子线程的业务
    public synchronized void sub(int j){
//        if(!flag){    //使用while，防止伪唤醒，相比if更加健壮
        while(!flag){//主线程方法正在被调用，子线程方法等待
            try{
                this.wait();
            }catch (Exception e){

            }
        }
        for (int i=0;i<10;i++) {
            System.out.println("子线程:i="+i+"***j="+j+"    "+Thread.currentThread().getName());
        }
        flag = false;
        this.notify();
    }

}
