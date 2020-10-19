package Atom.File;

import Atom.Manifest;
import jdk.internal.reflect.Reflection;

import java.io.File;

public class FileUtility {

    public static File temp() {
        File temp = new File(Manifest.currentFolder, Reflection.getCallerClass().getName() + System.currentTimeMillis() + ".temp");
        temp.deleteOnExit();
        return temp;
    }
}
