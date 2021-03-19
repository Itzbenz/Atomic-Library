package Atom.Utility;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.Map;

public class EncoderJson {
	public static Gson gson = new Gson();


	public static JsonObject mapToJson(HashMap<String, String> h) {
		return gson.toJsonTree(h).getAsJsonObject();
	}
}
