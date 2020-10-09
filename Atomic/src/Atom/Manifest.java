package Atom;


import Atom.Classloader.UClassloader;
import Atom.Net.HTPS;
import Atom.Reflect.Reflect;
import Atom.Utility.Digest;
import Atom.Utility.Encoder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Manifest {
    public static final ArrayList<Library> library = new ArrayList<>();
    public static File currentJar = Reflect.getCurrentJar(), currentFolder = currentJar.getParentFile(), workingDir = new File("Atomic");
    private static String signature;

    static {
        try {
            signature = Encoder.getString(Digest.sha1(Reflect.getCurrentJar(Manifest.class)));
        } catch (Throwable aa) {
            signature = aa.toString();
        }
        library.add(new Library(1.8F, "google-java-format", "https://github.com/google/google-java-format/releases/download/google-java-format-1.7/google-java-format-1.7-all-deps.jar", currentFolder));


    }

    public static void downloadAll() throws ExecutionException, InterruptedException, IOException {
        for (Library l : library)
            if (!l.download().get().exists()) throw new IOException("Failed to download: " + l.jar.getName());
    }

    public static void loadAll() throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ExecutionException, InterruptedException {
        for (Library l : library) {
            if (!l.downloaded()) l.download().get();
            l.tryLoad();
        }
    }

    public static String getSignature() {
        return signature;
    }

    public static class Library {
        public final String name, link;
        public final float version;
        public final File jar;
        private Future<File> download = null;

        public Library(Float version, String name, String link, File folder) {
            this.version = version;
            this.name = name;
            this.link = link;
            jar = new File(folder, name + version + ".jar");
        }

        public Future<File> download() {
            if (download != null) return download;
            return download = HTPS.download(link, jar);
        }

        //URLClassloader hack
        public void tryLoad() throws InvocationTargetException, IOException, NoSuchMethodException, IllegalAccessException {
            if (downloaded()) {
                if (Manifest.class.getClassLoader() instanceof URLClassLoader)
                    UClassloader.addURL((URLClassLoader) Manifest.class.getClassLoader(), jar);
            } else {
                throw new IOException("File doesn't exists");
            }
        }

        public boolean downloaded() {
            return jar.exists();
        }
    }

}
