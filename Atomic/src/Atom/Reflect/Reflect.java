package Atom.Reflect;

import Atom.Classloader.JarClassLoader;
import Atom.Classloader.SystemURLClassLoader;
import Atom.Random;
import Atom.Struct.Filter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Method;
import java.util.*;

public class Reflect {

    public static <E> Set<Class<? extends E>> getExtendedClass(String packageName, Class<E> e) {
        Reflections reflections = new Reflections(packageName, SubTypesScanner.class);
        return reflections.getSubTypesOf(e);
    }

    public static Method findDeclaredMethod(String name, Class<?> clazz, Class<?>... parameterTypes){
        try{
            Method method = clazz.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (Throwable ignored) {
            return null;
        }
    }

    public static Set<Class<?>> getAllExtendedOrImplementedTypesRecursively(Class<?> clazz) {
        List<Class<?>> res = new ArrayList<>();

        do {
            res.add(clazz);

            // First, add all the interfaces implemented by this class
            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                res.addAll(Arrays.asList(interfaces));

                for (Class<?> interfaze : interfaces) {
                    res.addAll(getAllExtendedOrImplementedTypesRecursively(interfaze));
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
        } while (!"java.lang.Object".equals(clazz.getCanonicalName()));

        return new HashSet<>(res);
    }

    //work for color need improvement
    public static <E> E getRandomField(Class<E> e) {
        return (E) Random.getRandom(e.getDeclaredFields());
    }

    public static ArrayList<Package> findPackages(Filter<Package> filter) {
        ArrayList<Package> a = new ArrayList<>();
        try {
            if (!(SystemURLClassLoader.getURLSystemCl() instanceof JarClassLoader)) {
                for (Package p : Package.getPackages())
                    if (filter.accept(p)) a.add(p);

            } else {
                for (Package P : ((JarClassLoader) SystemURLClassLoader.getURLSystemCl()).getPackages())
                    if (filter.accept(P)) a.add(P);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return a;
    }

}
