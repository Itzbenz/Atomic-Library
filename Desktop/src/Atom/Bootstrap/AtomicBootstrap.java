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

package Atom.Bootstrap;

import Atom.Classloader.AtomClassLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

public class AtomicBootstrap {
	public AtomClassLoader atomClassLoader;
	protected HashSet<String> loadedList = new HashSet<>();
	
	public AtomicBootstrap() {
		this(null);
	}
	
	public AtomicBootstrap(ClassLoader parent) {
		atomClassLoader = new AtomClassLoader(new URL[]{}, parent);
		setStatus("AtomClassLoader parent: " + String.valueOf(parent));
	}
	
	public void loadCurrentClasspath() {
		moduleCheck("CurrentClasspath");
		atomClassLoader.addURL(this.getClass().getProtectionDomain().getCodeSource().getLocation());
	}
	
	public void setStatus(String s) {
		System.out.println(s);
	}
	
	public void loadClasspath() throws MalformedURLException {
		moduleCheck("Classpath");
		setStatus("Loading " + "Classpath" + " Library");
		for (String s : System.getProperty("java.class.path").split(File.pathSeparator))
			atomClassLoader.addURL(new File(s));
	}
	
	protected void moduleCheck(String name) {
		if (loadedList.contains(name))
			throw new RuntimeException(new IllegalStateException("Module: " + name + " already loaded"));
		loadedList.add(name);
		setStatus("Loading: " + name);
	}
}
