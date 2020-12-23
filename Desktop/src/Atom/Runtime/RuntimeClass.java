package Atom.Runtime;

import Atom.Reflect.Reflect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

//one time use lol
public class RuntimeClass {
	public final String classpath;
	private final URLClassLoader classLoader;
	public File dirs;
	private Class<?> loadC;
	private Object classObject;
	
	public RuntimeClass(File dirs, String classpath) throws IOException {
		this(dirs, classpath, null);
	}
	
	public RuntimeClass(File dirs, String classpath, ClassLoader classLoader) throws MalformedURLException {
		this.dirs = dirs;
		this.classpath = classpath;
		if (classLoader == null) classLoader = this.getClass().getClassLoader();
		this.classLoader = new URLClassLoader(new URL[]{dirs.toURI().toURL()}, classLoader);
	}
	
	
	public void load() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		load(classpath);
	}
	
	public void load(String clazzName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		if (loadC != null) throw new IllegalAccessException(clazzName + " already loaded");
		loadC = classLoader.loadClass(clazzName);
		classObject = loadC.getDeclaredConstructor().newInstance();
	}
	
	public void invokeMethod(String name) throws InvocationTargetException, IllegalAccessException {
		getMethod(name).invoke(classObject);
	}
	
	public Method getMethod(String name) {
		return Reflect.getMethod(loadC, name, classObject);
	}
	
}
