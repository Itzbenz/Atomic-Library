package Atom.File;

import Atom.Manifest;

import java.io.File;
import java.nio.file.Files;

public class FileUtility {

    public static boolean createFile(File file) {
        try {
            Files.createFile(file.toPath());
            return true;
        } catch (Throwable ignored) {
            //probraly already exists
        }
        return false;
    }

    public static File temp() {
        File temp = new File(Manifest.currentFolder, System.currentTimeMillis() + ".temp");
        temp.deleteOnExit();
        return temp;
    }
}
