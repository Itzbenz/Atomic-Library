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

import Atom.Utility.Pool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Repo {
	protected ArrayList<URL> repos = new ArrayList<>();
	
	public Repo() {
	
	}
	
	public Repo(URL... urls) {
		repos.addAll(Arrays.asList(urls));
	}
	
	public static URL getResource(URL u, String s) {
		try {
			URL url = new URL(u.toString() + (u.toString().endsWith("/") ? "" : "/") + s);
			url.openConnection().getContent();
			return url;
		}catch (Throwable e) {
			return null;
		}
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
	
	public void addRepo(URL u) {
		repos.add(u);
	}
}
