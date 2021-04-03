package Atom.File;

import Atom.Manifest;
import Atom.Struct.Filter;
import Atom.Utility.Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

import static Atom.Reflect.OS.*;
//agh
public class FileUtility {
	
	public static ArrayList<File> findAll(File parent, Filter<File> filter) {
		ArrayList<File> files = new ArrayList<>();
		HashSet<File> needToRecurse = new HashSet<>();
		needToRecurse.add(parent);
		if (!parent.isDirectory()) {
			files.add(parent);
			return files;
		}
		while (!needToRecurse.isEmpty()) {
			File f = needToRecurse.iterator().next();
			for (File fe : f.listFiles()) {
				if (filter.accept(fe)) files.add(fe);
				if (fe.isDirectory()) needToRecurse.add(fe);
			}
			needToRecurse.remove(f);
		}
		return files;
	}
	
	public static String getExtension(File f) {
		String extension = "";
		int i = f.getName().lastIndexOf('.');
		if (i > 0) {
			extension = f.getName().substring(i + 1);
		}
		return extension;
	}
	
	public static File getCurrentWorkingDir() {
		File f;
		try {
			f = new File(System.getProperty("user.dir"));
			if (!f.exists()) throw new RuntimeException("no u");
		}catch (Throwable ignored) {
			try {
				f = new File(getAppdata(), "Atomic/");
				if (!f.exists()) throw new RuntimeException("no u");
			}catch (Throwable ignoreds) {
				try {
					f = new File(getTempDir(), "Atomic/");
					if (!f.exists()) throw new RuntimeException("no u");
				}catch (Throwable i) {
					f = new File("Atomic/");
					//gave up already
				}
			}
		}
		return f;
	}
	
	public static File getTempDir() {
		File f;
		try {
			f = new File(System.getProperty("java.io.tmpdir"));
			if (!f.exists()) f.mkdirs();
			
			if (!f.exists()) throw new RuntimeException("no u");
		}catch (Throwable ignored) {
			f = new File(getCurrentWorkingDir(), "cache/");
		}
		return f;
	}
	
	public static File getAppdata() {
		if (isWindows) {
			return new File(System.getenv("AppData"));
		}else if (!isIos && !isAndroid) {
			if (isLinux) {
				if (System.getenv("XDG_DATA_HOME") != null) {
					String dir = System.getenv("XDG_DATA_HOME");
					if (!dir.endsWith("/")) {
						dir = dir + "/";
					}
					
					return new File(dir);
				}else {
					return new File(getProperty("user.home") + "/.local/share/");
				}
			}else {
				return isMac ? new File(getProperty("user.home") + "/Library/Application Support/") : null;
			}
		}else if (isAndroid) {
			return new File(getTempDir().getParent());
		}else {
			return null;
		}
	}
	
	public static Process runJar(File jar) throws IOException {
		String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		if (Manifest.isWindows()) javaBin += ".exe";
		if (!new File(javaBin).exists()) throw new FileNotFoundException(new File(javaBin).getAbsolutePath());
		if (jar.exists()) throw new FileNotFoundException(new File(javaBin).getAbsolutePath());
		//it is a jar ?
		if (!jar.getName().endsWith(".jar")) throw new RuntimeException(jar.getAbsolutePath() + " is not a jar");
		
		//java -jar path/to/Mindustry.jar
		ArrayList<String> command = new ArrayList<>();
		command.add(javaBin);
		command.add("-jar");
		command.add(jar.getPath());

		ProcessBuilder builder = new ProcessBuilder(command);
		return builder.start();
	}


	public static boolean append(File file, byte[] bytes) throws IOException {
		return write(file, bytes, true);
	}

	public static boolean write(File dst, byte[] bytes) {
		return write(dst, bytes, false);
	}

	public static boolean write(File dst, byte[] bytes, boolean append) {
		try (OutputStream out = new FileOutputStream(dst, append)) {
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Throwable t) {
			return false;
		}

	}


	public static boolean makeFile(File file) {
		try {
			file.getParentFile().mkdirs();
		}catch (Throwable javabeinggay) {}
		try {
			return file.createNewFile();
		}catch (Throwable ignored) {
		}
		return false;
	}

	public static File temp() {
		File temp = new File(getTempDir(), System.currentTimeMillis() + ".temp");
		temp.deleteOnExit();
		return temp;
	}

	public static byte[] readAllBytes(File src) throws IOException {
		InputStream is = new FileInputStream(src);
		return Encoder.readAllBytes(is);

	}

	public static boolean replace(File src, File dst) {
		return copy(src, dst, true);
	}

	public static boolean copy(File src, File dst) {
		return copy(src, dst, false);
	}

	public static boolean copy(File src, File dst, boolean replace) {
		if (dst.exists() && replace) return false;
		try (InputStream in = new FileInputStream(src)) {
			try (OutputStream out = new FileOutputStream(dst)) {
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.flush();
				out.close();
			}
			return true;
		} catch (Throwable t) {
			return false;
		}
	}
}
