import java.util.concurrent.*;


////用while强制main线程进行等待  在小线程完成后 断开线程池
////   用主线程sleep方法 让main线程等待 线程池的方法完成
public class method3_1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Boolean fa = true;
        long start = System.currentTimeMillis();
        Object o = new Object();
        // 在这里创建一个线程或线程池，
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        // 异步执行 下面方法
        Future<?> submit = executorService.submit(new Callable<Object>(

        ) {
            @Override
            public Object call() throws Exception {

                int result = sum();
                return result;
            }
        });

        while(fa){
            Thread thread = Thread.currentThread();
            if (submit.get()!=null) {
                thread.sleep(1000);
                System.out.println(submit.get());
                executorService.shutdown();
            }

            fa=false;
        }


        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms"+"主线程退出时间");

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
