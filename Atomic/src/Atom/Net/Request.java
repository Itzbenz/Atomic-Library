/*
 * Copyright 2021 Itzbenz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Atom.Net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.Future;

import Atom.File.FileUtility;
import Atom.Utility.Encoder;
import Atom.Utility.Pool;

public class Request {

	public static byte[] get(String url) throws IOException {
		return Encoder.readAllBytes(new URL(url).openStream());
	}

	public static Future<File> download(String url, File target) {
		return Pool.submit(() -> {
			try {
				return downloadSync(url, target);
			}catch (IOException e) {
				throw new RuntimeException(e);//sneaky
			}
		});
	}
	
	public static File downloadSync(String url, File target) throws IOException {
		URL urls = new URL(url);
		File temp = FileUtility.temp();
		ReadableByteChannel rbc = Channels.newChannel(urls.openStream());
		if (target.exists()) target.delete();
		FileOutputStream fos = new FileOutputStream(temp);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		FileUtility.replace(temp, target);
		return target;
	}

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
	
	public static byte[] post(String url, byte[] postData) throws IOException {
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
		conn.getOutputStream().write(postData);
		conn.getOutputStream().flush();
		return Encoder.readAllBytes(conn.getInputStream());
	}
}
