package Atom.File;

import Atom.Manifest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static Atom.Reflect.OS.*;

public class FileUtility {
	
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
	
	
	public static void append(File file, byte[] bytes) throws IOException {
		Files.write(file.toPath(), bytes, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
	}
	
	public static void write(File file, byte[] bytes) throws IOException {
		makeFile(file);
		Files.write(file.toPath(), bytes, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
	}
	
	
	public static boolean makeFile(File file) {
		file.getParentFile().mkdirs();
		try {
			return file.createNewFile();
		}catch (Throwable ignored) {
		}
		return false;
	}
	
	public static File temp() {
		File temp = new File(Manifest.currentFolder, System.currentTimeMillis() + ".temp");
		temp.deleteOnExit();
		return temp;
	}
}
