package Atom.Utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.WeakHashMap;

public class EncoderJson {
    public static Gson gson = new Gson();
    public static WeakHashMap<String, JsonElement> cache = new WeakHashMap<>();
    
    public static JsonElement parseJson(String s) {
        if (!cache.containsKey(s)) {
            cache.put(s, JsonParser.parseString(s));
        }
        return cache.get(s);
    }
    
    public static JsonObject mapToJson(HashMap<String, String> h) {
        return gson.toJsonTree(h).getAsJsonObject();
    }
}
