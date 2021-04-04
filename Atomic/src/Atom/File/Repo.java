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

package Atom.File;

import Atom.Utility.Encoder;
import Atom.Utility.Pool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Repo {
	protected ArrayList<URL> repos = new ArrayList<>();
	
	public Repo() {
	
	}
	
	public ArrayList<String> readArrayString(String path, String delimiter) throws IOException {
		return new ArrayList<>(Arrays.asList(readString(path).split(delimiter)));
	}
	
	public Properties readProperty(String path) throws IOException {
		Properties p = new Properties();
		p.load(getResourceAsStream(path));
		return p;
	}
	
	public HashMap<String, String> readMap(String path) throws IOException {
		return Encoder.parseProperty(getResourceAsStream(path));
	}
	
	public ArrayList<String> readArrayString(String path) throws IOException {
		return readArrayString(path, System.lineSeparator());
	}
	
	public String readString(String path) throws IOException {
		return Encoder.readString(getResourceAsStream(path));
	}
	
	public void loadClasspath() {
		for (String s : System.getProperty("java.class.path").split(File.pathSeparator)) {
			try {
				addRepo(new File(s));
			}catch (MalformedURLException e) {
				// ???
			}
		}
	}
	
	public Repo(URL... urls) {
		repos.addAll(Arrays.asList(urls));
	}
	
	public static URL getResource(URL u, String s) {
		try {
			URL url = new URL(u.toString() + (u.toString().endsWith("/") ? "" : "/") + s);
			if (url.getContent() != null) return url;
		}catch (Throwable ignored) {}
		return null;
	}
	
	public URL getResource(String s) {
		for (Future<URL> f : parallelSearch(s)) {
			try {
				if (f.get() != null) return f.get();
			}catch (InterruptedException | ExecutionException ignored) { }
		}
		return null;
	}
	
	protected ArrayList<Future<URL>> parallelSearch(String s) {
		ArrayList<Future<URL>> futures = new ArrayList<>();
		for (URL u : repos)
			futures.add(Pool.submit(() -> getResource(u, s)));
		return futures;
	}
	
	public boolean resourceExists(String s) {
		for (Future<URL> f : parallelSearch(s)) {
			try {
				if (f.get() != null) return true;
			}catch (InterruptedException | ExecutionException ignored) { }
		}
		return false;
	}
	
	public InputStream getResourceAsStream(String s) throws IOException {
		URL u = getResource(s);
		return u.openStream();
	}
	
	public ArrayList<URL> getRepos() {
		return new ArrayList<>(repos);
	}
	
	public static URL convertToURLJar(URL u) throws MalformedURLException {
		return new URL("jar:" + u.toExternalForm() + "!/");
	}
	public void addRepo(File f) throws MalformedURLException {
		addRepo(f.toURI().toURL());
	}
	public void addRepo(URL u) {
		repos.add(u);
		String s = u.getFile();
		if (s.endsWith(".jar") || s.endsWith(".zip")) {
			try { repos.add(convertToURLJar(u)); }catch (Throwable ignored) { }
		}
	}
	
}
