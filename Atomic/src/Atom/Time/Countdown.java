package Atom.Time;


import Atom.Struct.Queue;

import java.util.concurrent.TimeUnit;

public class Countdown {
	private static Queue<Long> lastCount;
	private static long resultCount;
	
	static {
		reset();
	}
	
	public static void start() {
		start(TimeUnit.MICROSECONDS);
	}
	
	public static void start(TimeUnit t) {
		
		lastCount.addLast(System.currentTimeMillis());
	}
	
	public static long stop() {
		if (lastCount.isEmpty()) return 0L;
		long milliseconds = System.currentTimeMillis() - lastCount.removeLast();
		return resultCount = milliseconds;
	}
	
	public static String result() {
		return get() + " Milliseconds";
	}
	
	public static String result(TimeUnit a) {
		return result(get(), a);
	}
	
	public static String result(long a) {
		return result(a, TimeUnit.MILLISECONDS);
	}
	
	public static String result(long a, TimeUnit b) {
		return b.convert(System.currentTimeMillis() - a, TimeUnit.MILLISECONDS) + " " + b.toString();
	}
	
	public static long getActual(long a, long b) {
		return b - a;
	}
	
	public static long getActual(long src) {
		return getActual(src, System.currentTimeMillis());
	}
	
	public static long get() {
		return resultCount;
	}
	
	public static void reset() {
		lastCount = new Queue<>();
		resultCount = 0L;
	}
}
