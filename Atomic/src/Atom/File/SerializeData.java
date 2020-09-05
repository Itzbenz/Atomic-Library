package Atom.File;

import java.io.*;

public class SerializeData {


    public static <T> void DataOut(T ish, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ish);
        oos.close();
    }

    public static <T> T DataIn(File file) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        T iHandler = (T) ois.readObject();
        ois.close();
        return iHandler;
    }
}
