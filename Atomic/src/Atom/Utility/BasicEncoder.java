package Atom.Utility;

//import Atom.Annotation.Utils.Annotation;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BasicEncoder {

    //@Annotation.Replicate(source = "SHA-256", target = {"SHA1, MD5"}, clazz = "Encoder")
    public static byte[] sha256(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(text.getBytes(Charset.forName("UTF-8")));
    }
    //@Annotation.Replicate(source = "SHA-256", target = {"SHA1, MD5"}, clazz = "Encoder")
    public static byte[] sha256(File file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(Files.readAllBytes(file.toPath()));
    }
}
