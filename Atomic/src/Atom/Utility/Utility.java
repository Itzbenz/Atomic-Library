package Atom.Utility;


import java.util.Arrays;
import java.util.HashMap;

public class Utility {

    public static HashMap<String, String> ArgParser(String[] arg, String prefix) {

        HashMap<String, String> args = new HashMap<>();

        if (arg.length % 2 != 0)
            throw new IllegalArgumentException("Not Enough Argument" + Arrays.toString(arg));
        boolean i = true;
        String k = "";
        for (String d : arg) {
            if (d.startsWith(prefix)) {
                k = d.replaceFirst(prefix, "");
            } else {
                args.put(k, d);
            }
            i = !i;
        }

        return args;
    }

    public static int getJavaMajorVersion(){
        String version;
        version = System.getProperty("java.version");
        if(version == null || version.isEmpty())
            version = System.getProperty("java.runtime.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);

    }

    public static String joiner(String[] datas, String prefix) {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < datas.length; i++) {
            data.append(datas[i]);
            if (i != datas.length - 1)
                data.append(prefix);
        }
        return data.toString();
    }
}
