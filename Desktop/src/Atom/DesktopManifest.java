package Atom;

import static Atom.Manifest.*;

public class DesktopManifest {
	static {
		platform = "Desktop";
		library.add(new MavenLibrary("com.github.javaparser", "javaparser-core", "3.16.2"));
		library.add(new MavenLibrary("org.mongodb", "mongodb-driver-sync", "4.1.0"));
	}
	
	public static void init() {
	
	}
}
