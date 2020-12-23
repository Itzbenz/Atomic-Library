package Atom.Classloader;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public final class JarClassLoader extends URLClassLoader {
	
	static {
		registerAsParallelCapable();
	}
	
	public JarClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}
	
	public JarClassLoader(URL[] urls) {
		super(urls);
	}
	
	public JarClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
		super(urls, parent, factory);
	}
	
	public JarClassLoader(ClassLoader parent) {
		super(new URL[0], parent);
	}
	
	public JarClassLoader() {
		this(Thread.currentThread().getContextClassLoader());
	}
	
	@Override
	public void addURL(URL url) {
		super.addURL(url);
	}
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		return super.findClass(name);
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return loadClass(name, false);
	}
	
	public Class<?> findLoadedClazz(String name) {
		return findLoadedClass(name);
	}
	
	protected Class<?> bootstrapFindClass(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = ClassLoader.class.getDeclaredMethod("findBootstrapClassOrNull", String.class);
		method.setAccessible(true);
		return (Class<?>) method.invoke(getParent(), new Object[]{name});
	}
	
	protected Class<?> parentLoadClass(String name, boolean resolve) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Method method = ClassLoader.class.getDeclaredMethod("loadClass", String.class, boolean.class);
		method.setAccessible(true);
		return (Class<?>) method.invoke(getParent(), new Object[]{name, resolve});
	}
	
	//why im doing this
	@Override
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		synchronized (getClassLoadingLock(name)) {
			// First, check if the class has already been loaded
			Class<?> c = findLoadedClass(name);
			if (c == null) {
				long t0 = System.nanoTime();
				try {
					if (getParent() != null) {
						c = parentLoadClass(name, false);
					}else {
						c = bootstrapFindClass(name);
					}
				}catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
					// ClassNotFoundException thrown if class not found
					// from the non-null parent class loader
				}
				
				if (c == null) {
					// If still not found, then invoke findClass in order
					// to find the class.
					c = findClass(name);
					
				}
			}
			if (resolve) {
				resolveClass(c);
			}
			return c;
		}
	}
	
	@Override
	public Package[] getPackages() {
		return super.getPackages();
	}
	
	@Override
	public Object getClassLoadingLock(String className) {
		return super.getClassLoadingLock(className);
	}
	
	
}