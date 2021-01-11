import Atom.Utility.Cache;
import Atom.Utility.Encoder;
import Atom.Utility.Random;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class Net {
	
	static ArrayList<String> arrayList = new ArrayList<>();
	static URL englishURL;
	
	@Test
	public void start() throws IOException {
		Net n = new Net();
		n.cacheEnglishWord();
		n.memoryCache();
		n.randomEnglishWord();
	}
	
	public void cacheEnglishWord() throws IOException {
		englishURL = new URL("https://raw.githubusercontent.com/AlexHakman/Java-challenge/master/words.txt");
		englishURL = Cache.http(englishURL);
	}
	
	public void memoryCache() throws IOException {
		Runtime runtime = Runtime.getRuntime();
		System.gc();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		new ArrayList<>(Arrays.asList(new String(Encoder.readAllBytes(englishURL.openStream())).split("\n")));
		long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory: " + (usedMemory - memory));
		arrayList = new ArrayList<>(Arrays.asList(new String(Encoder.readAllBytes(englishURL.openStream())).split("\n")));
	}
	
	
	public void randomEnglishWord() {
		for (int i = 0; i < 10; i++) {
			System.out.println(Random.getRandom(arrayList));
		}
	}
}
