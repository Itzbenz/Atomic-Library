package Atom.Utility;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;

public class Utility {

	public static String jitpack = "https://jitpack.io/";

	public static boolean isRepeatingPattern(String s) {
		return isRepeatingPattern(s, 5);
	}

	public static String capitalizeEnforce(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	public static <T> T getOrDefault(Map<?, T> map, Object key, T def) {
		T t = map.get(key);
		if (t == null) return def;
		return t;
	}

	public static boolean isRepeatingPattern(String s, int maxRepeatingChar) {
		int parsed = s.replaceAll("(.+?)\\1+", "$1").length();
		parsed = parsed + maxRepeatingChar;
		int unparsed = s.length();
		return unparsed > parsed;
	}

	public static String getDownload(String url, String groupId, String artifactId, String version) {
		if (url.endsWith("/")) url = url.substring(0, url.length() - 1);
		return String.format("%s/%s/%s/%s/%s-%s.jar", url, groupId.replace('.', '/'), artifactId, version, artifactId, version);
	}
	
	public static String getExtension(File f) {
		String extension = "";
		int i = f.getName().lastIndexOf('.');
		if (i > 0) {
			extension = f.getName().substring(i + 1);
		}
		return extension;
	}


	public static String getDate() {
		Formatter formatter = new Formatter();
		Calendar c = Calendar.getInstance();
		return formatter.format(Locale.UK, "%tl:%tM:%tS %tp %tB %te, %tY", c, c, c, c, c, c, c).toString();
	}
	
	public static String getDate(long milis) {
		Date d = new Date(milis);
		Formatter formatter = new Formatter();
		Calendar c = toCalendar(d);
		return formatter.format(Locale.UK, "%tl:%tM:%tS %tp %tB %te, %tY", c, c, c, c, c, c, c).toString();
	}
	
	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public static <T> T[] toArray(List<T> arr) {
		return (T[]) arr.toArray();
	}
	
	//T t3 = new T[t1.length + t2.length];
	public static <T> T[] joinArray(T[] t1, T[] t2, T[] t3) {
		int m = 0;
		for (int i = 0; i < t1.length; i++) {
			m = i;
			t3[m] = t1[m];
		}
		for (int i = m; i < t2.length; i++) {
			t3[m] = t2[i];
		}
		return t3;
	}
	
	public static String[] getArray(List<String> arrayList) {
		String[] arr = new String[arrayList.size()];
		arr = arrayList.toArray(arr);
		return arr;
	}
	
	public static String shrinkString(String s) {
		return new LinkedHashSet<>(Arrays.asList(s.split("-"))).toString().replaceAll("(^\\[|]$)", "").replace(", ", "-");
	}
	
	public static <T> T getFromJson(String json, Class<T> tClass) {
		return new Gson().fromJson(json, tClass);
	}
	
	public static String toJson(Object clazz) {
		return new Gson().toJson(clazz);
	}
	
	public static String removal(String s, char openChar, char closeChar) {
		StringBuilder sb = new StringBuilder();
		boolean delete = false;
		for (char i : s.toCharArray()) {
			if (i == openChar) {
				delete = true;
			}else if (i == closeChar) {
				delete = false;
			}else {
				if (delete) continue;
				sb.append(i);
			}
		}
		return sb.toString();
	}
	
	public static String shrinkChar(String s) {
		char[] chars = s.toCharArray();
		Set<Character> cr = new LinkedHashSet<>();
		for (char c : chars) cr.add(c);
		StringBuilder sb = new StringBuilder();
		for (Character chr : cr) sb.append(chr);
		return sb.toString();
	}
	
	public static boolean containIntOnly(String s) {
		return (s.matches("[0-9]+"));
	}
	
	public static String removeFirstChar(String s, int howMany) {
		return s.substring(howMany);
	}
	
	public static String removeFirstChar(String s) {
		return s.substring(1);
	}
	
	public static String[] sliceString(String s, String fix) {
		return s.split(fix, -1);
	}
	
	public static int[] sliceInt(String s, String fix) {
		String[] strArray = s.split(fix);
		int[] intArray = new int[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			intArray[i] = Integer.parseInt(strArray[i]);
		}
		return intArray;
	}
	
	public static String shuffle(String input) {
		List<Character> characters = new ArrayList<>();
		for (char c : input.toCharArray()) {
			characters.add(c);
		}
		StringBuilder output = new StringBuilder(input.length());
		while (characters.size() != 0) {
			int randPicker = (int) (Math.random() * characters.size());
			output.append(characters.remove(randPicker));
		}
		return output.toString();
	}
	
	public static String capitalizeFirstLetter(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public static String lowerFirstLetter(String s) {
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
	public static HashMap<String, String> ArgParser(String[] arg, String prefix) {
		
		HashMap<String, String> args = new HashMap<>();
		
		if (arg.length % 2 != 0) throw new IllegalArgumentException("Not Enough Argument" + Arrays.toString(arg));
		boolean i = true;
		String k = "";
		for (String d : arg) {
			if (d.startsWith(prefix)) {
				k = d.replaceFirst(prefix, "");
			}else {
				args.put(k, d);
			}
			i = !i;
		}
		
		return args;
	}
	
	public static int getJavaMajorVersion() {
		String version;
		version = System.getProperty("java.version");
		if (version == null || version.isEmpty()) version = System.getProperty("java.runtime.version");
		if (version.startsWith("1.")) {
			version = version.substring(2, 3);
		}else {
			int dot = version.indexOf(".");
			if (dot != -1) {
				version = version.substring(0, dot);
			}
		}
		return Integer.parseInt(version);
		
	}
	
	public static String joiner(List<String> datas, String prefix) {
		return joiner(getArray(datas), prefix);
	}
	
	public static String joiner(String[] datas, String prefix) {
		StringBuilder data = new StringBuilder();
		for (int i = 0; i < datas.length; i++) {
			data.append(datas[i]);
			if (i != datas.length - 1) data.append(prefix);
		}
		return data.toString();
	}
	
	public static void convertThreadToInputListener(String enterCommandName, Consumer<String> listener) throws IOException {
		while (!Thread.interrupted()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(enterCommandName);
			listener.accept(br.readLine());
		}
	}
}
