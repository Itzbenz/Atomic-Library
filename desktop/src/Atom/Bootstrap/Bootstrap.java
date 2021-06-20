package Atom.Bootstrap;

import java.util.HashSet;

public interface Bootstrap<T extends ClassLoader> {
	HashSet<String> loadedList = new HashSet<>();
	
	default void moduleCheck(String name) {
		if (loadedList.contains(name))
			throw new RuntimeException(new IllegalStateException("Module: " + name + " already loaded"));
		loadedList.add(name);
		setStatus("Loading: " + name);
	}
	
	void setStatus(String s);
	
	T getLoader();
	
	default void loadMain(Class<?> c, String[] arg) throws Throwable {
		loadMain(c.getCanonicalName(), arg);
	}
	
	void loadMain(String s, String[] arg) throws Throwable;
}
