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

package Atom.Classloader;


import Atom.Net.HTPS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class AtomClassLoader extends URLClassLoader {
    protected static File cache = new File("lib/");

    static {
        cache.mkdirs();
        registerAsParallelCapable();
    }


    public AtomClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public AtomClassLoader(URL[] urls) {
        super(urls);
    }

    public void defineClass(String name, InputStream is) throws IOException {
        byte[] h = is.readAllBytes();
        defineClass(name, h, 0, h.length);
    }

    @Override
    public void addURL(URL url) {
        if (url.getProtocol().startsWith("http")) {
            File temp = new File(cache, url.getFile().substring(1).replace("/", "."));
            if (!temp.exists()) {
                try { HTPS.downloadSync(url.toExternalForm(), temp); }catch (Throwable ignored) { }
            }
            if (temp.exists()) {
                try { url = temp.toURI().toURL(); }catch (MalformedURLException ignored) { }
            }
        }

        super.addURL(url);
    }

    public void addURL(File file) throws MalformedURLException, FileNotFoundException {
        if (file.exists()) addURL(file.toURI().toURL());
        else throw new FileNotFoundException(file.getAbsolutePath());
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        //Note: don't mess with java.
        if (name.startsWith("java.")) return ClassLoader.getSystemClassLoader().loadClass(name);
        return super.loadClass(name);
    }
}
