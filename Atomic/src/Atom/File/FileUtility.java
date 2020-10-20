package Atom.File;

import Atom.Manifest;

import java.io.File;

public class FileUtility {


    public static boolean makeFile(File file) {
        file.getParentFile().mkdirs();
        try {
            return file.createNewFile();
        } catch (Throwable ignored) {
        }
        return false;
    }

    public static File temp() {
        File temp = new File(Manifest.currentFolder, System.currentTimeMillis() + ".temp");
        temp.deleteOnExit();
        return temp;
    }
}
