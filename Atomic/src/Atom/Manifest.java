package Atom;


import Atom.Reflect.Reflect;
import Atom.Utility.Digest;
import Atom.Utility.Encoder;

import java.io.File;

public class Manifest {
    private static final String signature = "12431DGSHRHRTH5917354163SAFJFEG";
    public static File workingDir = new File("AtomicWorkDir/");

    public static boolean checkIntegrity() {
        return Reflect.getExtendedClass("Atom", java.lang.Object.class).size() > 1;
    }

    public static String signature() {
        try {
            return Encoder.getString(Digest.md5(Reflect.getCurrentJar(Manifest.class)));
        } catch (Throwable t) {
            return signature;
        }
    }


}
