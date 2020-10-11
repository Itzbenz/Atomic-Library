package Atom.File;

import java.io.*;
import java.util.ArrayList;

public class SerializeData {


    public static <T> void dataOut(T ish, File file) throws IOException {
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ish);
        oos.close();
    }

    public static <T> T dataIn(File file) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        T iHandler = (T) ois.readObject();
        ois.close();
        return iHandler;
    }

    public static <T> void dataOut(ArrayList<T> ish, File file) throws IOException {
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ish);
        oos.close();
    }

    public static <T> ArrayList<T> dataArrayIn(File file) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        ArrayList<T> iHandler;
        iHandler = (ArrayList<T>) ois.readObject();
        ois.close();
        return iHandler;
    }

}
