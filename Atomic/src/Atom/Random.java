package Atom;

public class Random {

    public static String getString(){
        return getString(8);
    }
    public static String getString(int length){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append((char) getInt(33, 125));
        }
        return sb.toString();
    }

    public static boolean getBool(){
        return getInt(0, 1) != 0;
    }
    public static int getInt(){
      return getInt(0, 10000);
    }

    public static int getInt(int min, int max){
        java.util.Random random = new java.util.Random();

        return random.nextInt((max - min) + 1) + min;
    }
}
