package Atom;

public class Meth {

    public static int positive(int i) {
        if (i < 0) return -i;
        return i;
    }

    public static int negative(int i) {
        if (i > 0) return -i;
        return i;
    }
}
