package Atom.Struct;

import java.util.Arrays;
import java.util.HashMap;

@Deprecated
public class Arguments {
    private HashMap<String, String> hashMap = new HashMap<>();


    public Arguments(String arg, String prefixKey, String prefixValue) {
        this(arg.split(prefixValue), prefixKey);
    }

    public Arguments(String[] arg, String prefix) {
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
        hashMap = args;
    }

    public Arguments(HashMap<String, String> a) {
        hashMap = a;
    }

    public Arguments() {

    }

    public static Arguments parse(String arg, String prefixKey, String prefixValue) {
        if (arg.isEmpty()) return new Arguments();
        return new Arguments(arg, prefixKey, prefixValue);
    }

    public static Arguments parse(String arg, String prefixKey) {
        return parse(arg, prefixKey, " ");
    }

    public void add(String key, String value) {
        if (hashMap.containsKey(key))
            hashMap.replace(key, value);
        else
            hashMap.put(key, value);
    }

    public String get(String key) {
        return hashMap.getOrDefault(key, "");
    }

    public String get(String key, String def) {
        return hashMap.getOrDefault(key, def);
    }

    public int size() {
        return hashMap.size();
    }
}
