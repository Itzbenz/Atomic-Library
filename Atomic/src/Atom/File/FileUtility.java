package Atom.File;

import Atom.Manifest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileUtility {


    public static Process runJar(File jar) throws IOException {
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        if (Manifest.isWindows())
            javaBin += ".exe";
        if (!new File(javaBin).exists())
            throw new FileNotFoundException(new File(javaBin).getAbsolutePath());
        if (jar.exists())
            throw new FileNotFoundException(new File(javaBin).getAbsolutePath());
        //it is a jar ?
        if (!jar.getName().endsWith(".jar"))
            throw new RuntimeException(jar.getAbsolutePath() + " is not a jar");

        //java -jar path/to/Mindustry.jar
        ArrayList<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(jar.getPath());

        ProcessBuilder builder = new ProcessBuilder(command);
        return builder.start();
    }


    public static boolean write(File file, byte[] bytes) {
        try {
            makeFile(file);
            Files.write(file.toPath(), bytes, StandardOpenOption.WRITE);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    public static boolean append(File file, byte[] bytes) {
        try {
            makeFile(file);
            Files.write(file.toPath(), bytes, StandardOpenOption.APPEND);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

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
