package Atom.Utility;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Meth {
	
	public static long max(Long[] arr) {
		List<Long> b = Arrays.asList(arr);
		return Collections.max(b);
	}
	
	public static long min(Long[] arr) {
		List<Long> b = Arrays.asList(arr);
		return Collections.min(b);
	}
	
	public static double avg(Iterable<Long> arr) {
		long sum = 0;
		int length = 0;
		for (long l : arr) {
			sum += l;
			length++;
		}
		return (double) sum / length;
	}
	
	public static int positive(int i) {
		if (i < 0) return -i;
		return i;
	}
	
	public static int negative(int i) {
		if (i > 0) return -i;
		return i;
	}
	
	public static float positive(float i) {
		if (i < 0) return -i;
		return i;
	}
	
	public static float negative(float i) {
		if (i > 0) return -i;
		return i;
	}
	
	public static long positive(long i) {
		if (i < 0) return -i;
		return i;
	}
	
	public static long negative(long i) {
		if (i > 0) return -i;
		return i;
	}
	
	public static double positive(double i) {
		if (i < 0) return -i;
		return i;
	}
	
	public static double negative(double i) {
		if (i > 0) return -i;
		return i;
	}
}
