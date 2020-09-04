package Atom.Reflect;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

public class SystemClassLoader {

    public static void addURL(URL url) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        addURL(getURLSystemCl(), url);
    }

    public static boolean isAlreadyLoaded(URL url){
        return isAlreadyLoaded(getURLSystemCl(), url);
    }

    public static boolean isAlreadyLoaded(URLClassLoader loader, URL url){
        for (java.net.URL it : loader.getURLs()) {
            if (it.equals(url)) {
               return true;
            }
        }
        return false;
    }

    public static void addURL(URLClassLoader loader, URL url) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        java.lang.reflect.Method method = java.net.URLClassLoader.class.getDeclaredMethod("addURL", java.net.URL.class);
        method.setAccessible(true);
        method.invoke(loader,  new Object[]{url});
    }

    public static URLClassLoader getURLSystemCl(){
       return (URLClassLoader) ClassLoader.getSystemClassLoader();
    }
}
