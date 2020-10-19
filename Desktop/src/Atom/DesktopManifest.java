package Atom;

import static Atom.Manifest.*;

public class DesktopManifest {
    static {
        platform = "Desktop";
        library.add(new Library("3.16.2", "com.github.javaparser-javaparser-core", "https://repo1.maven.org/maven2/com/github/javaparser/javaparser-core/3.16.2/javaparser-core-3.16.2.jar", currentFolder));
    }
}
