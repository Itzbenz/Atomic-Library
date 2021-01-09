/*
 * Copyright 2020 Itzbenz
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

package Atom.Utility;

import Atom.Net.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Cache {
	public static File cache = new File("cache/");
	
	static {
		cache.mkdirs();
	}
	
	public static URL http(String s) throws IOException {
		return http(new URL(s));
	}
	
	public static URL http(URL url) throws IOException {
		if (url.getProtocol().startsWith("file")) return url;
		if (!url.getProtocol().startsWith("http")) throw new MalformedURLException("URL is not http");
		File target = new File(cache, url.getFile().replaceAll("/", "."));
		if (target.exists()) return target.toURI().toURL();
		Request.downloadSync(url.toExternalForm(), target);
		if (target.exists()) return target.toURI().toURL();
		else throw new FileNotFoundException(target.getAbsolutePath());
	}
}
