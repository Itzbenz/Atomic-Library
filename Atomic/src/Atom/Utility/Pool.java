package Atom.Utility;

import java.util.concurrent.*;

public class Pool {
    private static final ExecutorService service = Executors.newCachedThreadPool();


    public static Future<?> submit(RunnableFuture<?> future) {
        return service.submit(future);
    }

    public static <V> Future<V> submit(Callable<V> future) {
        return service.submit(future);
    }

}
