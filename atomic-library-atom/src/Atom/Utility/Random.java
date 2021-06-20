package Atom.Utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

//Random get more random when used really often
public class Random extends java.util.Random {
    private static final java.util.Random atomicRandom = new java.util.Random();
    
    public static synchronized void seed(long seed) {
        atomicRandom.setSeed(seed);
    }
    
    public static void getNextBytes(byte[] bytes) {
        atomicRandom.nextBytes(bytes);
    }
    
    public static int getNextInt() {
        return atomicRandom.nextInt();
    }
    
    public static int getNextInt(int bound) {
        return atomicRandom.nextInt(bound);
    }
    
    public static long getNextLong() {
        return atomicRandom.nextLong();
    }
    
    public static boolean getNextBoolean() {
        return atomicRandom.nextBoolean();
    }
    
    public static float getNextFloat() {
        return atomicRandom.nextFloat();
    }
    
    public static double getNextDouble() {
        return atomicRandom.nextDouble();
    }
    
    public static synchronized double getNextGaussian() {
        return atomicRandom.nextGaussian();
    }
    
    public static IntStream getInts(long streamSize) {
        return atomicRandom.ints(streamSize);
    }
    
    public static IntStream getInts() {
        return atomicRandom.ints();
    }
    
    public static IntStream getInts(long streamSize, int randomNumberOrigin, int randomNumberBound) {
        return atomicRandom.ints(streamSize, randomNumberOrigin, randomNumberBound);
    }
    
    public static IntStream getInts(int randomNumberOrigin, int randomNumberBound) {
        return atomicRandom.ints(randomNumberOrigin, randomNumberBound);
    }
    
    public static LongStream getLongs(long streamSize) {
        return atomicRandom.longs(streamSize);
    }
    
    public static LongStream getLongs() {
        return atomicRandom.longs();
    }
    
    public static LongStream getLongs(long streamSize, long randomNumberOrigin, long randomNumberBound) {
        return atomicRandom.longs(streamSize, randomNumberOrigin, randomNumberBound);
    }
    
    public static LongStream getLongs(long randomNumberOrigin, long randomNumberBound) {
        return atomicRandom.longs(randomNumberOrigin, randomNumberBound);
    }
    
    public static DoubleStream getDoubles(long streamSize) {
        return atomicRandom.doubles(streamSize);
    }
    
    public static DoubleStream getDoubles() {
        return atomicRandom.doubles();
    }
    
    public static DoubleStream getDoubles(long streamSize, double randomNumberOrigin, double randomNumberBound) {
        return atomicRandom.doubles(streamSize, randomNumberOrigin, randomNumberBound);
    }
    
    public static DoubleStream getDoubles(double randomNumberOrigin, double randomNumberBound) {
        return atomicRandom.doubles(randomNumberOrigin, randomNumberBound);
    }
    
    public static String getString() {
        return getString(8);
    }
    
    public static String getString(int length) {
        return getString(length, 125);
    }
    
    public static String getString(int length, int max) {
        return getString(length, 33, max);
    }
    
    public static String getString(int length, int min, int max) {
        if (length < 0) return "";
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            sb.append((char) getInt(min, max));
        }
        return sb.toString();
    }
    
    public static int getRandomColor() {
        return atomicRandom.nextInt(0xffffff + 1);
    }
    
    public static String getRandomHexColor() {
        int getNextInt = Random.getInt(5000000, 16777215);
        return String.format("#%06x", getNextInt);
    }
    
    public static String getHex(int h) {
        return String.format("#%06x", h);
    }
    
    public static boolean getBool(float chance) {
        return getFloat() < chance;
    }
    
    public static float getFloat() {
        return atomicRandom.nextFloat();
    }
    
    public static double getDouble() {
        return atomicRandom.nextDouble();
    }
    
    public static long getLong() {
        return atomicRandom.nextLong();
    }
    
    public static boolean getBool() {
        return atomicRandom.nextBoolean();
    }
    
    public static int getInt() {
        return atomicRandom.nextInt();
    }
    
    public static int getInt(int max) {
        return getInt(0, max);
    }
    
    public static int getInt(int min, int max) {
        return atomicRandom.nextInt((max - min) + 1) + min;
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
        int rnd = getInt(arrays.size() - 1);
        return arrays.get(rnd);
    }
    
    public static <T> T getRandom(T[] arrays) {
        if (arrays.length == 0) return null;
        int rnd = getInt(1 - arrays.length);
        return arrays[rnd];
    }
    
    public static long getLong(long min, long max) {
        return min + ((long) (atomicRandom.nextDouble() * (max - min)));
    }
    
    public static long getLong(long max) {
        return getLong(0, max);
    }
    
    public static double getDouble(double min, double max) {
        return min + (atomicRandom.nextDouble() * (max - min));
    }
    
    public static double getDouble(double max) {
        return getDouble(0, max);
    }
    
    public static float getFloat(float min, float max) {
        return min + (atomicRandom.nextFloat() * (max - min));
    }
    
    public static float getFloat(float max) {
        return getFloat(0, max);
    }
}
