package Atom.File;

import Atom.Manifest;

import java.io.File;

public class FileUtility {


    public static File temp() {
        File temp = new File(Manifest.currentFolder, System.currentTimeMillis() + ".temp");
        temp.deleteOnExit();
        return temp;
    }
}
