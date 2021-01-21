package Atom.Reflect;

import Atom.Struct.Filter;
import Atom.Utility.Random;
import io.github.classgraph.ClassGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Reflect {
	public static String getCallerClass() {
		return Thread.currentThread().getStackTrace()[3].getClassName();
	}
	
	public static ClassGraph cg = new ClassGraph().enableClassInfo().enableFieldInfo().enableAnnotationInfo().enableRemoteJarScanning();
	
	public static StackTraceElement getCallerClassStackTrace() {
		return Thread.currentThread().getStackTrace()[3];
	}
	
	public static Object parseStringToPrimitive(String data, Class<?> type) {
		if (type.equals(String.class)) return data;
		if (data.isEmpty()) return null;
		
		if (type.getTypeName().equals(boolean.class.getTypeName())) {
			if (data.equalsIgnoreCase("true")) return true;
			else if (data.equalsIgnoreCase("false")) return false;
			else return null;
		}
		
		if (type.getTypeName().equals(int.class.getTypeName())) return Integer.parseInt(data);
		if (type.getTypeName().equals(long.class.getTypeName())) return Long.parseLong(data);
		if (type.getTypeName().equals(double.class.getTypeName())) return Double.parseDouble(data);
		if (type.getTypeName().equals(float.class.getTypeName())) return Float.parseFloat(data);
		if (type.getTypeName().equals(short.class.getTypeName())) return Short.parseShort(data);
		if (type.getTypeName().equals(byte.class.getTypeName())) return Byte.parseByte(data);
		
		return null;
	}
	
	public static <E> Set<Class<? extends E>> getExtendedClass(String packageName, Class<E> e) {
		Reflections reflections = new Reflections(packageName, SubTypesScanner.class);
		return reflections.getSubTypesOf(e);
	}
	
	public static void restart(File jar, List<String> classpath) throws FileNotFoundException {
		if (!jar.exists()) throw new FileNotFoundException(jar.getAbsolutePath());
		StringBuilder cli = getRestartArg();
		cli.append("-cp ");
		for (String s : classpath)
			cli.append(s).append(System.getProperty("path.separator"));
		if (System.getProperty("path.separator").equals(String.valueOf(cli.charAt(cli.length() - 1)))) {
			cli = new StringBuilder(cli.substring(0, cli.length() - 1));
		}
		cli.append(" jar ");
		cli.append(jar.getAbsolutePath());
		String s = cli.toString();
		new Thread(() -> {
			try {
				Runtime.getRuntime().exec(s);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		System.exit(0);
	}
	
	private static StringBuilder getRestartArg() {
		StringBuilder cli = new StringBuilder();
		cli.append(System.getProperty("java.home")).append(File.separator).append("bin").append(File.separator).append("java").append(OS.isWindows ? ".exe " : " ");
		for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
			cli.append(jvmArg).append(" ");
		}
		return cli;
	}
	
	public static void restart(String mainClass, List<String> classpath) {
		StringBuilder cli = getRestartArg();
		cli.append("-cp ");
		for (String s : classpath)
			cli.append(s).append(System.getProperty("path.separator"));
		if (System.getProperty("path.separator").equals(String.valueOf(cli.charAt(cli.length() - 1)))) {
			cli = new StringBuilder(cli.substring(0, cli.length() - 1));
		}
		cli.append(" ");
		cli.append(mainClass);
		String s = cli.toString();
		new Thread(() -> {
			try {
				Runtime.getRuntime().exec(s);
			}catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
		System.exit(0);
	}
	
	public static String getMainClassName() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		String s = null;
		if (trace.length > 0) {
			s = trace[trace.length - 1].getClassName();
			if (s.equals(Thread.class.getName())) s = null;
		}
		return s;
	}
	
	public static File getCurrentJar(Class<?> clazz) {
		//fool proof
		return new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
	}
	
	public static File getCurrentJar(Object clazz) {
		//more fool proof
		return new File(clazz.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
	}
	
	public static File getCurrentJar() {
		//even more fool proof
		return new File(Reflect.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	}
	
	public static <E> E getField(Class<?> clazz, String name, Object object) {
		ArrayList<Field> result = findDeclaredField(clazz, f -> f.getName().equals(name));
		E e = null;
		for (Field f : result) {
			try {
				e = (E) f.get(object);
			}catch (Throwable ignored) {
			}
		}
		return e;
	}
	
	public static Method getMethod(Class<?> clazz, String name, Class<?>... parameter) throws NoSuchMethodException {
		return clazz.getMethod(name, parameter);
	}
	
	public static Method getMethod(Class<?> clazz, String name, Object object) {
		ArrayList<Method> f = findMethod(clazz, s -> s.getName().equals(name), object);
		if (f == null || f.isEmpty()) return null;
		else return f.get(0);
	}
	
	public static ArrayList<Method> findMethod(Class<?> clazz, Filter<Method> filter, Object object) {
		ArrayList<Method> result = new ArrayList<>();
		try {
			Method[] methods = object.getClass().getDeclaredMethods();
			for (Method m : methods) {
				if (filter.accept(m)) result.add(m);
			}
			for (Method m : methods) {
				try {
					m.setAccessible(true);
				}catch (Throwable ignored) {
				}
			}
			return result;
		}catch (Throwable ignored) {
		}
		try {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method m : methods) {
				if (filter.accept(m)) result.add(m);
			}
			for (Method m : methods) {
				try {
					m.setAccessible(true);
				}catch (Throwable ignored) {
				}
			}
			
		}catch (Throwable ignored) {
		}
		if (result.isEmpty()) return null;
		else return result;
	}
	
	public static ArrayList<Field> findDeclaredField(Class<?> clazz, Filter<Field> filter) {
		return findDeclaredField(clazz, filter, null);
	}
	
	public static ArrayList<Field> findDeclaredField(Class<?> clazz, Filter<Field> filter, Object object) {
		ArrayList<Field> result = new ArrayList<>();
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (filter.accept(f)) result.add(f);
			}
			for (Field f : result) {
				try {
					f.setAccessible(true);
				}catch (Throwable ignored) {
				}
			}
			return result;
		}catch (Throwable ignored) {
		}
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				if (filter.accept(f)) result.add(f);
			}
			for (Field f : result) {
				try {
					f.setAccessible(true);
				}catch (Throwable ignored) {
				}
			}
		}catch (Throwable ignored) {
		}
		if (result.isEmpty()) return null;
		else return result;
	}
	
	public static Object invoke(Method m, Object o, Object... arg) throws InvocationTargetException, IllegalAccessException {
		m.setAccessible(true);
		return m.invoke(o, arg);
	}
	
	@Deprecated
	//help its broke
	public static <T> ArrayList<Class<T>> getAllExtendedOrImplementedTypesRecursively(Class<?> clazz) {
		List<Class<?>> res = new ArrayList<>();
		
		do {
			res.add(clazz);
			
			// First, add all the interfaces implemented by this class
			Class<?>[] interfaces = clazz.getInterfaces();
			if (interfaces.length > 0) {
				res.addAll(Arrays.asList(interfaces));
				
				for (Class<?> interfaced : interfaces) {
					res.addAll(getAllExtendedOrImplementedTypesRecursively(interfaced));
				}
			}
			
			// Add the super class
			Class<?> superClass = clazz.getSuperclass();
			
			// Interfaces does not have java,lang.Object as superclass, they have null, so break the cycle and return
			if (superClass == null) {
				break;
			}
			
			// Now inspect the superclass
			clazz = superClass;
		}while (!"java.lang.Object".equals(clazz.getCanonicalName()));
		ArrayList<Class<T>> classes = new ArrayList<>();
		for (Class<?> c : res) {
			try {
				classes.add((Class<T>) c);
			}catch (Throwable ignored) {
			
			}
		}
		return classes;
	}
	
	
	//static
	public static <E> E getRandomField(Class<E> type) {
		return getRandomField(type, null);
	}
	
	//dynamic
	public static <E> E getRandomField(Class<E> type, Object o) {
		ArrayList<E> arrayList = new ArrayList<>();
		for (Field f : type.getDeclaredFields()) {
			try {
				if (type.isInstance(f.get(o))) arrayList.add((E) f.get(o));
			}catch (Throwable ignored) {
			}
		}
		return Random.getRandom(arrayList);
	}
	
	public static ArrayList<Package> findPackages(Filter<Package> filter) {
		ArrayList<Package> a = new ArrayList<>();
		for (Package p : Package.getPackages())
			if (filter.accept(p)) a.add(p);
		return a;
	}
	
	public static ArrayList<Package> findPackages(Filter<Package> filter, ClassLoader classLoader) {
		ArrayList<Package> a = new ArrayList<>();
		for (Package p : (Package[]) getField(ClassLoader.class, "getPackages", classLoader))
			if (filter.accept(p)) a.add(p);
		return a;
	}
	
}
