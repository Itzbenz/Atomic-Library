package Atom.Utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

public class EncoderJson {
	
	public static HashMap<String, String> jsonToMap(JsonObject jo) {
		HashMap<String, String> map = new HashMap<>();
		for (Map.Entry<String, JsonElement> s : jo.entrySet()) {
			if (s.getValue() instanceof JsonPrimitive) map.put(s.getKey(), s.getValue().getAsString());
			if (s.getValue() instanceof JsonObject) map.putAll(jsonToMap((JsonObject) s.getValue()));
		}
		return map;
	}
	
	public static JsonObject mapToJson(HashMap<String, String> h) {
		return new Gson().toJsonTree(h).getAsJsonObject();
	}
}
