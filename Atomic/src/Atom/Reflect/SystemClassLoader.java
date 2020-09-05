package Atom.Reflect;

import Atom.Utility.Utility;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

public class SystemClassLoader {

    public static void addURL(URL url) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        addURL(getURLSystemCl(), url);
    }

    public static boolean isAlreadyLoaded(URL url) throws IllegalAccessException {
        return isAlreadyLoaded(getURLSystemCl(), url);
    }

    public static boolean isAlreadyLoaded(URLClassLoader loader, URL url) {
        for (java.net.URL it : loader.getURLs()) {
            if (it.equals(url)) {
                return true;
            }
        }
        return false;
    }

    public static void addURL(URLClassLoader loader, URL url) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(isAlreadyLoaded(loader, url))throw new RuntimeException("URL already loaded: " + url);
        java.lang.reflect.Method method = java.net.URLClassLoader.class.getDeclaredMethod("addURL", java.net.URL.class);
        method.setAccessible(true);
        method.invoke(loader, new Object[]{url});
    }

    public static URLClassLoader getURLSystemCl() throws IllegalAccessException {
        if(ClassLoader.getSystemClassLoader() instanceof DynamicClassLoader){
            return (DynamicClassLoader)ClassLoader.getSystemClassLoader();
        }
        if(Utility.getJavaMajorVersion() > 8)throw new IllegalAccessException("Can't get system URLClassloader in java 9+");
        return (URLClassLoader) ClassLoader.getSystemClassLoader();
    }
}
