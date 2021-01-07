package Atom.Utility;

import java.util.List;

//Random get more random when used really often
public class Random extends java.util.Random {
	
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
		Random random = new Random();
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
		return new Random().nextFloat();
	}
	
	public static double getDouble() {
		return new Random().nextDouble();
	}
	
	public static long getLong() {
		return new Random().nextLong();
	}
	
	public static boolean getBool() {
		return new Random().nextBoolean();
	}
	
	public static int getInt() {
		return new Random().nextInt();
	}
	
	public static int getInt(int max) {
		return getInt(0, max);
	}
	
	public static int getInt(int min, int max) {
		java.util.Random random = new java.util.Random();
		return random.nextInt((max - min) + 1) + min;
	}
	
	public static <T> T getRandom(List<T> arrays) {
		int rnd = new java.util.Random().nextInt(arrays.size());
		return arrays.get(rnd);
	}
	
	public static <T> T getRandom(T[] arrays) {
		int rnd = new java.util.Random().nextInt(arrays.length);
		return arrays[rnd];
	}
	
	public static long getLong(long min, long max) {
		return min + ((long) (new Random().nextDouble() * (max - min)));
	}
	
	public static long getLong(long max) {
		return getLong(0, max);
	}
	
	public static double getDouble(double min, double max) {
		return min + (new Random().nextDouble() * (max - min));
	}
	
	public static double getDouble(double max) {
		return getDouble(0, max);
	}
	
	public static float getFloat(float min, float max) {
		return min + (new Random().nextFloat() * (max - min));
	}
	
	public static float getFloat(float max) {
		return getFloat(0, max);
	}
}
