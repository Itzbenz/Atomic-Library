package Atom.Utility;

import Atom.Reflect.Reflect;

import java.util.concurrent.*;

//wtf ?
public class Pool {
	public static ExecutorService service = Executors.newCachedThreadPool(r -> {
		Thread t = Executors.defaultThreadFactory().newThread(r);
		t.setName(t.getName() + "-Atomic-Pool");
		t.setDaemon(true);
		return t;
	});
	
	public static Future<?> submit(RunnableFuture<?> future) {
		return service.submit(future);
	}
	
	public static <V> Future<V> submit(Callable<V> future) {
		return service.submit(future);
	}
	
	public static Future<?> submit(Runnable r) {
		return service.submit(r);
	}
	
	public static Thread thread(Runnable r) {
		Thread t = new Thread(r);
		t.setName(Reflect.getCallerClassStackTrace().toString());
		return t;
	}
	
	public static Thread daemon(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		t.setName(Reflect.getCallerClassStackTrace().toString());
		return t;
	}
}
