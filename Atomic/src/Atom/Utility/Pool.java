package Atom.Utility;

import java.util.concurrent.*;

//wtf ?
public class Pool {
	private static final ExecutorService service = Executors.newCachedThreadPool();
	
	public static Future<?> submit(RunnableFuture<?> future) {
		return service.submit(future);
	}
	
	public static <V> Future<V> submit(Callable<V> future) {
		return service.submit(future);
	}
	
	public static Future<?> submit(Runnable r) {
		return service.submit(r);
	}
	
	public static Thread daemon(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}
}
