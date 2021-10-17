import java.util.concurrent.*;

public class method2_3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Boolean fa=true;
        long start=System.currentTimeMillis();
        Object o = new Object();
        // 在这里创建一个线程或线程池，
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 异步执行 下面方法
        Future<?> submit = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                int result = sum();
                return result;
            }
        });

        while (fa){
            Thread.currentThread().wait();
            if (submit.get()!=null) {
                System.out.println(submit.get());
                executorService.shutdown();
                //这是得到的返回值
            }
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms"+"主线程退出时间");
            fa=false;
            System.out.println(Thread.currentThread().getName());
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
