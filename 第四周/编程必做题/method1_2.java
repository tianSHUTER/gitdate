import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class method1_2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Boolean fa=true;
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        ExecutorService executorService = Executors.newCachedThreadPool();
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

            if (submit.get()!=null) {
                executorService.shutdown();
                //这是得到的返回值
            }
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms"+"主线程退出时间");
            fa=false;
        }
        // 然后退出main线程
        System.out.println(Thread.currentThread());
        ;
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
