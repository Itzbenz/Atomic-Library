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
    }

    public static File[] getLibs() {
        ArrayList<File> f = new ArrayList<>();
        library.forEach(library1 -> {
            f.add(library1.jar);
        });
        return f.toArray(new File[0]);
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

    public static class Library {
        public final String name, link;
        public final String version;
        public final File jar;
        public boolean downloaded;
        private Future<File> download = null;

        public Library(String version, String name, String link, File folder) {
            this.version = version;
            this.name = name;
            this.link = link;
            jar = new File(folder, name + version + ".jar");
            downloaded = downloaded();
        }

        public Future<File> download() {
            if (download != null) return download;
            return download = HTPS.download(link, jar);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Library{").append('\n');
            sb.append("name='").append(name).append('\n');
            sb.append(", link='").append(link).append('\n');
            sb.append(", version='").append(version).append('\n');
            sb.append(", jar=").append(jar).append('\n');
            sb.append(", downloaded=").append(downloaded).append('\n');
            sb.append('}');
            return sb.toString();
        }

        public boolean downloaded() {
            return jar.exists();
        }
    }

}
