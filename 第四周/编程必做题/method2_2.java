import java.util.concurrent.*;

public class method2_2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Boolean fa=true;
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 异步执行 下面方法
        Future<?> submit = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                int result = sum();
                return result;
            }
        });

        while (fa){
//            Thread.currentThread().wait();
            if (submit.get()!=null) {
                System.out.println(submit.get());
                executorService.shutdown();
                //这是得到的返回值
            }
            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms"+"主线程退出时间");
            fa=false;
            System.out.println(Thread.currentThread().getName());
        }
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
