package Atom.Net;

import Atom.File.FileUtility;
import Atom.Utility.Pool;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.concurrent.Future;
@Deprecated
public class HTPS {
	//Sync request
	private final URL url;
	private final HashMap<String, String> header = new HashMap<>();
	
	public HTPS(URL url) {
		this.url = url;
	}
	
	public HTPS(String url) throws MalformedURLException {
		this.url = new URL(url);
	}
	
	
	@Deprecated
	public static Future<File> download(String url, File target) {
		return Pool.submit(() -> {
			try {
				return downloadSync(url, target);
			}catch (IOException e) {
				return target;
			}
		});
	}
	
	@Deprecated
	public static File downloadSync(String url, File target) throws IOException {
		URL urls = new URL(url);
		File temp = FileUtility.temp();
		ReadableByteChannel rbc = Channels.newChannel(urls.openStream());
		if (target.exists()) target.delete();
		FileOutputStream fos = new FileOutputStream(temp);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		Files.copy(temp.toPath(), target.toPath());
		return target;
	}
	
	@Deprecated
	public static String post(String url, String postData) throws IOException {
		PrintWriter out;
		BufferedReader in;
		StringBuilder result = new StringBuilder();
		
		URL realUrl = new URL(url);
		// build connection
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		// set request properties
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		// enable output and input
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		out = new PrintWriter(conn.getOutputStream());
		// send POST DATA
		out.print(postData);
		out.flush();
		in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result.append("/n").append(line);
		}
		out.close();
		in.close();
		return result.toString();
	}
	
	@Deprecated
	public static String get(String url) throws IOException {
		URL urls = new URL(url);
		return URLRead(urls);
	}
	
	@Deprecated
	private static String URLRead(URL urls) throws IOException {
		URLConnection yc = urls.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
		StringBuilder inputLine = new StringBuilder();
		while (in.ready()) inputLine.append(in.readLine());
		in.close();
		return inputLine.toString();
	}
	
	@Deprecated
	public static String getPublicIP() {
		String ip = "";
		//Q: why http ?
		//A: what you data you gonna send over http ? password ? no just plain ip that everyone can detect
		//Q: spyware ?
		//A: no, unless someone implement it
		try {
			URL amazon = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(amazon.openStream()));
			ip = in.readLine();
			return ip;
		}catch (Throwable ignored) {
		}
		
		try {
			URL ipApi = new URL("http://ip-api.com/line/");
			BufferedReader in = new BufferedReader(new InputStreamReader(ipApi.openStream()));
			while (in.ready()) ip = in.readLine();
			return ip;
		}catch (Throwable ignored) {
		}
		return ip;
	}
	
	public void setHeader(String k, String v) {
		header.put(k, v);
	}
	
	private void setHeader() {
		if (!header.containsKey("accept")) header.put("accept", "*/*");
		if (!header.containsKey("connection")) header.put("connection", "Keep-Alive");
		if (!header.containsKey("user-agent"))
			header.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	}
	
	public String get() throws IOException {
		return URLRead(url);
	}
	
	public String post(String postData) throws IOException {
		StringBuilder result = new StringBuilder();
		URLConnection conn = url.openConnection();
		// enable output and input
		conn.setDoOutput(true);
		conn.setDoInput(true);
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		// send POST DATA
		out.print(postData);
		out.flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result.append("/n").append(line);
		}
		out.close();
		in.close();
		return result.toString();
	}
}
