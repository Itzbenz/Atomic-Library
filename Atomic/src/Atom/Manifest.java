package Atom;


import Atom.Net.HTPS;
import Atom.Reflect.Reflect;
import Atom.Utility.Digest;
import Atom.Utility.Encoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Manifest {
    public static final ArrayList<Library> library = new ArrayList<>();
    public static File currentJar = Reflect.getCurrentJar(), currentFolder = currentJar.getParentFile(), workingDir = new File("Atomic");
    private static String signature;
    protected static String platform = "Core";
    static {
        try {
            signature = Encoder.getString(Digest.sha1(Reflect.getCurrentJar(Manifest.class)));
        } catch (Throwable aa) {
            signature = aa.toString();
        }
        tryLoadExtension();
    }

    public static File[] getLibs() {

        ArrayList<File> f = new ArrayList<>();
        library.forEach(library1 -> {
            f.add(library1.jar);
        });
        return f.toArray(new File[0]);
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().contains("WIN");
    }

    public static void tryLoadExtension() {
        try {
            Class.forName("Atom.DesktopManifest");
        } catch (Throwable ignored) {
        }
        try {
            Class.forName("Atom.AndroidManifest");
        } catch (Throwable ignored) {
        }
    }

    //should work on any jdk
    public static boolean javacExists() {
        try {
            Class.forName("com.sun.tools.javac.Main");
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    public static void downloadAll() throws ExecutionException, InterruptedException, IOException {
        for (Library l : library)
            if (!l.download().get().exists()) throw new IOException("Failed to download: " + l.jar.getName());
    }

    public static String getSignature() {
        return signature;
    }

    public static abstract class Library {
        protected String name, downloadURL;
        protected String version;
        protected File jar;
        private Future<File> download = null;

        public Library() {

        }

        public Library(String version, String name, String downloadURL, File folder) {
            this.version = version;
            this.name = name;
            this.downloadURL = downloadURL;
            jar = new File(folder, name + version + ".jar");
        }

        public String getName() {
            return name;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public String getVersion() {
            return version;
        }

        public File getJar() {
            return jar;
        }

        public Future<File> download() {
            if (download != null) return download;
            return download = HTPS.download(downloadURL, jar);
        }

        @Override
        public String toString() {
            return "Library{" + '\n' +
                    "name='" + name + '\n' +
                    ", link='" + downloadURL + '\n' +
                    ", version='" + version + '\n' +
                    ", jar=" + jar + '\n' +
                    ", downloaded=" + downloaded() + '\n' +
                    '}';
        }

        public boolean downloaded() {
            return jar.exists();
        }
    }


    public static class JitpackLibrary extends Library {
        StringBuilder jitpack = new StringBuilder("https://jitpack.io/");

        //com.github.o7-Fire.Atomic-Library
        //bdf20a1954
        public JitpackLibrary(String github, String version) {
            name = github.substring(github.lastIndexOf('.')) + "-" + version;
            this.version = version;
            this.jar = new File(currentFolder, name + ".jar");
            jitpack.append(github.replaceAll("\\.", "/")).append(name).append("jar");
            downloadURL = jitpack.toString();
        }
        //com.github.o7-Fire.Atomic-Library
        //Desktop
        //bdf20a1954
        /*
        public JitpackLibrary(String github, String child, String version) {
            name = github.substring(github.lastIndexOf('.')) + "-" + child.replaceAll(":", "-") + "-" + version;
            this.version = version;
            this.jar = new File(currentFolder, name + ".jar");
            child = child.replaceAll(":", "-");
            jitpack.append(github.replaceAll("\\.", "/")).append().append("jar");
            downloadURL = jitpack.toString();
        }
        */
    }

    public static class MavenLibrary extends Library {
        StringBuilder maven = new StringBuilder("https://repo1.maven.org/maven2/");

        public MavenLibrary(String group, String name, String version) {
            StringBuilder nameVersion = new StringBuilder(name);
            nameVersion.append("-").append(version);
            maven.append(group.replaceAll("\\.", "/")).append("/").append(name);
            maven.append("/").append(version).append("/");
            maven.append(nameVersion).append(".jar");
            downloadURL = maven.toString();
            this.version = version;
            this.name = nameVersion.toString();
            this.jar = new File(currentFolder, nameVersion.append(".jar").toString());
        }

    }

    public static class OtherLibrary extends Library {

    }

}
