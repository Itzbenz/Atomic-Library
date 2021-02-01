package Atom.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Random get more random when used really often
public class Random extends java.util.Random {
	private static final Random random = new Random();
	
	public static String getString() {
		return getString(8);
	}
	
	public static String getString(int length) {
		if (length < 0) return "";
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < length; i++) {
			sb.append((char) getInt(33, 125));
		}
		return sb.toString();
	}
	
	public static int getRandomColor() {
		return random.nextInt(0xffffff + 1);
	}
	
	public static String getRandomHexColor() {
		int nextInt = Random.getInt(5000000, 16777215);
		return String.format("#%06x", nextInt);
	}
	
	public static String getHex(int h) {
		return String.format("#%06x", h);
	}
	
	public static boolean getBool(float chance) {
		return getFloat() < chance;
	}
	
	public static float getFloat() {
		return random.nextFloat();
	}
	
	public static double getDouble() {
		return random.nextDouble();
	}
	
	public static long getLong() {
		return random.nextLong();
	}
	
	public static boolean getBool() {
		return random.nextBoolean();
	}
	
	public static int getInt() {
		return random.nextInt();
	}
	
	public static int getInt(int max) {
		return getInt(0, max);
	}
	
	public static int getInt(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}
	
	public static <T> T getRandom(Iterator<T> arr) {
		ArrayList<T> rand = new ArrayList<>();
		while (arr.hasNext()) rand.add(arr.next());
		return getRandom(rand);
	}
	
	public static <T> T getRandom(Iterable<T> arr) {
		ArrayList<T> rand = new ArrayList<>();
		for (T e : arr)
			rand.add(e);
		return getRandom(rand);
	}
	
	public static <T> T getRandom(List<T> arrays) {
		if (arrays.size() == 0) return null;
		int rnd = getInt(arrays.size());
		return arrays.get(rnd);
	}
	
	public static <T> T getRandom(T[] arrays) {
		if (arrays.length == 0) return null;
		int rnd = getInt(arrays.length);
		return arrays[rnd];
	}
	
	public static long getLong(long min, long max) {
		return min + ((long) (random.nextDouble() * (max - min)));
	}
	
	public static long getLong(long max) {
		return getLong(0, max);
	}
	
	public static double getDouble(double min, double max) {
		return min + (random.nextDouble() * (max - min));
	}
	
	public static double getDouble(double max) {
		return getDouble(0, max);
	}
	
	public static float getFloat(float min, float max) {
		return min + (random.nextFloat() * (max - min));
	}
	
	public static float getFloat(float max) {
		return getFloat(0, max);
	}
}
