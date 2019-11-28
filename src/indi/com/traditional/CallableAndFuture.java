package indi.com.traditional;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 09_CallableAndFuture
 */
public class CallableAndFuture {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<String> future =
        threadPool.submit(
                new Callable<String>() {
                    @Override
                    public String call() {
                        return "hello";
                    }
                }
        );
        try {
            System.out.println("结果"+future.get());
        }catch (Exception e){

        }


        ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
        for (int i = 0;i<10;i++){
            final int seq = i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(1000));
                    return seq;
                }
            });
        }

        for (int i = 0;i<10;i++){
            try {
                System.out.println(completionService.take().get());
            }catch (Exception e){

            }
        }

    }
}
