import sun.misc.Lock;

import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 一个简单的代码参考：
 */
//用while强制main线程进行等待  在小线程完成后 断开线程池
// 运用到 线程池的 submit  方法的get  还获取线程池的完成情况

public class method1_1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
         Boolean fa=true;
        long start=System.currentTimeMillis();
    Object o = new Object();
        // 在这里创建一个线程或线程池，
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        // 异步执行 下面方法
        Future<?> submit = executorService.submit(new Runnable() {
            @Override
            public void run() {
                int result = sum();
                // 确保  拿到result 并输出
                System.out.println("异步计算结果为：" + result);
            }
        });

        while (fa){
            if (submit.get()==null) {
                executorService.shutdown();
                //这是得到的返回值
            }
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms"+"主线程退出时间");
            fa=false;
        }
        // 然后退出main线程
    }
    
    private static int sum() {
        return fibo(36);
    }
    
    private static int fibo(int a) {
        if ( a < 2) 
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
