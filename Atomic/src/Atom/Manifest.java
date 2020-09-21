package Atom;


import Atom.Reflect.Reflect;
import Atom.Utility.Digest;
import Atom.Utility.Encoder;

import java.io.File;


public class Manifest {
    private static String signature;
    public static File workingDir = new File("AtomicWorkDir/");

    static {
        try {
            signature = Encoder.getString(Digest.sha1(Reflect.getCurrentJar(Manifest.class)));
        }catch (Throwable aa){
            signature = aa.toString();
        }
    }


    public static String getSignature() {
       return signature;
    }


}
