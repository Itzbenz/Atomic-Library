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

    public static boolean getBool() {
        return getInt(0, 1) != 0;
    }

    public static int getInt() {
        return getInt(0, 10000);
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
}
