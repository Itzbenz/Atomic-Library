package Atom.Utility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Encoder {
    public static String base64Encode(byte[] bytes){
        return getString(Base64.getEncoder().encode(bytes));
    }
    public static byte[] base64Decode(String s){
        return Base64.getDecoder().decode(s);
    }
    public static String getString(byte[] bytes){
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
