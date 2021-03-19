package Atom.Utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

public class EncoderJson {
	
	public static HashMap<String, String> jsonToMap(JsonElement jo, String prefix) {
		HashMap<String, String> map = new HashMap<>();
		if(	jo.isJsonNull()){
			return map;
		}else if(jo.isJsonObject()) {
			for (Map.Entry<String, JsonElement> s : jo.getAsJsonObject().entrySet()) {
				String pre = prefix + "." + s.getKey();
				if (s.getValue() instanceof JsonPrimitive) map.put(pre, s.getValue().getAsString());
				if (s.getValue() instanceof JsonObject) map.putAll(jsonToMap(s.getValue(), pre));
			}
		}else if(jo.isJsonArray()){
			int i = 0;
			for (JsonElement s : jo.getAsJsonArray()) {
				String pre = prefix + "." + i++;
				map.putAll(jsonToMap(s, pre));
			}
		}else if(jo.isJsonPrimitive()){//in case
			map.put(prefix, jo.getAsJsonPrimitive().getAsString());
		}
	
		return map;
	}
	
	public static HashMap<String, String> jsonToMap(JsonElement jo) {
		return jsonToMap(jo, "");
	}
	
	public static JsonObject mapToJson(HashMap<String, String> h) {
		return new Gson().toJsonTree(h).getAsJsonObject();
	}
}
