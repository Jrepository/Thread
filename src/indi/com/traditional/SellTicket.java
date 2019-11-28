package indi.com.traditional;

/**
 * 07_SellTicket
 *
 * 卖票系统
 *
 * 多线程共享数据：如果每个线程执行代码相同，可以使用同一个Runnable对象，这个Runnable对象中有共享数据，eg:买票系统；
 */
public class SellTicket {
    public static void main(String[] args) {
        TicketWindow ticketWindow = new TicketWindow();
        for (int i=0;i<4;i++){//4个线程，相当于4个售票窗口
            new Thread(ticketWindow).start();
        }

    }

}

class TicketWindow implements Runnable{

    private int count = 100;//100张票
    @Override
    public void run() {
        while (true){
            sellTicket();
        }
    }

    private synchronized void sellTicket() {
        if(count>0){
            count--;
            System.out.println(Thread.currentThread().getName()+"   卖出一张票,还有"+count+"张票");
        }else{
            System.out.println("票已卖完...");
        }
    }
}
